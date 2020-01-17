package com.lzf.wanandroidapp.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzf.wanandroidapp.R;

public class HeaderLayout extends FrameLayout {
    private Context mContext;
    private ImageView headImageView;
    private View mVHeaderContent;
    private int mHeaderHeight;
    private ValueAnimator mHeightAnim;
    private int mStatus = -1;

    public HeaderLayout(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public HeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_view, this, false);
        addView(view);
        mVHeaderContent = view.findViewById(R.id.header_rl_content);
        headImageView = view.findViewById(R.id.dapter_empty_animator);
        headImageView.setImageResource(R.drawable.empty_animator);
        AnimationDrawable animation = (AnimationDrawable) headImageView.getDrawable();
        animation.setOneShot(false);//是否重复播放
        animation.start();
    }

    public int getHeaderHeight() {
        return mVHeaderContent.getMeasuredHeight();
    }


    public void onBackNormalStatus() {
        setHeightAnim(0);
    }

    public void onBackRefreshStatus() {
        setHeightAnim(getHeaderHeight());
    }

    private void setHeightAnim(final int toHeight) {
        mHeightAnim = ValueAnimator.ofInt(getHeaderHeight(), toHeight);
        mHeightAnim.setDuration(1000);
        mHeightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mHeightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final Integer animValue = (Integer) valueAnimator.getAnimatedValue();
                setHeaderHeight(animValue);
            }
        });
        mHeightAnim.start();
    }

    public void setHeaderHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        mHeaderHeight = height;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.height = mHeaderHeight;
        headImageView.setLayoutParams(params);
    }

    public void setStatus(int status) {
        if (this.mStatus == status) {
            return;
        }

        int oldStatus = this.mStatus;
        this.mStatus = status;

        switch (this.mStatus) {
            case Status.NORMAL:
                //onNormalStatus(oldStatus);
                break;
            case Status.PULL_DOWN:
                //onPullDownStatus(oldStatus);
                break;
            case Status.CAN_RELEASE:
                //onCanRefreshStatus();
                break;
            case Status.REFRESH:
                //onRefreshStatus();
                break;
            //below will reset mStatus value
            case Status.BACK_NORMAL:
                onBackNormalStatus();
                break;
            case Status.BACK_REFRESH:
                onBackRefreshStatus();
                break;
            case Status.AUTO_REFRESH:
                //onAutoRefreshStatus();
                break;
            default:
                break;
        }
    }

    public static class Status {

        public static final int NORMAL = 0;

        public static final int PULL_DOWN = 1;

        public static final int CAN_RELEASE = 2;

        public static final int REFRESH = 3;

        public static final int BACK_NORMAL = 4;

        public static final int BACK_REFRESH = 5;

        public static final int AUTO_REFRESH = 6;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    public static class LayoutParams extends FrameLayout.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
