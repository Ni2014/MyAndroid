#理解Activity
	framework源码
	Activity类和其他一般类的区别
		没啥区别
	生命周期是什么时候回调的
	Activity是如何被打开的
		startActivity
	Activity栈是怎么交互的
		在AMS  交互
	相关的类
		Context
		ActivityThread
		ApplicationThread
		
	深入学习Activity的意义
		知其所以然
		高级开发中 Android的设计机制
		插件化开发中 设计框架  Activity生命周期的回调
		理解层次 不仅仅是熟练使用 Activity的实现机制
	
	ActivityThread
		并不是线程
		仅仅是个普通类
		ActivityThread是如何被启动的
			运行在主线程中
			main()
				Looper.prepareMainLooper()
				Activ	ityThread thread = new ActivityThread();
				thread.attach(false)
				Looper.loop()
				主线程的消息队列
		ActivityThread启动后怎么通知AMS的
				IActivityManager mgr = ActivityManagerNative.getDefault();
				和AMS交互
				ActivityThread运行在App进程中的主线程中  AMS是在另一个进程中 
				ActivityThread和AMS之间的交互就是一个IPC过程
				
		ActivityThread中的main方法
				ActivityThread 仅仅是个普通的java类
				注意 其中有AsyncTask的init方法
				注意attach方法
				和AMS之间交互
				IActivityManager  mgr = ActivityManagerNative.getDefault();得到的是AMS一个代理对象
				AMS运行在一个单独的进程中 那么怎么在App运行所在的进程中得到这个AMS对象 IPC
				通过ActivityManagerNative拿到AMS的代理对象
				mgr.attachApplication(mAppThread);
				ApplicationThread mAppThread是一个实现了Binder的类
				attachApplication方法做了什么
		ApplicationThread相关类
				继承了ApplicationThreadNative，ApplicationThreadNative继承了Binder
		在哪被初始化
			在ActivityThread中
				ApplicationThread mAppThread = new ApplicationThread();
				
		ActivityClientRecord
			Activity的描述类  ActivityInfo(theme permission等)

		ActivityRecord
			AMS中存的
			记录了Activity的各种属性和管理状态

		Context类
			常用 getResource getSystemService getSP等
			环境 
			抽象类Context
				下有ContextImpl
					ContextWrapper
						下有Service
						       Application
						       ContextThemeWrapper
							下有Activity(带有主题)
		Context类
			getResource等方法
			sendBroadcast等
			定义了和Activity相关的方法和资源 提供给在云的Activity的环境
		ContextWrapper类
			都是调mBase(Context)的方法 mBase在构造方法中传进来的
			mBase是ContextImpl？？
			
	Activity生命周期分析
	Activity类和其他类又什么区别
		本质上没啥区别
		framework对其统一控制 栈 IPC 
	生命周期方法是什么时候被回调的
		Instrumentation是什么
			辅助类(回调生命周期方法)  AMS控制着回调
			ActivityThread的一个重要的成员变量 H Handler  他处理AMS通过IPC 发过来的消息
			看他的handleMessage方法
			case LAUNCH_ACTIVITY
			r是远程传来的
			先拿到ActivityClientRecord r = (ActivityClientRecord)msg.obj;
			r.packageInfo = getPackageInfoNoCheck();
			handleLaunchActivity(r,null);
		Instrumentation的作用
			1 Application对象的创建  	
			2 生命周期的管理
			3 启动Activity
		startActivity
		startActivityForResult
			最终都是调用到了Instrumentation的方法
		ActivityManagerNative是什么
			远程的AMS的代理对象
		H Handler是干啥用的
			
		onCreate onResume
			生命周期方法分析
			onCreate在哪里被调用
			Activity对象在哪被创建
			 H Handler
			handleLaunchActivity方法
				performLaunchActivity方法
					Application对象建立
					window对象和Activity进行关联
					classloader加载相关Activity(反射)
					newInstance
					和其他对象不同的  生命周期函数
					activity.attach() 调用attach之前会创建Application对象
					整个App的第一个对象 Application对象
					(Application对象的创建是通过Instrumentation类)
					activity.attach()   => 关联window对象和Activity
					创建并关联后 就会调用
					mInstrumentation.callActivityOnCreate(activity,r.state)
					r是ActivityClientRecord
				Application app = r.packageInfo.makeApplication(false,mInstrumentation)
				回调onCreate callActivityOnCreate=> app.onCreate();
				title 
				Configuration 横竖屏等
				activity.attach(); -> activity和window进行关联
				mWindow = PolicyManager.makeNewWindow(this);
				mWindow.setWindowManager();
				mInstrumentation.callActivityOnCreate(); // 回调onCreate
				中 调用activity.performCreate();
						中 调用onCreate();
				activities.put();
				每一个ActivityThread都保存着一个运行的Activity对象的集合activities 是ArrayMap类型的
			onCreate()中的setContentView方法	
			看setContentView();
				还是调用了getWindow().setContentView(view,params);
				activity.attach()中关联了activity和window
				PhoneWindow  实际调用到的是PhoneWindow的setContentView
				PhoneWindow类
					setContentView方法
						DecorView(继承自FrameLayout)
						再在帧布局的基础上addView()
						
			performLaunchActivity做了什么
	vm.addView(decor,l)
	WindowManagerGlobal的addView方法
	ViewManager是什么
		WindowManagerGlobal
		WindowManagerImpl
	ViewRootImpl
	让Activity处于显示的状态 r.activity.makeVisibile();
	Activity Window WindowManager三者关系

		onResume分析
			显示Activity
			怎么显示的 通过WindowManager
			View 添加到窗口中 ViewRootImpl add remove方法 checkThread方法
			H 的handleResumeActivity方法
				拿到Record对象r
				performResumeActivity
					performResume
						mInstrumentation.callActivityOnResume(this);
							activity.onResume()
							此时布局还没显示到界面 onResume之后才能
						拿到window   
							activity.getWindow();
						拿到decorView
							r.window.getDecorView();
						// WindowManager在哪被赋值的 在activity调用attach方法时
						ViewManager wm = a.getWindowManager();
						vm.addView(decor,l)
						// attach 方法
						mWindow.setWindowManager()
					window 抽象类中
						setWindowManager方法
						WindowManager是继承自ViewManager
							ViewManager是个接口 三个方法
								addView()
								updateView()
								removeView()
					ViewManager
						WindowManager
							WindowManagerImpl
							 	他的addView等操作委托给了WindowManagerGlobal(mGlobal)去addView	
					代码是vm.addView(decor)
					最终执行的是WindowManagerGlobal的addView方法
					WindowManagerGlobal中有ViewRootImpl对象
						ViewRootImpl实现具体对每个View的管理
						因为ViewRootImpl需要和远程的AMS进行交互
						对View的操作都是通过ViewRootImpl
							checkThread等
							ViewRootImpl中有一个W 类  实现了IWindow.Stub
							每个Activity都有一个ViewRootImpl
							WindowManagerGlobal中
							mViews.addView(view);
							mRoots.add(root);
							mParams.add(wparams);
							// 把ViewRootImpl和要显示的View进行关联
							root.setView()  
							setView中调用了requestLayout();
							后view.assignParent(this);
							对View调用invalidate方法 最终都会调用到ViewRootImpl的invalidate方法
							View的mParent对象
							这个parent就是ViewRootImpl？
						View的绘制是通过ViewRootImpl的
							invalidate()
							重绘中会拿到parent对象
							是ViewRootImpl
							再调p.invalidateChild();
								重绘时 会调用到checkThread()
								子线程更新UI抛出的异常 就是在这方法中抛出的
								if(mThreaf!=Thread.currentThread()){
										throw new XxxException("Only ........");}
								View在哪里被显示
								ActivityThread中
								r.activity.makeVisibile();
									mDecor.setVisibility(View.VISIBILE);
									会检查DecorView是否添加到Window
								WindowManagerGlobal对View的管理也是通过ViewRootImpl的

		
		onStop
			不可见 
			之前设置为可见仅仅是mDecor.setVisibility(VIEW.VISIBILE);
			=> mDecor.setVisibility(VIEW.DISABLE);??
			handlerStopActivity方法
				updateVisibility()
					mNumVisibleActivities--;
					// 设置不可见
					v.setVisibility(View.INVISIBILE);

		onDestory
			Activity怎么设置不可见
			onDestory真的销毁Activity了吗
				No 仅仅是取消View和ViewRootImpl不进行关联 和把ActivityClientRecord对象在AMS中进行移除
				拿到WindowManager
				WindowManager wm = r.activity.getWindowManager();
				View v = r.activity.mDecor;
				// 移除DecorView
				wm.removeViewImmediate(v);
				和AMS交互
				performDestoryActivity();
				有个IBinder token;
				r = mActivities.get(token);
				ArrayMap<IBinder,ActivityClientRecord> mActivities;
				销毁时做什么处理？
				判断Activity的状态
				回调完各种方法后
				mActivities.remove(token);
				
			wm.removeViewImmediate(v);
				mRoots
				从ViewRootImpl中拿到View
				View和ViewRootImpl解除关联
					view.assignParent(null);
					// 添加到要移除的View中  ArraySet<View> mDyingViews = new ArraySet<View>();
					mDyingViews.add(view);
				还要对Activity的Context对象进行移除
				再拿到AMS代理对象 发个消息 
				整个过程中并没有把Activity置null
				
		Activity Window WindowManager关系
			Activity 控制 MVC的C  接收事件
			View添加到Activity Window中
			添加过程
			显示过程
				Window界面显示
				WindowManager 界面管理
		
			 
			
		重识Activity					
			"页面"
			Activity本质是什么
			Activity的生命周期
				一个Activity的生命周期
				多个Activity交互的生命周期
				设计思想
				横竖屏切换
				应用场景
			启动方式
				直接启动
				匿名启动
			Activity之间的数据交互
			启动系统的Activity
			启动模式
		