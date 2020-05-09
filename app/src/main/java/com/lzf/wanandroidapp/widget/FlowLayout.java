package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowLayout extends ViewGroup {
    String TAG = "FlowLayout";
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 收到事件首先传递到Activity，然后会调用到window的事件分发，从Window到顶级View，传递到ViewGroup的事件分发方法，dispatchTouchEvent，
     * ViewGroup是有一个onInterceptTouchEvent,意思是ViewGroup是否需要拦截这个事件，如果不需要处理，会向下传递给子View
     * 如果拦截，事件就不会传递给子View了，会调用ViewGroup的touch方法，
     * 关于onTouchEvent方法，如果返回的是false，后续点击事件不会再传递到这个View了，如果所有的子View都是返回false的话，就会调用到Activity的onTouchEvent方法
     *
     *
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_CANCEL: ");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: ");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN: ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE: ");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");

        return true;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = 0, measuredHeight = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widtMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Map<String, Integer> compute = compute(widthSize - getPaddingRight());
        //EXACTLY模式：对应于给定大小或者match_parent情况
        if (widtMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
            //AT_MOST模式：对应wrap-content（需要手动计算大小，否则相当于match_parent）
        } else if (widtMode == MeasureSpec.AT_MOST) {
            measuredWidth = compute.get("allChildWidth");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = compute.get("allChildHeight");
        }
        //设置flow的宽高
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 测量过程
     * padding是控件本身内容的边距，margin是整个控件的边距
     *
     * @param flowWidth 该view的宽度
     * @return 返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
     */
    private Map<String, Integer> compute(int flowWidth) {
        //是否是单行
        boolean aRow = true;
        MarginLayoutParams marginParams;//子元素margin
        int rowsWidth = getPaddingLeft();//当前行已占宽度(注意需要加上paddingLeft)
        int columnHeight = getPaddingTop();//当前行顶部已占高度(注意需要加上paddingTop)
        int rowsMaxHeight = 0;//当前行所有子元素的最大高度（用于换行累加高度）

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            //获取元素测量宽度和高度
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            //获取元素的margin
            int childWidth, childHeight;
            int right = 0, left = 0, top = 0, bottom = 0;
            childWidth = measuredWidth;
            childHeight = measuredHeight;
            if (child.getLayoutParams() != null) {
                marginParams = (LayoutParams) child.getLayoutParams();
                //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
                childWidth += marginParams.leftMargin + marginParams.rightMargin;
                childHeight += marginParams.topMargin + marginParams.bottomMargin;
                right = marginParams.rightMargin;
                left = marginParams.leftMargin;
                top = marginParams.topMargin;
                bottom = marginParams.bottomMargin;
            }

            //判断是否换行： 该行已占大小+该元素大小>父容器宽度  则换行

            rowsMaxHeight = Math.max(rowsMaxHeight, childHeight);
            //换行
            if (rowsWidth + childWidth > flowWidth) {
                //重置行宽度
                rowsWidth = getPaddingLeft() + getPaddingRight();
                //累加上该行子元素最大高度
                columnHeight += rowsMaxHeight;
                //重置该行最大高度
                rowsMaxHeight = childHeight;
                aRow = false;
            }
            //累加上该行子元素宽度
            rowsWidth += childWidth;
            //判断时占的宽段时加上margin计算，设置顶点位置时不包括margin位置，不然margin会不起作用，这是给View设置tag,在onlayout给子元素设置位置再遍历取出
            child.setTag(new Rect(rowsWidth - childWidth + left, columnHeight + top, rowsWidth - right, columnHeight + childHeight - bottom));
        }

        //返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
        Map<String, Integer> flowMap = new HashMap<>();
        //单行
        if (aRow) {
            flowMap.put("allChildWidth", rowsWidth);
        } else {
            //多行
            flowMap.put("allChildWidth", flowWidth);
        }
        //FlowLayout测量高度 = 当前行顶部已占高度 +当前行内子元素最大高度+FlowLayout的PaddingBottom
        flowMap.put("allChildHeight", columnHeight + rowsMaxHeight + getPaddingBottom());
        return flowMap;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = (Rect) getChildAt(i).getTag();
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    //需要重写这个方法，否则无法使用MarginLayoutParams
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
