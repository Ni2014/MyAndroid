package com.example.p.ex10_;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P on 2015/6/10.
 */
public class MyScrollView extends ScrollView implements Obserable{
//    private OnScrollChangedListener mListener;
    private List<Observer> mList = new ArrayList<Observer>();

//    public interface OnScrollChangedListener{
//        void onScrollChanged(int offset);
//    }

//    public void setOnScrollChangedListener(OnScrollChangedListener listener)
//    {
//        this.mListener = listener;
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.e("onScrollChanged", "top: " + t);
//        mListener.onScrollChanged(t);
        notifyObservers(t);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addObserver(Observer o) {
        if (o == null){
            return;
        }
        if (!mList.contains(o)){
            mList.add(o);
        }
    }

    @Override
    public void delObserver(Observer o) {
        if (o == null){
            return;
        }
        if (mList.contains(o)){
            mList.remove(o);
        }
    }

    @Override
    public void notifyObservers(int offset) {
        for (int i = 0; i < mList.size(); i++) {
            Observer observer = mList.get(i);
            observer.update(offset);
        }
    }
}
