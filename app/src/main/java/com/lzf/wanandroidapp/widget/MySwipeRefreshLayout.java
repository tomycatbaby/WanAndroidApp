package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class MySwipeRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
