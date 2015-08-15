package com.example.administrator.ex02_gson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley.FaSTHTTPUtils;
import com.github.volley.VolleyListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuchen.fast.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);

//        String url = "http://suiyuchen.qiniudn.com/show_user.txt";
        String url = "http://suiyuchen.qiniudn.com/newcourse.json";
        FaSTHTTPUtils.httpGet(url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        });
    }

    private void parseJSON(String response) {
//        ShowUser showUser = GsonUtils.parseJson(response, ShowUser.class);
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<NewCourse>>() {}.getType();
//        ArrayList<NewCourse> data = gson.fromJson(response, type);

        Type type = new TypeToken<ArrayList<NewCourse>>() {}.getType();
        ArrayList<NewCourse> data = GsonUtils.parseJson(response, type);
        mTextView.setText(data.get(2).getCoursename());
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
