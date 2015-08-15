package com.yuchen.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley.FaSTHTTPUtils;
import com.github.volley.VolleyListener;

public class MainActivity extends Activity {

    private TextView mTvResult;
    private View mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvResult = (TextView) findViewById(R.id.textView);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequest();
            }
        });
    }

    private void httpRequest() {
        String strUrl = "http://suiyuchen.qiniudn.com/newcourse.json";
        FaSTHTTPUtils.httpGet(strUrl, new VolleyListener() {
            @Override
            public void onResponse(String response) {
                mTvResult.setText(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mTvResult.setText(error.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
