#《Android群英传》读书笔记
##第一章：Android体系与系统架构
1.Dalvik与ART<br>
前者是运行时编译，后者是安装时编译，在5.x中取代了前者<br>
2.四大组件如何协同工作呢？<br>
简单说就是通过Intent串起来了<br>
3.系统源代码目录和系统目录<br>
  
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
3.1控件的分类：①View控件；②ViewGroup控件  
   通过ViewGroup，整个界面上的控件形成了一个树形结构，就是控件树，上层控件负责下层子空间的测量和绘制，并传递交互事件，在Activity的findViewById()方法，就是在控件树中以树的深度优先遍历来查找对应的元素。每个控件树的顶部都有一个ViewParent对象，是整棵树的核心，所有的交互管理事件都由他来统一调度和分配。那么，setContentView()做了什么事情呢<br>
   每个Activity都有一个Window对象(Android中通常是由PhoneWindow实现)，PhoneWindow将一个DecorView设置成整个应用窗口的根View，作为窗口界面的顶层视图，DecorView包含了TitleView和ContentView

 
##第四章：ListView使用技巧
4.1.1 使用ViewHolder模式提高效率，是ListView的视图缓存机制，避免每次调getView的时候都去findViewById()，方式就是在自定义的Adapter里面写个内部类ViewHolder<br>
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
##第六章：Android绘图机制与处理I技巧
1.屏幕的尺寸信息<br>
  1.1 屏幕参数<br>
      大小，分辨率，PPI<br>
  1.2 系统屏幕密度<br>
	  ldpi，mdpi,hdpi,xhdpi,xxhdpi<br> 
  1.3 独立像素密度<br>

2.2D绘图基础<br>
3.xml绘图<br>
4.绘图技巧<br>
5.色彩特效处理<br>
6.图形特效处理<br>
7.画笔特效处理<br>
8.View的孪生兄弟=>SurfaceView
##第十章：Android性能优化
1.布局优化<br>
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
2.内存优化<br>
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
3.Lint工具<br>
4.用AS中的Memory Monitor工具<br>
5.用TraceView工具优化App的性能<br>
6.用MAT工具分析App内存状态<br>
7.用Dumpsys命令分析系统状态<br>


##第十一章：搭建云端服务器

##第十二章：Android 5.X 新特性
