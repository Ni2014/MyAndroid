package com.sdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.sdk.lib.FixBugException;
import com.sdk.lib.FixBugManage;

public class MyApplication extends Application {

    public FixBugManage fixBugManage;
    private Context mContext;
    private static String DEX_URL = "https://github.com/Ni2014/MyAndroid/blob/master/dex/patch_2.jar?raw=true";
    private static String DEX_NAME = "patch2.jar";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        downloadDex();
        try {
            this.fixBugManage = new FixBugManage(this);
            this.fixBugManage.init("1.0");
        } catch (FixBugException e) {
            e.printStackTrace();
        }
		MyApplication application = (MyApplication) getApplicationContext();
		File patch = new File(mContext.getFilesDir() + File.separator,
				"patch2.jar");
		try {
			application.fixBugManage.addPatch(patch.getAbsolutePath());
		} catch (FixBugException e) {
			e.printStackTrace();
		}
    }

    private void downloadDex() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        URL url = null;
                        try {
                            url = new URL(DEX_URL);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();
                            int fileOfLength = conn.getContentLength();
                            Log.e("size", "" + fileOfLength);
                            int length = 0;
                            InputStream in = conn.getInputStream();
                            FileOutputStream fos = new FileOutputStream(new File(
                                    mContext.getFilesDir() + File.separator + DEX_NAME));
                            byte[] buff = new byte[2 * 1024];
                            while ((length = in.read(buff)) != -1) {
                                fos.write(buff, 0, length);
                            }
                            Log.e("download", "OK");
                            fos.flush();
                            fos.close();
                            in.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
