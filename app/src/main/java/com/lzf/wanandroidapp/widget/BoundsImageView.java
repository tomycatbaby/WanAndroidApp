package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.lzf.wanandroidapp.R;

public class BoundsImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint paint = new Paint();
    private Rect rect = new Rect();
    private int mBorderColor = Color.BLACK;
    private int mBorderWidth = 1;
    public BoundsImageView(Context context) {
        super(context);

    }

    public BoundsImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoundsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundsImageView, defStyleAttr, 0);

        mBorderWidth = a.getDimensionPixelSize(R.styleable.BoundsImageView_biv_border_width, 0);
        mBorderColor = a.getColor(R.styleable.BoundsImageView_biv_border_color, Color.BLACK);

        a.recycle();
        init();
    }

    private void init() {
        paint.setColor(mBorderColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);    //设置画笔宽度
        paint.setStyle(Paint.Style.STROKE);    //设置画笔为线条模式

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.getClipBounds(rect);    //获取到ImageView的外轮廓矩形
        float mDrawableRadius = Math.min(rect.height() / 2.0f, rect.width() / 2.0f);
        canvas.drawCircle(rect.centerX(), rect.centerY(), mDrawableRadius, paint);    //绘制这个矩形，获得一个边框
    }
}
