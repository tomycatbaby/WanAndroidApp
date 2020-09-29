package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SimpleViewGroup extends ViewGroup {
    public SimpleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SimpleViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SimpleViewGroup(Context context) {
        super(context);
    }

    public SimpleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int totalWidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(totalWidth, 0, totalWidth + child.getMeasuredWidth(), b);
            totalWidth = child.getMeasuredWidth();
        }
    }

    String TAG = "lzf";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() < 2) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            Log.d(TAG, "onMeasure: "+width);
            int maxHeight = 0;
            int totalWidth = 0;
            int childState = 0;
            int tailWidth = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    totalWidth += child.getMeasuredWidth();
                    if (i != 0) {
                        tailWidth += child.getMeasuredWidth();
                    }
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    childState = combineMeasuredStates(childState, child.getMeasuredState());
                    maxHeight = Math.max(maxHeight,
                            child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                }
            }
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    resolveSizeAndState(maxHeight, heightMeasureSpec, childState));
            View content = getChildAt(0);
            Log.d(TAG, "width:"+width+" totalWidth: " + totalWidth + " tailWidth:" + tailWidth + " content:" + content.getMeasuredWidth());
            if (totalWidth > width) {
                int contentWidth = width - tailWidth;
                measureChild(content, MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.AT_MOST), heightMeasureSpec);
                Log.d(TAG, "contentWidth: " + content.getMeasuredWidth());
            }

        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public static final int UNSPECIFIED_GRAVITY = -1;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(FrameLayout.LayoutParams source) {
            super(source);
        }
    }
}
