#《Android群英传》读书笔记
##第一章：Android体系与系统架构
###1.Dalvik与ART<br>
前者是运行时编译，后者是安装时编译，在5.x中取代了前者<br>
###2.四大组件如何协同工作呢？<br>
简单说就是通过Intent串起来了<br>
###3.系统源代码目录和系统目录<br>
  
  3.1Android源代码结构<br>
  -Makefile<br>
  -bionic			(bionic C库)<br>
  -bootable			(启动引导相关代码)<br>
  -build		   		(存放系统编译规则等基础开发包设置)<br>
  -cts          		(Google兼容性测试标准)<br>
  -dalvik			(虚拟机)<br>
  -development  		(App开发相关)<br>
  -external			(android用的开源的模块)<br>
  -frameworks		(框架核心)<br>
  -hardware			(HAL代码)<br>
  -out				(编译完成后的代码输出目录)<br>
  -package			(App包)<br>
  -prebuilt			(x86和arm架构下预编译资源)<br>
  -sdk				(sdk及模拟器)<br>
  -system			(底层文件系统库,应用和组件)<br>
  -vendor			(厂商定制代码)<br>
  tips:makefile机制，可以指定编译哪个模块
  
  3.2 Android系统目录<br>
  不同于Android源代码的目录如下,其中/system和/data是我们最应该关心的<br>
  /system/app/<br>
  放的是系统的App<br>
  /system/bin/<br>
  放的是linux自带的组件<br>
  /system/build.prop<br>
  放的是系统的属性信息
  /system/fonts/<br>
  系统字体，root后可以用ttf格式替换<br>
  /system/framework/<br>
  系统的核心文件，框架层<br>
  /system/lib/<br>
  存放几乎所有的共享库(.so)文件<br>
  /system/media/<br>
  保存系统提示音，系统铃声<br>
  /system/usr/<br>
  保存用户配置文件，键盘布局，时区文件等<br>
  /data/app/<br>
  data下放了用户的大部分数据信息，/data/app/放了用户安装的或者升级的app<br>
  /data/data/<br>
  是开发者访问最多的了，下面有App的数据信息，文件信息，数据库信息等，以包名的形式来区分<br>
  /data/system/<br>
  包含了手机的各项系统信息<br>
  /data/misc/<br>
  保存了大部分的wifi，vpn信息<br>
  3.3 Android App文件目录<br>
  
 
##第三章：Android控件架构与自定义控件详解
###3.1 控件的分类：①View控件；②ViewGroup控件  
   通过ViewGroup，整个界面上的控件形成了一个树形结构，就是控件树，上层控件负责下层子空间的测量和绘制，并传递交互事件，在Activity的findViewById()方法，就是在控件树中以树的深度优先遍历来查找对应的元素。每个控件树的顶部都有一个ViewParent对象，是整棵树的核心，所有的交互管理事件都由他来统一调度和分配。那么，setContentView()做了什么事情呢<br>
   每个Activity都有一个Window对象(Android中通常是由PhoneWindow实现)，PhoneWindow将一个DecorView设置成整个应用窗口的根View，作为窗口界面的顶层视图，DecorView包含了TitleView和ContentView,这个ContentView是个FrameLayout。<br>
   在代码中，当在onCreate()中调用setContentView方法时，AMS会回调onResume()方法，此时系统才会把整个DecorView添加到PhoneWindow中，让其显示，最终完成界面绘制。<br>
###3.2 View的测量<br>
    场景：要知道具体定点和控件大小你才能进行绘制吧，告诉系统这个View是多大，这个过程在onMeasure()中进行<br>
    一个帮助我们测量View的类=>MeasureSpec类，有3种测量模式：<br>
	(1)EXACTLY<br>
	   当给空间指定具体多少dp的宽高时<br>
	(2)AT_MOST<br>
	   当给控件的宽高指定为wrap_content时<br>
	(3)UNSPECIFIED<br>
       在绘制自定义View时会用<br>
       View类默认的onMeasure()方法只支持EXACTLY模式，所以在自定义控件没有重写onMeasure()的话就只能用EXACTLY模式了，也就是说如果想让你自定义的控件支持wrap_content属性的话你就要重写onMeasure()来指定wrap_content时的大小了<br>
###3.3 View的绘制<br>
    测量好一个View后，重写onDraw(Canvas canvas),一般把Bitmap传到Canvas的构造方法中<br>
    疑问：onDraw()中指定的画布是在哪里呢？<br>
###3.4 ViewGroup的测量<br>
    ViewGroup要管理子View的显示大小，当ViewGroup的大小为wrap_content时，ViewGroup就要对子View进行遍历，取得所有子View的大小从而决定自己的大小，遍历子View时会调用子View的onMeasure()得到每一个测量结果，Layout布局过程也是会调用子View的onLayout()<br>
###3.5 ViewGroup的绘制<br>
    通常不需要绘制，除非是指定了ViewGroup的背景颜色，否则ViewGroup的onDraw()不会被调用，但是ViewGroup会使用dispatchDraw()来绘制子View，同样是遍历所有子View，并利用子View的绘制方法完成绘制工作。<br>
###3.6 自定义View<br>
    如果你决定要自定义View了，你需要做的几点：<br>
	(1)重写onDraw()来绘制View的显示内容；<br>
	(2)如果该控件还要使用wrap_content属性，必须重写onMeasure()；<br>
	(3)另外，通过自定义attr属性可以设置新的属性配置值；<br>
    而在View中有几个重要的回调方法：<br>
	(1)onFinishInfalte(),从xml加载组件后回调;<br>
	(2)onSizeChanged(),组件大小改变时回调;<br>
	(3)onMeasure();<br>
	(4)onLayout(),确定显示的位置;<br>
	(5)onTouchEvent(),监听触摸事件时回调;<br>
	通常有三种方式来实现自定义控件:<br>
	(1)对现有控件进行扩展;<br>
	(2)通过组合来实现新的控件;<br>
	(3)重写View来实现全新的控件;<br>
3.6.1 对现有控件进行扩展;<br>
	一般是在onDraw()中对原生控件进行扩展；<br>
3.6.2 创建复合控件<br>
	一般要继承一个合适的ViewGroup，再给他添加一个指定功能的控件，让他具有更强的扩展性<br>
	例子：TopBar<br>
	(1)自定义属性<br>
	   在res/values下创建一个attrs.xml的属性定义文件；<br>
	(2)新建一个继承自ViewGroup(可以选RelativeLayout)的类,并且获取自定义的属性,系统用了TypedArray的数据结构来获取属性集<br>
	` TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TopBar);`<br>
	(3)组合控件<br>
	   ①定义接口；<br>
	   ②暴露接口给调用者；<br>
	   ③实现接口回调；<br>
	(4)引用UI模板<br>
	   需指定命名空间<br>
3.6.3 重写View来实现全新的控件<br>
###3.7 自定义ViewGroup<br>
###3.8 事件拦截机制分析<br>
3.8.1 什么是触摸事件以及对应的MotionEvent类<br>
    ViewGroup级别最高，比View多了一个方法=>onInterceptTouchEvent(),事件传递和处理的顺序刚好相反<br> 
##第四章：ListView使用技巧
###4.1.1 使用ViewHolder模式提高效率，是ListView的视图缓存机制，避免每次调getView的时候都去findViewById()，方式就是在自定义的Adapter里面写个内部类ViewHolder<br>
tip1:设置项目间分隔线和线的高度<br>
android:divider(如果透明就是@null)<br>
android:dividerHeight<br>
tip2:隐藏滚动条<br>
android:scrollbars:"none"<br>
tip3:取消Item的点击效果<br>
android:listSeletors="#00000000"或者android:listSeletors="@android:color/transparents"<br>
tip4:设置ListView需要显示在第几项<br>
listView.setSelection(N)(注意:这个方法类似scrollTo,是瞬间完成的移动)<br>
以下代码实现平滑移动：<br>
listView.smoothScrollBy(distance,duration)<br>
listView.smoothScrollByOffset(offset)<br>
listView.smoothScrollToPosition(index)<br>
tip5：动态修改ListView<br>
mAdapter.notifyDataSetChanged()<br>
tip6:遍历所有的item<br>
listView.getChildAt(i)<br>
tip7:处理空的ListView<br>
listView.setEmptyView(resId)<br>
tip8:ListView的滑动监听<br>
(1)通过OnTouchListener(是View中的监听事件)，判断MotionEvent对象的事件(ACTION_UP,ACTION_DOWN,ACTION_MOVE)<br>
(2)通过OnScrollListener(是AbsListView中的监听事件)，两个回调方法,onScrollStateChanged()和onScroll(),,onScrollStateChanged()的参数是int scrollState，有三种状态模式：①SCROLL_STATE_IDLE(滚动停止时)②SCROLL_STATE_TOUCH_SCROLL(正常滚动时)③SCROLL_STATE_FLING(手指抛动时，就是用力滑动，在离开后ListView由于惯性继续滑动的状态)<br>
扩展<br>
1.具有弹性的ListView<br>
2.自动显示和隐藏的ListView<br>
3.聊天的ListView<br>
4.动态改变ListView的布局<br>
##第五章：Android Scroll分析
###5.1 滑动效果是如何产生的<br>
	5.1.1 Android坐标系<br>
	5.1.2 视图坐标系<br>
	      和Android坐标系相比区别只是在于坐标的原点是在父视图的左上角，而非屏幕最左上角<br>，在触控事件中，通过getX(),getY()获取的就是视图坐标系中的坐标<br>
	5.1.3 触控事件MotionEvent<br>
		  获取坐标的api方法总结<br>
		  (1)View提供的获取坐标方法<br>
	      ①getTop();取到的是View自身的顶边到其父布局顶边的距离<br>
	      ②getLeft();取到的是View自身的顶边到其父布局左边的距离<br>
		  ③getRight();取到的是View自身的顶边到其父布局右边的距离<br>
	      ④getButtom();取到的是View自身的顶边到其父布局底边的距离<br>
		  (2)MotionEvent提供的方法<br>
		  ①getX();获取点击事件距离控件左边的距离，即视图坐标<br>
	      ②getY();获取点击事件距离控件顶边的距离，即视图坐标<br>
		  ③getRawX();获取点击事件距离整个屏幕左边的距离，即绝对坐标<br>
	      ④getRawY();获取点击事件距离整个屏幕左边的距离，即绝对坐标<br>
###5.2 实现滑动的7种方法<br>
	如何使用系统的Api实现动态修改一个View的坐标，即实现滑动效果。<br>
	思路：当触摸时，系统记下当前触摸点的坐标；移动时，记下移动后的触摸点坐标，取到偏移量，通过这个偏移量来修改View的坐标，不断这样重复就实现了滑动过程。<br>
    5.2.1 layout方法<br>
	      (1)在ACTION_DOWN事件中记录触摸点的坐标，通过event.getX()和event.getY();<br>(2)在ACTION_MOVE事件中计算偏移量；<br>
	      (3)把偏移量传到layout方法中；<br>
    5.2.2 offsetLeftAndRight()与offsetTopAndButtom()<br>
          相当于系统提供的一个对左右和上下移动的API的封装，使用简单，直接传入偏移量；<br>
	5.2.3 Layoutparams<br>
		  Layoutparams保存了一个View的布局参数，所以可以在代码中，通过改变Layoutparams来动态的修改一个布局的位置参数，从而达到改变View位置的效果。<br>
		  在代码中可以通过getLayoutparams()来获取一个View的Layoutparams，偏移量的获取同layout的方法，然后通过setLayoutparams改变其Layoutparams。<br>
	5.2.4 scrollTo和scrollBy<br>
          在一个View中，系统提供了这两个方法来改变一个View的位置，区别很简单<br>
	      scrollTo(x,y)=>移动到一个点(x,y)<br>
		  scrollBy(dx,dy)=>移动的增量是dx和dy<br>
          注意如果是在ViewGroup中，调用者应该是这个ViewGroup<br>，注意要将增量设置成负值<br>
	5.2.5 Scroller<br>
          它与scrollTo()和scrollBy()有什么区别呢？<br>
          场景：scrollTo()和scrollBy()的移动都是瞬间产生的，需要有平滑动画的特殊效果，所以Scroller就出来了<br>
	      它的实现原理就是在move事件中，不断调用那两个方法，粒度较细，传的是很多个微小的增量，所以看起来就是平滑的了(类似动画的原理)<br>
	      用法(步骤)<br>
	      (1)初始化Scroller;<br>
			 `mScroller = new Scroller(context);`<br>
		  (2)重写computeScroll()方法，实现模拟滑动l;<br>
	 <pre> 
	@Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }
     </pre>
   系统在绘制View时会在draw()中调用该方法，实际使用的就是scrollTo(),注意invalidate()，因为computeScroll()不会自动调用，只能通过注意invalidate()=>draw()=>computeScroll()来间接调用;
(3)startScroll开启模拟过程<br>
<pre>
	mScroller.startScroll(
                    viewGroup.getScrollX(),
                    viewGroup.getScrollY(),
                    -viewGroup.getScrollX(),
                    -viewGroup.getScrollY());
</pre>
   5.2.6 ps:用属性动画同样可以达到这种效果<br>
   5.2.7 ViewDragHelper<br>
         google在support包中提供的DrawerLayout和SlidingPaneLayout具有滑动效果，他们就是出自一个叫ViewDragHelper的类(功能强大，用法复杂)<br>
         步骤：<br>
	     (1)初始化ViewDragHelper;<br>
			` mViewDragHelper = ViewDragHelper.create(this, callback);`<br>
		 (2)拦截事件；<br>
	<pre>
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }
	</pre>
	<pre>
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给ViewDragHelper,此操作必不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
   </pre>
        (3)处理computeScroll()；<br>
		   因为ViewDragHelper内部也是通过Scroller来实现平滑移动的<br>
        (4)处理回调callback；<br>
           
##第六章：Android绘图机制与处理I技巧
###1.屏幕的尺寸信息<br>
  1.1 屏幕参数<br>
      大小，分辨率，PPI<br>
  1.2 系统屏幕密度<br>
	  ldpi，mdpi,hdpi,xhdpi,xxhdpi<br> 
  1.3 独立像素密度<br>

###2.2D绘图基础<br>
###3.xml绘图<br>
###4.绘图技巧<br>
###5.色彩特效处理<br>
###6.图形特效处理<br>
###7.画笔特效处理<br>
###8.View的孪生兄弟=>SurfaceView<br>
##第八章：Activity与Activity调用栈分析
####8.1.1 Activity的形态<br>
	  (1)Active/Running<br>
	     此时，Activity处于Activity栈的最顶层，可见，并与用户进行交互；<br>
      (2)Paused<br>
         失去焦点，被一个非全屏的或者透明的Activity放在栈顶时，原来的这个Activity就失去了与用户交互的能力，系统内存极低时就会被回收；<br>
      (3)Stopped<br>
         被完全覆盖，不再可见，却依旧保持了所有的状态信息和成员变量；<br>
      (4)Killed<br>
         被系统回收，无法控制；<br>
####8.1.2 生命周期状态<br>
	  (1)Resumed<br>
	     就是Active/Running状态<br>
	  (2)Paused<br>
         部分被挡住，该状态下Activity不会接收用户输入<br>
      (3)Stopped<br>
         被完全覆盖，不可见，仅仅在后台运行<br>
8.1.2.1 Activity启动和销毁的过程<br>
      系统调用onCreate()后，会马上调用onStart(),接着调用onResume()进入Resume状态，最后就停在了Resume状态，完成启动。系统会调用onDestory()来结束一个Activity的生命周期让他回到Killed形态。<br>
      onCreate()=>创建基本的UI元素<br>
      onPause()与onStop()=>清楚Activity的资源，避免浪费<br>
      onDestory()中，因为引用在Activity销毁时销毁，但是线程不会，所以清除开启的线程。<br>
8.1.2.2 Activity暂停与恢复过程<br>
      当栈顶的Activity部分不可见后会导致这个Activity进入Pause形态，会调用onPause()，当线程结束后就调用onResume()来恢复到Resume状态<br>
      onPaused()=>释放系统资源，如Camera，sensor、receivers<br>
      onResume()=>需要重新初始化在onPause()中释放的资源<br>
8.1.2.3 Activity的停止过程<br>
      当Activity部分不可见时有两种可能，(1)从部分不可见到可见，就是恢复过程；(2)从部分不可见到完全不可见，就是停止过程，但都会掉onPause()<br>
8.1.2.4 Activity的重新创建过程<br>
      onSaveInstanceState(),将状态信息保存到Bundle对象中，这就是onCreate()方法中Bundle savedInstanceState的来源；<br>
      注意：onSaveInstanceState()方法并非每次Activity离开前台都会调用的，如果用户使用finish()结束了Activity就不会调用，并且Android系统已经默认实现了控件的状态缓存，简化开发者需要实现的缓存逻辑；<br>
###8.2 Activity任务栈简介<br>
      TaskStack,Last In First Out<br>
      改变：(1)Mainifest中的android:launchMode<br>
           (2)Intent的flag<br>
###8.3 AndroidManifest启动模式<br>
       (1)standard；<br>
          默认的启动模式<br>，每次都会创建新的Activity实例<br>
	   (2)singleTop；<br>
          如果栈顶的Activity是你要启动的Activity就不会新创建重复的；<br>
	   (3)singleTask；<br>
          和singleTop的区别，singleTop是查看栈顶元素是否是需要启动的Activity，而singleTask是查整个Activity栈是否存在当前需要启动的Activity；<br>
    	  如果是在同一个App中跳转，如果栈中已经存在需要启动的Activity，那么就将这个Activity放到栈顶并将该Activity之上的Activity全部销毁；如果是其他的Activity来启动的话呢就会新建一个新的任务栈；<br>
          注意：如果现在是A-B,B要启动E(E用了这种模式)，在E所在的栈中的情况是F-E，那么E所在的栈到了前台后，单击返回是会到F的，过程就是A-B=>(B启动了E,记住中间隔了个F)A-B-F-E(back)=>A-B-F(back)=>A-B<br>
          用法：这种启动模式创建的Activity不是在新的任务栈中被打开就是将已打开的Activity切换到前台，所以可以用在推出整个应用的场景，将主Activity设置为这种模式，在要退出的Activity转到主Activity，然后主Activity之上的都会被清除，再重写主Activity的onNewIntent(),在方法中加上finish(),将最后一个Activity结束掉；<br>
	   (4)singleInstance；<br>
          这种模式和浏览器工作原理类似，一个新的任务栈并且这个栈中只有这一个Activity，被其他的App共享，这种启动模式常用于需要与程序分离的界面<br>
       说明：如果是在singleTop和singleInstance两种模式中一个Activity通过startActivityForResult()，那么系统直接返回Activity.RESULT_CANCELED,因为系统在framework层做的限制，因为不同的应用间默认是不能传数据的，如果一定要传的话，就只能通过Intent来绑定数据<br>
###8.4 Intent Flag 启动模式<br>
       常用的Flag<br>
       (1)Intent.FLAG_ACTIVITY_NEW_TASK<br>
          用一个新的栈，启动的每个Activity都会在一个新的栈中，通常的使用场景是在Service启动Activity，因为Service不在Activity的栈中<br>
       (2)FLAG_ACTIVITY_SINGLE_TOP<br>
          singleTop<br>
	   (3)FLAG_ACTIVITY_CLEAR_TOP<br>
          singleTask<br>
	   (4)FLAG_ACTIVITY_NO_HISTORY<br>
          比如：A-B,B用这种模式启动了C，C再去启动D的话，最后的栈的情况就是A-B-D;<br>
###8.5 清空任务栈<br>
       将一个Task全部清除，一般是在Manifest中的activity标签下指定属性<br>
       (1)clearTaskOnLaunch<br>
          就是每次返回该Activity的时候都将该Activity之上的所有Activity都清除，可以让栈在每次初始化时都只有这一个Activity；<br>
       (2)finishOnTaskLaunch<br>
          与clearTaskOnLaunch类似，只是clearTaskOnLaunch作用在别人身上，而finishOnTaskLaunch是作用在自己身上，通过这个属性，当离开这个Activity所在的栈，用户在返回时，该Activity就会被finish掉；<br>
       (3)alwaysRetainTaskState<br>
          给Task的“一道免死金牌”，指定了该属性的Activity所在的栈将不再接受任何清理命令，一直保持当前Task状态；<br>
###8.6 Activity任务栈使用<br>
##第十章：Android性能优化
###1.布局优化<br>
1.1 Android UI渲染机制<br>
    Android中，系统通过VSYNC信号触发对UI的渲染和重绘,间隔时间是16ms(1秒显示60帧=>1000ms/60),也就是说当你不能再16ms内完成绘制，就丢帧卡顿了<br>
    Android中提供的检测UI渲染时间的工具，Profile GPU Rendering，选中On Screen as bars<br>
1.2 避免Overdraw<br>
    检测工具=>Enable GPU Overdraw(要增大蓝色区域，减小红色区域)<br>
1.3 优化布局层级<br>
    Android中在测量，布局和绘制时，本质上都是对View树的遍历，如果树的高度太高就应该优化下了，Google在API文档中建议View树的层数不要超过10<br>
    一个细节：低版本中默认布局的实现是LinearLayout作为xml文件的根布局，现在是RealtiveLayout。<br>
1.4 避免嵌套过多无用的布局<br>
    (1)用<include>重用Layout，比如公共UI<br>
	(2)用<ViewStub>实现View的延迟加载<br>
       怎么让它可见呢：①setVisibility(View.VISIBLE);②inflate(),可以返回布局<br>
       tip：和设置view的GONE的区别：<ViewStub>是只有在显示时才会去渲染布局，而View.GONE在初始化布局就加到了布局树下了，所以<ViewStub>更加高效<br>
	(3)<merge>标签<br>
1.5 Hierarchy View<br>
###2.内存优化<br>
2.1 什么是内存<br>
	因为Android应用的沙箱机制，每个App分到的内存是有限的，太低就会触发LMK(Low Memory Kill)机制，内存通常是指的RAM，包括有：<br>(1)寄存器；在cpu内部，程序无法控制<br>(2)栈；放基本类型的数据和对象的引用<br>(3)堆；放new出来的对象和数组，由GC回收器管理<br>(4)静态存储区域；<br>(5)常量池；包括直接常量和符号引用<br>
    所谓内存分析，就是分析Heap中的内存状态<br>
    在代码中获取堆的大小
    `ActivityManager manager  = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);`
	`int heapSize = manager.getLargeMemoryClass();`<br>
2.2 获取Android系统内存信息<br>
2.3 内存回收<br>
2.4 实例<br>
    2.4.1 Bitmap优化<br>
    (1)使用适当分辨率和大小的图片<br>(2)及时回收内存<br>bitmap.recycle(),3.0后因为Bitmap的全被放到了堆中，就不用这样手动回收了<br>(3)使用图片缓存<br>LruCache和DiskLruCache<br>
	2.4.2 代码优化<br>
    任何一个java类，都会占用500字节，创建一个类的实例会耗15字节<br>
	(1)对常量用static修饰符<br>
	(2)使用静态方法，静态方法比普通方法提高15%的访问速度<br>
	(3)如果可以是局部变量就不要定义为成员变量，Lint工具<br>
	(4)较少不必要的对象，也应该避免频繁创建短作用域的变量<br>
	(5)尽量不要使用枚举，少用迭代器<br>
	(6)对Cursor，Reeceiver，Sensor，File等对象，要非常注意他们的创建，回收和注册及解注册<br>
	(7)避免使用IOC框架，大量使用反射带来的性能下降<br>
	(8)使用RenderScript和OpenGL进行复杂的绘图操作<br>
	(9)用SurfaceView代替View来进行大量和频繁的绘图操作<br>
	(10)尽量使用视图缓存，而不是每次都执行inflate()解析视图<br>
###3.Lint工具<br>
###4.用AS中的Memory Monitor工具<br>
###5.用TraceView工具优化App的性能<br>
###6.用MAT工具分析App内存状态<br>
###7.用Dumpsys命令分析系统状态<br>


##第十一章：搭建云端服务器

##第十二章：Android 5.X 新特性
