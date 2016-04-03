package com.sdkexample;

import java.io.File;

import android.content.Context;

import com.sdk.lib.FixBugException;


public class MyApplication extends com.sdk.MyApplication {
	private Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
//		mContext = getApplicationContext();
//		MyApplication application = (MyApplication) getApplicationContext();
//		File patch = new File(mContext.getFilesDir() + File.separator,
//				"patch2.jar");
//		try {
//			application.fixBugManage.addPatch(patch.getAbsolutePath());
//		} catch (FixBugException e) {
//			e.printStackTrace();
//		}
	}
	}
