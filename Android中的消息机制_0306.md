#Android中的消息机制
	Handler内部是怎么得到Looper的？
		通过ThreadLocal的get();
	App
		主线程(UI线程)
		子线程(worker线程)
	在主线程中做耗时操作(网络 数据库) 3.x以上直接禁止 => ANR 会阻塞主线程
	=> 耗时操作在子线程中做，将返回结果更新到UI上 => FC(运行时异常 为啥=> 非UI线程不能更新UI)
	为啥非UI线程不能更新UI
	=> 多线程并发 如果加锁耗性能
	=> 就约定只能UI线程去更新UI
	那么 ANR和FC的两难怎么解决
	要遵守的前提就是 只有UI线程才能更新UI
	=> 那就把耗时操作执行完的结果发到UI线程 让他去更新(线程间通信)
	怎么发 谁来发 发到哪 谁来处理
	(Handler发  发到消息队列 Looper去取到并给主线程中的Handler处理，为啥Handler必须创建在主线程=> 确保handleMessage方法是执行再主线程中的)
	Handler Message  MessageQueue Looper 之间的关系
	消息循环 
	放大到整个App的主线程的消息循环
		你每个操作(事件) 都能得到反应 每个操作或者事件被封装成消息 放进消息队列中 消息驱动 主线程是有自己的消息循环的(其实每个线程都可以有自己的消息循环，和消息队列)
	消息循环怎么开启
		Looper.loop()
		Looper就一直(死循环)查看队列中是否有新消息 死循环是为了不让主线程退出
	主线程的消息循环
		ActivityThread的main方法(入口)
		// 得到Looper
		Looper.prepareMainLooper();
		// 开启(运转起来)
		Looper.loop();
	关于Handler
		Handler怎么关联到Looper 又怎么关联到MessageQueue的？ 还有 线程和消息队列怎么关联的？
		Handler的构造方法中有
			mLooper = Looper.myLooper();
        			if (mLooper == null) {
            			throw new RuntimeException(
               				 "Can't create handler inside thread that has not called Looper.prepare()");
       			 }
       			 mQueue = mLooper.mQueue;
			得到了Looper 顺带得到了MessageQueue(放在Looper中的)
			具体是怎么得到Looper的？
			 public static Looper myLooper() {
        				return sThreadLocal.get();
    			}
			从sThreadLocal中得到 那是什么时候放进去的呢？
			最开始的时候 Looper.prepareMainLooper()中
			-> prepare()
				-> sThreadLocal.set(new Looper());
			ThreadLocal的妙用和原理
				每个线程的Looper都可以是不同的 -> 同一个ThreadLocal 不同线程访问到的是不同的Looper 
				ThreadLocal<Looper> sThreadLocal
				static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();


			Handler相关的异常
				在子线程中创建Handler遇到的
				原因：子线程中没有Looper，要调用Looper.prepare();
					   没Looper 哪来的消息队列 Handler得不到消息 发往何处
				那为什么在主线程中没事呢
				因为在ActivityThread中就得到了默认的主线程的Looper
					Looper.prepareMainLooper();
			
			HandlerThread
				能用(带有)Handler的线程  在Thread的run()中封装了Handler
			IntentService
				对HandlerThread的进一步封装
				理解IntentService的设计 
				还有为什么是叫IntentService 为什么采用Intent来放消息
				onHandleIntent()  on开头但是运行在子线程中的方法
			理解从Handler到HandlerThread再到IntentService的过程
			如果你想自定义消息队列呢？
			
			Handler的用法 
				结合Message
				简单数值 what等属性
				对象数据 obj字段
				post
				postDelay方法

			话说 子线程就真的不能更新UI吗
				-> 如果真的不能 那么在运行时是谁去检查当前线程是否是子线程的？
					-> ViewRootImpl 的checkThread方法
					例子：在子线程的run方法中直接去setText();
					竟然可以，为什么
					-> ViewRootImpl对象的创建时机问题
						-> ViewRootImpl是在调用onResume后才创建的，如果更新UI的在onCreate中 ViewRootImpl对象都没来得及创建 就没报错了
			
			AsyncTask => Handler + 线程池
					线程池: 打扫房间复用的那把扫把
					基础: Callable接口(call方法)
						Future接口(get方法)  可以得到耗时操作的结果 Thread无法得到
						FutureTask 继承自 RunnableFuture(同时继承了Future和Runnable)
					
					
					execute方法为什么只能执行一次
						源码中做了状态的判断
					不同版本的实现差异
						串行 并行
					

			关于主线程的消息循环 ActivityThread的main方法中
				AsyncTask.init();
				为什么
			
			AsyncTask中的Handler			
				主线程有一个Handler
				异步操作有一个Handler
				怎么切换到主线程

			doInBackground方法是怎么被执行的？
				call方法会调用doInBackground() 所以是运行在子线程中的

			
		
			

			关于界面显示 和View  Window相关
				Activity
				DecorView
				AMS
				WMS
				Window
				PhoneWindow	
				WindowManager
					addView()
					updateView()
					removeView()
				WindowManagerImpl
					转由WindowManagerGlobal去实现addView等操作
				ViewManager继承自WindowManager
				View和ViewRootImpl
				Window的创建过程
				Activity的创建和界面显示过程
					setContentView其实调用的是window的setContentView()

				Activity界面是怎么可见和不可见的
					类似的mDecor.setVisibility(View.VISIBILE);
				
				Window和DecorView的联系
				Activity和Window的attach关联
				View和ViewRootImpl的关联
				
				onDestory 真的是销毁了Activity对象吗
					no 只是断开连接 顺便把DecorView设置为不可见
					此时Activity对象在内存中还是真实存在的

				Activity对象的创建过程
				Activity的生命周期方法是怎么被回调的
				要启动新的Activity 需要和远程的AMS沟通 -> IPC
				H Handler是用开干嘛的
			
				ActivityThread是线程吗
					不是继承自线程 只是这个类加载运行在主线程中而已
				
				自定义AsyncTask
				
				线程池


				Context是什么
					Context是抽象类
					下有ContextImpl
						ContextWrapper

					提供给组件运行时需要用到的环境
					

 				Activity背后的framework层做的管理Activity状态和生命周期函数回调
				
				消息队列 消息队列 真的是通过队列实现的吗 -> 链表
				

				Handler 把一个任务切换到handler所在线程中去执行，更新UI只是其中一个用处
				ThreadLocal  可以在每个线程中存取数据
				Handler内部怎么得到当前线程的Looper？
					通过sThreadLocal的get方法
				除了主线程 其他线程默认都是没有Looper的
				
				采用单线程模型处理UI操作 -> 简单高效
				Handler会采用当前线程的Looper来构建内部的消息循环系统

				通过Handler的post方法将一个Runnable投递到Handler的Looper中去或者send方法 其实post也是调用了send方法
				
				send的工作过程
					调用MessageQueue的enqueueMessage()放入消息队列中
					因为Looper是运行在创建Handler的线程中的，这样Handler中的业务就被切换到创建Handler所在的线程中去执行了
					Thread 1
						Handler
					Thread 2
 						MessageQueue
						Looper
					
				ThreadLocal
					一个线程内部的数据存储类
					Android源码中的应用 ActivityThread和AMS中都用到了ThreadLocal
					场景：某些数据是以线程为作用域且不同线程有不同的数据副本时  比如Handler的Looper 是不同线程都不一样的
					如果没有用ThreadLocal 系统就要提供一个类似LooperManager的全局表来管理不同线程的Looper
					第二个使用场景就是：复杂逻辑下的对象传递
					
				ThreadLocal的原理
					内部实现
					set方法
						取到当前线程
						再从当前线程中取到数据
					public void set(T value) {
        						Thread currentThread = Thread.currentThread();
       						Values values = values(currentThread);
       						 if (values == null) {
            						values = initializeValues(currentThread);
       						 }
        						values.put(this, value);
   					 }
					Values values(Thread current) {
        						return current.localValues;
    					}


				Looper
					Looper.prepare()
					Looper.prepareMainLooper()
					
					Looper.loop();  // 之后才能真正运行起来
					

			Android中的线程形态
				AsyncTask
					execute(Params)
						executeOnExecutor(Executor exec,Params params);
							onPreExecute();
							exec.execute(mFuture);
									Executor的execute(Runnable)方法
										//  最终真正执行的
										THREAD_POOL_EXECUTOR.execute(Runnable mActive);							

				AsyncTask中的两个线程池
					//  真正执行异步任务的线程池
					public static final Executor THREAD_POOL_EXECUTOR
            					= new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                    									TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

					// 排队任务的线程池
   					 /**
    					 * An {@link Executor} that executes tasks one at a time in serial
     					* order.  This serialization is global to a particular process.
    					 */
    					public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
				AsyncTask的Handler	
					InternalHandler
						用于将执行环境切换到主线程
						sHandler 在init方法中就是调用了sHandler.getLooper();
						sHandler的handleMessage()中调用了onPostExecute();
				AsyncTask的构造方法
					FutureTask mFuture
					WorkerRunnable  mWorker -> Callable
							abstract class WorkerRunnable<Params, Result> implements Callable<Result>
					FutureTask的run方法会调用mWorker的call方法
					所以call方法最终会在线程池执行？

					mWorker = new WorkerRunnable<Params, Result>() {
            					public Result call() throws Exception {
                					mTaskInvoked.set(true);

                					Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                					//noinspection unchecked
                					return postResult(doInBackground(mParams));
           					 }
       					 };
					 先把mTaskInvoked设为true 表示这个任务已经被调用 再执行doInBackground方法 返回值回传给postResult方法
					看postResult的实现
						private Result postResult(Result result) {
        							@SuppressWarnings("unchecked")
       						 	Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
                						new AsyncTaskResult<Result>(this, result));
       							message.sendToTarget();
        							return result;
    						}
					       通过sHandler发一个消息MESSAGE_POST_RESULT，sHandler收到这个消息后就会执行AsyncTask的finish方法
					private void finish(Result result) {
       						 if (isCancelled()) {
            						onCancelled(result);
        						} else {
           						 onPostExecute(result);
        						}
        						mStatus = Status.FINISHED;
    					}
					为了在3.x上能并行执行异步任务 可以用AsyncTask的executeOnExecutor方法 是3.0才添加的方法

				HandlerThread
					只是在run方法中 通过Looper.prepare()来构建消息队列并启动 在实际应用中就能在HandlerThread中创建Handler了
				HandlerThread和一般Thread的区别
					具体的使用场景是IntentService？
					外界需用通过Handler来通知执行任务？
				IntentService
					是服务 比一般的线程的优先级要高
					封装了handler和HandlerThread
					onCreate中
						创建了HandlerThread和Handler(mServiceHandler)
						通过mServiceHandler发的消息会在HandlerThread中执行
					onHandleIntent方法 需要在子类中自己实现
					启动IntentService  onStartCommand方法被调用 处理后台任务的Intent
						onStart()
							msg.obj=intent;
							mServiceHandler.sendMessage(msg);
							通过mServiceHandler发消息
							mServiceHandler(ServiceHandler)接到消息后 
								handleMessage方法中调用
									onHandleIntent(Intent);
						public void handleMessage(Message msg) {
            						onHandleIntent((Intent)msg.obj);
							// 执行后会自己结束自己
            						stopSelf(msg.arg1);
        						}
		Android中的线程池
			创建 销毁开销
			管理 控制
		java的Executor接口
			execute(Runnable)方法
			实现class ThreadPoolExecutor extends AbstractExecutorService
			class AbstractExecutorService implements ExecutorService 
			interface ExecutorService extends Executor
			// corePoolSize -> 核心线程数
			    maximumPoolSize -> 最大线程数 超过了的任务会被阻塞
			    keepAliveTime  -> 超过这个时长 非核心线程就会被回收
			    unit -> 指定keepAliveTime参数的时间单位 是枚举
			    workQueue -> 线程池中的任务队列 通过线程池的execute方法提交的Runnable对象会放在这个队列中
			public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
			    ThreadFactory threadFactory)

			看AsyncTask中对这几个参数的设置
				private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    				private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
   				private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    				private static final int KEEP_ALIVE = 1;
					
 				队列容量是 128
				private static final BlockingQueue<Runnable> sPoolWorkQueue =
               				new LinkedBlockingQueue<Runnable>(128);
			线程池的分类
			