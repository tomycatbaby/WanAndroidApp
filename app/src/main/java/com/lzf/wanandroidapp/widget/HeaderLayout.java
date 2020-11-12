package com.lzf.wanandroidapp.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lzf.wanandroidapp.R;

import org.xmlpull.v1.XmlPullParser;

import javax.xml.parsers.SAXParser;

public class HeaderLayout extends FrameLayout {
    private Context mContext;
    private ImageView headImageView;
    private View mVHeaderContent;
    private View mVHeader;
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
        mVHeader = view.findViewById(R.id.root);
        mVHeaderContent = view.findViewById(R.id.header_rl_content);
        headImageView = view.findViewById(R.id.dapter_empty_animator);
        headImageView.setImageResource(R.drawable.empty_animator);
        //帧动画
        AnimationDrawable animation = (AnimationDrawable) headImageView.getDrawable();
        animation.setOneShot(false);//是否重复播放
        animation.start();
        invalidate();
    }

    public int getHeaderContentHeight() {
        return mVHeaderContent.getMeasuredHeight();
    }


    public void onBackNormalStatus() {
        setHeightAnim(0);
    }

    public void onBackRefreshStatus() {
        setHeaderHeight(getHeaderContentHeight());
    }

    private void setHeightAnim(final int toHeight) {

        //属性动画
        mHeightAnim = ValueAnimator.ofInt(getHeaderContentHeight(), toHeight);
        mHeightAnim.setDuration(500);
        mHeightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mHeightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final Integer animValue = (Integer) valueAnimator.getAnimatedValue();
                Log.d("lzf", "onAnimationUpdate: "+animValue);
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
        mVHeader.getLayoutParams().height = mHeaderHeight;
        mVHeader.requestLayout();

    }

    public int getStatus() {
        return mStatus;
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
                onBackRefreshStatus();
                break;
            //below will reset mStatus value
            case Status.BACK_NORMAL:
                onBackNormalStatus();
                break;
            case Status.BACK_REFRESH:
                //onBackRefreshStatus();
                break;
            case Status.AUTO_REFRESH:
                //onAutoRefreshStatus();
                break;
            default:
                break;
        }
    }

    private void onRefreshStatus() {
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

}
