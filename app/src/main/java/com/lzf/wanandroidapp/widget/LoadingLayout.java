package com.lzf.wanandroidapp.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lzf.wanandroidapp.R;

public class LoadingLayout extends FrameLayout {
    private View mEmptyView, mErrorView, mLoadingView;
    private OnClickListener onErrorClickListener;
    private OnClickListener onEmptyClickListener;
    private LayoutInflater mLayoutInflater;

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            int emptyView = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_view);
            int errorView = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.error_view);
            int loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_view);
            mLayoutInflater = LayoutInflater.from(context);
            mEmptyView = mLayoutInflater.inflate(emptyView,this,true);
            mErrorView = mLayoutInflater.inflate(errorView,this,true);
            mLoadingView = mLayoutInflater.inflate(loadingView,this,true);
        } finally {
            a.recycle();
        }
    }
}
