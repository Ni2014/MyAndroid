###Android5.0新增控件——Toolbar
###ToolbarDemo
**1. Android Studio的Material风格App模板**

[https://github.com/kanytu/Android-studio-material-template](https://github.com/kanytu/Android-studio-material-template)

![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/D66F380DAEDE4215A105F6ADC135A9FC)

####2. Toolbar基本功能实现步骤：
1) 在布局中添加Toolbar控件
   ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/D44AED6F88D8468B972AA625B128D433) 

2) 修改Activity继承AppCompatActivity
  ![http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/19A93CF3BBF648ADBE5574C13D8A2E98](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/19A93CF3BBF648ADBE5574C13D8A2E98)

3) 代码设置使用Toolbar代替Actionbar
  ![http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/AB8F5951076C445CA3A3D7F392974E6E](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/AB8F5951076C445CA3A3D7F392974E6E)

4) 修改系统主题
  ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/2D230ECD1C964CC594D93F1132186750)

经过以上步骤Toolbar就可以显示了

####3. 代码设置Toolbar属性和菜单事件
1) Toolbar更多属性和菜单事件
 ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/26F6C2C1438F4512882A80B5D9306168)
2) NavigationIcon事件
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/FD1D763206D346BE8DA54271E19565F3)
3) 通过主题修改更多Toolbar属性
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/055923CEC6F64DDB83DA46106B00D1D8)
运行效果图：
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/A6FADFA2EDC94A2192F2157C580577E7/77160E4A8DBC44F6A662DDDD889DA534)

附：关键代码配置文件参考
   ` // App Logo`

    toolbar.setLogo(R.drawable.ic_launcher);
    // Navigation Icon 要設定在 setSupoortActionBar 才有作用
    // 否則會出現 back bottom
    toolbar.setNavigationIcon(R.drawable.ab_android);
    // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
    toolbar.setOnMenuItemClickListener(onMenuItemClick);
    <item name="colorPrimary">@color/accent_material_dark</item>
    <item name="colorPrimaryDark">@color/accent_material_light</item>
    <item name="android:windowBackground">@color/dim_foreground_material_dark</item>
    <android.support.v7.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    android:layout_width="match_parent">
    </android.support.v7.widget.Toolbar>