package com.dynamic;

import java.io.File;

import android.app.Application;

public class FixApplication extends Application {
	private  String path1 = getApplicationContext().getCacheDir()+File.separator;
	private  String path2 = getApplicationContext().getCacheDir()+File.separator+"out/";
	@Override
	public void onCreate() {
		super.onCreate();
		DexLoaderUtil.injectAboveEqualApiLevel14(path1, path2, null, "com.dynamic.impl.Dynamic");
		DexLoaderUtil.call(getClassLoader());
		
	}
}
