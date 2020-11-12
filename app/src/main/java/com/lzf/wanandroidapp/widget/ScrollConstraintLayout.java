package com.lzf.wanandroidapp.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ScrollConstraintLayout extends ConstraintLayout {
    public ScrollConstraintLayout(Context context) {
        super(context);
    }

    public ScrollConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 按下时当时所处的屏幕坐标
     */
    private int mXDown, mYDown;

    /**
     * 已经移动的距离
     */
    private int mXMove, mYMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private int mXLastMove, mYLastMove;

    private int mTop, mBottom;
    ValueAnimator valueAnimator;
    private boolean init = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = (int) event.getRawX();
                mYDown = (int) event.getRawY();
                if (!init){
                    init = true;
                    mTop = getTop();
                    mBottom = getBottom();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mXLastMove = (int) event.getRawX();
                mYLastMove = (int) event.getRawY();
                if (mYLastMove - mYDown > 0) {
                    mYMove = mYLastMove - mYDown;
                    mYMove = Math.min(mYMove, 300);
                    layout(getLeft(), mTop + mYMove, getRight(), mBottom + mYMove);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (valueAnimator != null && valueAnimator.isRunning()){
                    valueAnimator.cancel();
                }
                valueAnimator = ValueAnimator.ofInt(mYMove,0);
                valueAnimator.setDuration(300);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int move = (int) animation.getAnimatedValue();
                        layout(getLeft(), mTop + move, getRight(), mBottom + move);
                    }
                });
                valueAnimator.start();
                mXMove = 0;
                mYMove = 0;
                break;
        }
        return true;
    }
}
