package com.example.p.ex10_;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by P on 2015/6/16.
 */
public class MyRelativeLayout extends RelativeLayout implements  Observer{
    private int max;
//    private ImageView mImageView;

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setMax(int max){
        this.max = max;
    }

    @Override
    public void update(int offset) {
        // 根据ScrollView滑动距离，计算标题栏背景颜色透明度的百分比
        int percent = offset * 100 / max;
        int bg = Color.argb((100 - percent) * 0xFF / 100, 0xFF, 0, 0);
        setBackgroundColor(bg);
    }

//    public void setImageView(ImageView mImageView) {
//        this.mImageView = mImageView;
//    }
}
