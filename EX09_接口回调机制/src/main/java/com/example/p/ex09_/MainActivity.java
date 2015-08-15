package com.example.p.ex09_;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity implements MyScrollView.OnScrollChangedListener {

    private MyScrollView mScrollView;
    private ImageView mImageView;
    private View mPayOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = (MyScrollView) findViewById(R.id.scrollView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mPayOverlay = findViewById(R.id.pay_overlay);
        // mPayOverlay需要知道ScrollView滚动的距离
        mScrollView.setOnScrollChangedListener(this);
    }

    @Override
    public void onScrollChanged(int offset) {
        // 如果滚动的距离大于图片高度，悬浮的支付组件设置为显示，否则隐藏
        int imgHeight = mImageView.getHeight();
        if (offset > imgHeight){
            mPayOverlay.setVisibility(View.VISIBLE);
        }else{
            mPayOverlay.setVisibility(View.INVISIBLE);
        }


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
