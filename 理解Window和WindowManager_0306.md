#理解Window和WindowManager
	Window 窗口
		场景 桌面组件 Dialog Toast 类似悬浮窗
	Window是个抽象类
		实现类是PhoneWindow
	怎么创建Window
		通过WindowManager
	WindowManagerService -> WMS
	WindowManager和WMS的交互是个IPC过程
	Android中的视图都是通过Window来呈现的
	Activity Toast Dialog都是依附在Window上的
	Window是View的直接管理者
	Activity的onCreate中的setContentView也是调用了Window的setContentView方法
	WindowManager 接口
		addView()
		updateView()
		removeView()
	WindowManager继承了ViewManager
	
##Window的内部
	Window的添加 更新和删除过程
	WindowManager的addView()
		-> WindowManagerImpl类
			-> 交由 WindowManagerGlobal类去具体实现addView(View);
	具体过程
		1. 检查参数
		2. 创建ViewRootImpl并将View加到列表中
			几个重要的列表
			ArrayList<View> mViews = new ArrayList<View>();
			ArrayList<ViewRootImpl> mRoots = new ArrayList<ViewRootImpl>();
			ArrayList<WindowManager.LayoutParams> mParams = new ArrayList<WindowManager.LayoutParams>();
			// 移除的视图就放这里(放正在被删除的View对象)
			ArraySet<View> mDyingViews = new ArraySet<View>();
			new ViewRootImpl();
			mViews.add();
			mRoots.add();
			mParams.add();
			ViewRootImpl对象
			添加完后会调用requestLayout()
		3. 通过ViewRootImpl来更新界面完成Window的添加过程
			ViewRootImpl的setView();
				requestLayout() 异步刷新请求
			这个过程还涉及到和WMS的IPC交互
			View的绘制入口
				scheduleTraversals()
			mWindowSession 类型是IWindowSession 是个Binder对象 真正的实现类是Session => Window的添加过程就是一次IPC调用
			Session的内部再通过WMS完成Window的添加
			交由WMS去处理
		Window的删除过程
			从前面可知 直接看WindowManagerGlobal的removeView(View)
			如果你写 怎么实现
			之前的View是加到ArrayList中  要找到索引并移除  放进垃圾箱 mDyingViews(ArraySet)中
		Window的更新过程
			还是一样 直接看WindowManagerGlobal的updateView(View)
			替换
			requestLayout()

##Window的创建过程
		View不能单独存在
		有视图的地方就有Window 那么Activity Dialog Toast都会和W indow关联
		Activity中的Window的创建过程
			先了解Activity的启动过程(比较复杂)
			ActivityThread的performLaunchActivity()
			attach方法中  会创建Activity所属的Window对象
				通过Policy.makeNewWindow(this);
			因为Activity实现了Window的Callback接口 所以Window接收到外界状态改变就会回调Activity的方法
				public class Activity extends ContextThemeWrapper
        					implements LayoutInflater.Factory2,
        					Window.Callback, KeyEvent.Callback,
        					OnCreateContextMenuListener, ComponentCallbacks2
				setContent -> 直接看PhoneWindow的setContentView方法
					public void setContentView(int layoutResID) {
        						getWindow().setContentView(layoutResID);
        						initActionBar();
   					}
				步骤：
					1. 如果没有DecorView 就创建它 -> installDecor() -> generateDecor方法直接创建
						DecorView是Activity的顶级View 一般包含标题栏和内容栏
						标题栏可能随着主题变化而变化 而content是一定要存在的
						(联想下之前为什么说设置NOTITLLE要在setContentView之前 想下视图的层级 DecorView TitleView ContentView关系)
						content的id是android.R.id.content
						此时的DecorView只是一个空白的FrameLayout
					2. 把View加到DecorView的mContentParent中
					 	将Activity的视图加到DecorView的mContentParent中
						inflate方法
						回想下Activity中的setContentView的来历
							为啥不叫setView呢 => 就是添加到DecorView的mContentParent中 所以叫setContentView更准确
					3. 回调Activity的onContentChanged方法通知Activity视图已经发生改变
							已经被添加到DecorView的mContentParent中了 要通知Activity
							Activity中是空实现
							public void onContentChanged() {
    							}
							可以在你的子类Activity中实现
					此时 布局视图已经被添加到DecorView的mContentParent中了   !!!但是DecorView还被被添加到Window中
					虽然 早在Activity的attach方法中就把Window对象创建了 但是 DecorView还没被WindowManager识别	
					在ActivityThread的handleResumeActivity中 会调用onResume 再调用Activity的makeVIsibile();

					 void makeVisible() {
        						if (!mWindowAdded) {
            					ViewManager wm = getWindowManager();
						// DecorView添加到Window中
            					wm.addView(mDecor, getWindow().getAttributes());
           					mWindowAdded = true;
        						}
					// 让DecorView可见(显示)
        					mDecor.setVisibility(View.VISIBLE);
    					}
					在makeVisible中 DecorView才真正完成了添加和显示 此时Activity的视图才能被用户看到 联系下声明周期的可见  onStart()等
		Dialog中的Window的创建过程
			和Activity的类似
			1. 创建Window
					同样是通过PolicyManager.makeNewWindow();  创建出来的就是PhoneWindow
			2. 初始化DecorView并将Dialog的视图添加到DecorView中
					也是类似  通过Window去添加指定的布局文件
			3. 把DecorView添加到Window中并显示
					在Dialog的show方法中 通过WindowManager将DecorView添加到Window中
					if (mShowing) {
            					if (mDecor != null) {
                						if (mWindow.hasFeature(Window.FEATURE_ACTION_BAR)) {
                    						mWindow.invalidatePanelMenu(Window.FEATURE_ACTION_BAR);
                						}
                					mDecor.setVisibility(View.VISIBLE);
            					}
            				return;
       					 }
					mWindowManager.addView(mDecor, l);
            				mShowing = true;
					当Dialog被关闭时
						通过WindowManager来移除DecorView
						dismiss()
							-> dismissDialog();
								mWindowManager.removeViewImmediate(mDecor);
			Dialog的特殊之处
				必须采用Activity的Context 如果是Application的Context 就会出错
				创建Dialog时 传进构造方法
				报错：没有token  token一般只有Activity才有
				
			
		Toast中的Window的创建过程		
			稍复杂
			有定时取消功能 所以系统用了Handler
			Toast中存在的两类IPC 
				1. 访问NotificationManagerService(NMS)
				2. NMS回调Toast中的TN接口
			Toast的视图
				1. 系统默认
				2. 自定义 
						setView();
						public void setView(View view) {
        							mNextView = view;
   						 }	
			Toast的显示和隐藏
				1. show();
				2. cancel();
				实现
					// 显示
					public void show() {
        						if (mNextView == null) {
            						throw new RuntimeException("setView must have been called");
        						}

        					INotificationManager service = getService();
        					String pkg = mContext.getPackageName();
        					TN tn = mTN;
        					tn.mNextView = mNextView;

        					try {
						// 显示的关键
            					service.enqueueToast(pkg, tn, mDuration);
        						} catch (RemoteException e) {
            				// Empty
        						}
    					}
					// 隐藏
					public void cancel() {
        						mTN.hide();

        					try {
            					getService().cancelToast(mContext.getPackageName(), mTN);
        					} catch (RemoteException e) {
            				// Empty
        						}
    					}
				=> 显示和隐藏都需要通过NMS实现  NMS在系统的单独进程中 所以又是一个IPC过程
				只能通过远程调用的方式来显示和隐藏Toast
				TN类是个Binder类
				// 熟悉的Binder 
				private static class TN extends ITransientNotification.Stub 
				在Toast和NMS进行IPC时 
				
				显示的关键 调用service.enqueueToast(pkg, tn, mDuration); NMS的enqueueToast方法
					参数 tn是回调  
					发Toast请求也涉及到消息队列
					NMS的enqueueToast代码
					// 把请求对象封装成ToastRecord对象 并加到队列mToastQueue(ArrayList)中
					record = new ToastRecord(callingPid, pkg, callback, duration);
			                  mToastQueue.add(record);
					// 注意 这个队列的请求的数量是有上限的 50 
					为什么这么做 DOS(Denial of Service)
					!!!!!!关于Toast的时长 回调 TN等
						ToastRecord被添加到mToastQueue后 NMS通过 showNextToastLocked()显示Toast
						显示后 调用延时(取决于时长)
							 showNextToastLocked()中的scheduleTimeoutLocked(record);
						// 待续.......
					

##还有
	菜单 popupwindow 状态栏也是通过Window来实现的