package com.example.p.ex13_;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by P on 2015/7/6.
 */
public class LetterSideBar extends View {
    private final Paint mPaint;
    private char firstLetter = 'A';
    private boolean mPressed;
    private OnLetterChangedListener listener;
    private int index = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPressed){
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        for (int i = 0; i < 26; i++) {
            String letter = String.valueOf((char)(firstLetter + i));
            int xPos = (int) (getWidth() / 2 - mPaint.measureText(letter) / 2);
            int yHeight = getHeight() / 27 * (i + 1);
            if (i == index){
                mPaint.setColor(Color.RED);
            }else{
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawText(letter, xPos, yHeight, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                index = (int) (y / getHeight() * 27);
                String currLetter = String.valueOf((char)(firstLetter + index));
                listener.onLetterChanged(currLetter);
                mPressed = true;
                break;
            case MotionEvent.ACTION_UP:
                mPressed = false;
                listener.onActionUp();
                index = -1;
                break;
        }
        invalidate();
        return true;
    }

    public void setOnLetterChangedListener(OnLetterChangedListener listener){
        this.listener = listener;
    }

    public interface OnLetterChangedListener{

        void onLetterChanged(String letter);

        void onActionUp();
    }

    public LetterSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(20);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
