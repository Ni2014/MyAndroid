package com.example.p.ex11_iocandroid;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuchen.fast.util.XMLUtils;


public class MainActivity extends ActionBarActivity {
    public void onClick(View v){
        // 读取XML中配置的类名
        String className = XMLUtils.getStringFromXML(this, "activity", R.xml.ioc);

//        String className = "com.example.p.ex11_iocandroid.SecondActivity";
        // 根据类名创建对象
        try {
            Class<Activity> aClass = (Class<Activity>) Class.forName(className);
            Activity activity = (Activity) aClass.newInstance();
//            activity.onCreate(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
