#理解Activity的工作过程
	Activity
		启动 
		startActivity
			startActivityForResult方法
				mMainThread.getApplicationThread();
				ApplicationThread是什么  一个ActivityThread中的内部类
				mInstrumentation.execStartActivity 方法
					启动Activity由ActivityManagerNative.getDefault().startActivity方法实现
						AMS继承ActivityManagerNative
						ActivityManagerService extends ActivityManagerNative
						ActivityManagerNative
						// 熟悉的Binder
						class ActivityManagerNative extends Binder implements IActivityManager
						AMS也是一个Binder 是IActivityManager的具体实现
						启动Activity的事情转移到了AMS中 看AMS的.startActivity方法
						先看Instrumentation的execStartActivity方法中的checkStartActivityResult(result, intent);
								case ActivityManager.START_CLASS_NOT_FOUND:
								// 熟悉的异常
									 if (intent instanceof Intent && ((Intent)intent).getComponent() != null)
											throw new ActivityNotFoundException(
												 "Unable to find explicit activity class "
													+ ((Intent)intent).getComponent().toShortString()
														+ "; have you declared this activity in your AndroidManifest.xml?");
						看AMS的startActivity方法
							-> startActivityAsUser
								-> mStackSupervisor.startActivityMayWait方法
									-> startActivityLocked方法
										-> startActivityUncheckedLocked方法
											-> ActivityStack的resumeTopActivitiesLocked方法
							从ActivityStackSupervisor转移到了ActivityStack
							// 待续
						
						H Handler对消息的处理
							对LAUNCH_ACTIVITY消息的处理 调用ActivityThread的handleLaunchActivity方法
							-> performLaunchActivity方法
		最终追踪到ActivityThread的performLaunchActivity方法
		performLaunchActivity
			1. 从ActivityClientRecord中获取启动的Activity的组件信息
			2. 通过Instrumentation的newActivity方法使用类加载器创建Activity对象
			3. 通过LoadedApk的makeApplication方法创建Application对象
				Application对象是通过Instrumentation来创建的
				创建后 通过Instrumentation的callApplicationOnCreate()调用Application的onCreate方法
			4. 创建ContextImpl对象并通过Activity的attach方法来完成一些重要数据的初始化
				ContextImpl是很重要的数据结构 是Context的具体实现 Context中的大部分逻辑是通过ContextImpl实现的
				ContextImpl是通过Activity的attach方法和Activity关联的
				另外 在attach中 还完成了Window的创建并建立Activity和Window的关联 Window接受到外部输入事件后就可以把事件传给Activity
			5. 调用Activity的onCreate方法
				mInstrumentation.callActivityOnCreate();
				当Activity的onCreate被调用 就完成了Activity的启动过程