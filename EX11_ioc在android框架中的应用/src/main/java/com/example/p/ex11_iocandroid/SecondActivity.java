package com.example.p.ex11_iocandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SecondActivity extends Service {
    public SecondActivity(){
        Log.e("SecondActivity", "构造方法");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
    }


}
