package com.lzf.wanandroidapp.widget;


import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lzf.wanandroidapp.R;

import java.util.ArrayList;

public class LoadingLayout extends FrameLayout {
    private View mEmptyView, mErrorView, mLoadingView, mContentView;
    private int mErrorViewResId, mLoadingViewResId, mEmptyViewResId, mContentViewResId;
    private OnClickListener onErrorClickListener;
    private OnClickListener onEmptyClickListener;
    private LayoutInflater mLayoutInflater;
    private int mViewStatus;
    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;
    private static final int NULL_RESOURCE_ID = -1;
    private final ArrayList<Integer> mOtherIds = new ArrayList<>();
    private static final FrameLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            mEmptyViewResId = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_view);
            mErrorViewResId = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.error_view);
            mLoadingViewResId = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_view);
            mContentViewResId = a.getResourceId(R.styleable.LoadingLayout_contentView, NULL_RESOURCE_ID);
            mLayoutInflater = LayoutInflater.from(context);
//
        } finally {
            a.recycle();
        }
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    private View inflateView(int layoutId) {
        return mLayoutInflater.inflate(layoutId, null);
    }

    public final void showError(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showError(null == mErrorView ? inflateView(layoutId) : mErrorView, layoutParams);
    }

    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    /**
     * 显示错误视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showError(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Error view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = view;
//            View errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
//            if (null != mOnRetryClickListener && null != errorRetryView) {
//                errorRetryView.setOnClickListener(mOnRetryClickListener);
//            }
            //mOtherIds.add(mErrorView.getId());
            addView(mErrorView, 0, layoutParams);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showLoading(null == mLoadingView ? inflateView(layoutId) : mLoadingView, layoutParams);
    }

    /**
     * 显示加载中视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = view;
            addView(mLoadingView, 0, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mLayoutInflater.inflate(mContentViewResId, null);
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }
    }

    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }
}
