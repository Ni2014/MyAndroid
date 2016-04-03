package com.sdkexample;

import com.sdk.SDKObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context mContext;
	private Button btn_load;
	private Button btn_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		btn_load = (Button) findViewById(R.id.btn_load);
		btn_show = (Button) findViewById(R.id.btn_show);
		btn_load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		btn_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, new SDKObject().getName(), Toast.LENGTH_LONG).show();
			}
		});
		
	}

}
