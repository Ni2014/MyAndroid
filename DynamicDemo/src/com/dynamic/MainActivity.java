package com.dynamic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.example.dymanicdemo.R;

import dalvik.system.DexClassLoader;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btn_dex;
	private Context mContext;
	private static final String DEX_URL = "https://github.com/Ni2014/MyAndroid/blob/master/dex/dynamic_dex_lasted.jar?raw=true";
	private static final String DEX_NAME = "dynamic_dex_lasted.jar";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		btn_dex = (Button) findViewById(R.id.btn_dex);
		btn_dex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DexTask().execute(DEX_URL);
			}
		});
	}

	class DexTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				downloadDex();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
//			loadDex();
		}
	}

	private void downloadDex() throws MalformedURLException, IOException,
			ProtocolException, FileNotFoundException {
		URL url = new URL(DEX_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int length = connection.getContentLength();
		Log.d("size", length + "");
		int len = 0;
		InputStream in = connection.getInputStream();
		FileOutputStream fos = new FileOutputStream(new File(
				mContext.getFilesDir() + File.separator + DEX_NAME));
		byte[] buff = new byte[2*1024];
		while ((len = in.read(buff)) != -1) {
			fos.write(buff, 0, len);
		}
		Log.e("download", "ok");
		fos.flush();
		fos.close();
		in.close();
	}

	private void loadDex() {
		File dexPath = new File(mContext.getFilesDir() + File.separator
				+ DEX_NAME);
		DexClassLoader loader = new DexClassLoader(dexPath.getAbsolutePath(),
				mContext.getCacheDir()+File.separator, null, getClassLoader());
		Class clazz = null;
		try {
			clazz = loader.loadClass("com.dynamic.impl.Dynamic");
			Log.d("clazz", clazz.toString());
			Method method = clazz.getDeclaredMethod("doThing");
			method.setAccessible(true);
			String result = (String) method.invoke(clazz.newInstance());
			Log.e("result", result);
			Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
