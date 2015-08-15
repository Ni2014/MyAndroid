package com.example.p.ex09_;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by P on 2015/6/10.
 */
public class MyScrollView extends ScrollView {
    private OnScrollChangedListener mListener;

    public interface OnScrollChangedListener{
        void onScrollChanged(int offset);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener)
    {
        this.mListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.e("onScrollChanged", "top: " + t);
        mListener.onScrollChanged(t);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
