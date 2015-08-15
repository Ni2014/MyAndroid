package com.example.administrator.ex03_ormactiveandroid;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Administrator on 15.4.12.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
