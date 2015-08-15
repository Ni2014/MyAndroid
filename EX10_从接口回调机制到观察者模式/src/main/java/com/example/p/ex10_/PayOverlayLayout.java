package com.example.p.ex10_;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by P on 2015/6/16.
 */
public class PayOverlayLayout extends RelativeLayout implements  Observer{
    private int max;

    public PayOverlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setMax(int max){
        this.max = max;
    }

    @Override
    public void update(int offset) {
        if (offset > max){
            setVisibility(View.VISIBLE);
        }else {
            setVisibility(View.INVISIBLE);
        }
    }


//    public void setImageView(ImageView mImageView) {
//        this.mImageView = mImageView;
//    }
}
