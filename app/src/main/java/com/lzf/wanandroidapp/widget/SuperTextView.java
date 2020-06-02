package com.lzf.wanandroidapp.widget;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.UnsupportedEncodingException;

public class SuperTextView extends androidx.appcompat.widget.AppCompatTextView {
    String TAG = "SuperTextView";

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SuperTextView(Context context) {
        super(context);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_CANCEL: ");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: ");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN 请求拦截掉: ");
                invalidate();
                requestLayout();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE: ");

                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
