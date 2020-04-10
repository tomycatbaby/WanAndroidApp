package com.lzf.wanandroidapp.widget;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class RefreshLayout extends ViewGroup {

    private String TAG = "lzf";
    private HeaderLayout mHeaderLayout;
    private boolean mCanRefresh;

    private boolean mMultiTask;

    private CallBack mCallBack;

    /**
     * 上一次的y轴坐标
     */
    private float mPreviousYPos;
    /**
     * 当前y轴坐标
     */
    private float mNowYPos;
    /**
     * y轴移动的距离
     */
    private float mYDistance;

    /**
     * down事件时的y坐标
     */
    private float mDownYPos;
    /**
     * 第一次触发move事件的时候，判断移动的距离（避免误触）
     */
    private boolean mFirstMove;
    /**
     * 第一次触发move事件的时候，y轴移动的距离
     */
    private float mJudgeYDistance;
    /**
     * 误差距离
     */
    private int mTouchSlop;

    /**
     * header参数
     */
    //private HeaderLayout.Param mHeaderParam = new HeaderLayout.Param();
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.d(TAG, "onLayout: "+childCount);
        View vHeader = null;
        View vContent = null;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof HeaderLayout) {
                vHeader = child;
                vHeader.layout(0, 0, vHeader.getMeasuredWidth(), vHeader.getMeasuredHeight());
            } else {
                vContent = child;
            }
        }
        int y = (null == vHeader ? 0 : vHeader.getMeasuredHeight());
        //layout控制控件位置，显示在屏幕上的哪个位置
        vContent.layout(0, y, vContent.getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof HeaderLayout) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        child.getMeasuredHeightAndState());
            } else {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        getContentMeasuredHeightState());
            }
        }
    }

    //
    private int getContentMeasuredHeightState() {
        if (getContentView() instanceof RecyclerView) {
            return MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        } else if (getContentView() != null) {
            return MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        }
        return 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousYPos = ev.getRawY();
                mDownYPos = ev.getRawY();
                mFirstMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mNowYPos = ev.getRawY();
                mYDistance = mNowYPos - mPreviousYPos;
                mPreviousYPos = mNowYPos;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mFirstMove && (mJudgeYDistance = Math.abs(mNowYPos - mDownYPos)) < mTouchSlop) {
                    mFirstMove = false;
                    return false;
                }
                mFirstMove = false;
                if (mYDistance == 0f) {
                    return false;
                }
                if (isPullDown(MotionEvent.ACTION_MOVE, mYDistance)) {
                    Log.d(TAG, "onInterceptTouchEvent: ");
                    return true;
                } else if (isPullUp(MotionEvent.ACTION_MOVE, mYDistance)) {
                    return true;
                }
                break;
            default:
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mYDistance == 0f) {
                    break;
                }
                fresh();
//                if (isHeaderActive()) {
//                    mYDistance *= externForce(mHeaderLayout.getHeaderHeight(),
//                            mHeaderLayout.getHeaderContentHeight());
//                }
//                if (isPullDown(MotionEvent.ACTION_MOVE, mYDistance)) {
//                    mHeaderLayout.setHeaderHeight((int) (mHeaderLayout.getHeaderHeight() + mYDistance));
//                    updatePullDownStatus(MotionEvent.ACTION_MOVE);
//                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCallBack.onRefresh();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isCanRefresh() {
        return mCanRefresh;
    }

    public void setCanRefresh(boolean mCanRefresh) {
        this.mCanRefresh = mCanRefresh;
    }

    public void init() {
        setHeadLayout();
        setCanRefresh(true);
    }

    private void setHeadLayout() {
        mHeaderLayout = new HeaderLayout(getContext());
        mHeaderLayout.setHeaderHeight(0);
        addView(mHeaderLayout, 0);//header should be the first view
    }

    private boolean isPullDown(int action, float fDisYPos) {
        if (!isCanRefresh()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (fDisYPos > 0) {//move down
                    if (isContentToTop()) {//pull up
                        return true;
                    }
                } else if (fDisYPos < 0) {//move up

                }
            case MotionEvent.ACTION_UP:

            default:
                break;
        }
        return false;
    }

    public void stop() {
        mHeaderLayout.setStatus(HeaderLayout.Status.BACK_NORMAL);
    }

    public void fresh() {
        mHeaderLayout.setStatus(HeaderLayout.Status.REFRESH);
    }

    public boolean isContentToTop() {
        return RefreshLoadMoreUtil.isContentToTop(getContentView());
    }

    private boolean isPullUp(int action, float fDisYPos) {
//        switch (action) {
//            case MotionEvent.ACTION_MOVE:
//                if (fDisYPos > 0) {//move down
//                    if (isFooterActive()) {//back after pull up
//                        return true;
//                    }
//                } else if (fDisYPos < 0) {//move up
//                    if (isContentToBottom()) {//pull up
//                        return true;
//                    }
//                }
//            case MotionEvent.ACTION_UP:
//                if (isFooterActive()) {
//                    return true;
//                }
//            default:
//                break;
//        }
        return false;
    }

    public RefreshLayout(Context context) {
        super(context);
    }

    public interface CallBack {
        void onRefresh();
    }

    public void setOnRefresh(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public View getContentView() {
        return getChildAt(1);
    }

    private float externForce(int height, int factorHeight) {
        float s1 = (float) height / factorHeight;
        if (s1 >= 1.0f) {
            s1 = 0.4f;
        } else if (s1 >= 0.6 && s1 < 1.0) {
            s1 = 0.6f;
        } else if (s1 >= 0.3 && s1 < 0.6) {
            s1 = 0.8f;
        } else {
            s1 = 1.0f;
        }
        return s1;
    }
    private void updatePullDownStatus(int action) {

        switch (action) {
            case MotionEvent.ACTION_MOVE:
//                if (HeaderLayout.Status.REFRESH == mHeaderLayout.getStatus()) {//change height not change status when refreshing
//                    return;
//                }
//                if (mHeaderLayout.getHeaderHeight() >= mHeaderLayout.getHeaderContentHeight()) {//can release to refresh
//                    mHeaderLayout.setStatus(HeaderLayout.Status.CAN_RELEASE);
//                } else {//only pull down
//                    mHeaderLayout.setStatus(HeaderLayout.Status.PULL_DOWN);
//                }
                break;
            case MotionEvent.ACTION_UP://change status when move,check status on up

                break;
            default:
                break;
        }
    }

    public boolean istouchSlop() {
        int x = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        return mCanRefresh;
    }
}
