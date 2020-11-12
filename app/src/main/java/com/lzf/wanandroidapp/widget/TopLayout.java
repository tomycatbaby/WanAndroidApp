package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzf.wanandroidapp.R;

import java.util.Objects;

public class TopLayout extends FrameLayout {
    private ImageView leftImg;
    private ImageView rightImg;
    private TextView title;
    private Context mContext;

    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private String titleStr;

    public TopLayout(@NonNull Context context) {
        this(context, null);
    }

    public TopLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopLayout, defStyleAttr, 0);
            leftDrawable = a.getDrawable(R.styleable.TopLayout_tl_left_image);
            rightDrawable = a.getDrawable(R.styleable.TopLayout_tl_right_image);
            titleStr = a.getString(R.styleable.TopLayout_tl_title);
            a.recycle();
        }
        mContext = context;
        initView();
    }



    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.top_layout, this, true);
        leftImg = view.findViewById(R.id.iv_left);
        title = view.findViewById(R.id.tv_title);
        rightImg = view.findViewById(R.id.iv_right);
        if (Objects.nonNull(leftDrawable))
            leftImg.setImageDrawable(leftDrawable);
        if (Objects.nonNull(rightDrawable))
            rightImg.setImageDrawable(rightDrawable);
        if (Objects.nonNull(titleStr))
            title.setText(titleStr);
    }

    public ImageView getLeftImg() {
        return leftImg;
    }

    public ImageView getRightImg() {
        return rightImg;
    }

    public TextView getTitle() {
        return title;
    }
}
