package com.example.p.ex10_;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.*;


public class MainActivity extends AppCompatActivity {

    private MyScrollView mScrollView;
    private ImageView mImageView;
    private PayOverlayLayout mPayOverlay;
    private MyRelativeLayout mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        // 消息发送者
        mScrollView = (MyScrollView) findViewById(R.id.scrollView);
        // 观察者
        mTitlebar = (MyRelativeLayout) findViewById(R.id.titlebar);
        // 观察者
        mPayOverlay = (PayOverlayLayout) findViewById(R.id.pay_overlay);

//        mTitlebar.setImageView(mImageView);
//        mPayOverlay.setImageView(mImageView);
//        java.util.Observer
//        Observable
        // mPayOverlay需要知道ScrollView滚动的距离
        // 注册
        mScrollView.addObserver(mTitlebar);
        // 注册
        mScrollView.addObserver(mPayOverlay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPayOverlay.postDelayed(new Runnable() {
            @Override
            public void run() {
                int imgHeight = mImageView.getHeight();
                mTitlebar.setMax(imgHeight);
                mPayOverlay.setMax(imgHeight);
            }
        }, 100);
    }

    //    public void onScrollChanged(int offset) {
//        // 如果滚动的距离大于图片高度，悬浮的支付组件设置为显示，否则隐藏
//        int imgHeight = mImageView.getHeight();
//        if (offset > imgHeight) {
//            mPayOverlay.setVisibility(View.VISIBLE);
//        } else {
//            mPayOverlay.setVisibility(View.INVISIBLE);
//            // 根据ScrollView滑动距离，计算标题栏背景颜色透明度的百分比
//            int percent = offset * 100 / imgHeight;
//            int bg = Color.argb((100 - percent) * 0xFF / 100, 0xFF, 0, 0);
//            mTitlebar.setBackgroundColor(bg);
//        }
//
//    }

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
