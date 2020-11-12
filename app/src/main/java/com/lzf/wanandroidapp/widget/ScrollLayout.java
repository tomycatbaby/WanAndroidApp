package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewConfigurationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.utils.ResUtils;

public class ScrollLayout extends ViewGroup {

    public ScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 其他按钮的宽度
     */
    private int mWidth;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    private Scroller mScroller;
    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    //缓慢滑动到指定位置
    public void smoothScrollXTo(int targetX) {
        //1000ms内 缓慢 滑动到targetX处
        mScroller.startScroll(getScrollX(), 0, targetX, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        //动画未完成
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(1), widthMeasureSpec, MeasureSpec.makeMeasureSpec(getChildAt(0).getMeasuredHeight(), MeasureSpec.EXACTLY));
        setMeasuredDimension(getChildAt(0).getMeasuredWidth(), getChildAt(0).getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //super.onLayout(changed,left,top,right,bottom);
        int width = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(width, 0, width + child.getMeasuredWidth(), child.getMeasuredHeight());
            width += child.getMeasuredWidth();
        }
        // 初始化左右边界值
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
        mWidth = getChildAt(1).getMeasuredWidth();
    }

    private float mXDown;//手机按下时的屏幕坐标
    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove, mYMove;
    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove, mYLastMove;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("lzf", "onInterceptTouchEvent: " + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                Log.d("lzf", "onInterceptTouchEvent: " + diff + " mTouchSlop:" + mTouchSlop);
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("lzf", "onTouchEvent: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                mYMove = event.getRawY();

                int scrolledX = (int) (mXLastMove - mXMove);
                int scrolledY = (int) (mYLastMove - mYMove);
                if (scrolledY > mTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }

                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + mWidth / 2) / mWidth;
                int dx = targetIndex * mWidth - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                Log.d("lzf", "getScrollX(): " + getScrollX() + " dx:" + dx);
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return true;
    }
}
