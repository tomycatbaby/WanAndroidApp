package com.lzf.wanandroidapp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
            marginParams = (MarginLayoutParams) child.getLayoutParams();
            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            int childWidth = marginParams.leftMargin + marginParams.rightMargin + measuredWidth;
            int childHeight = marginParams.topMargin + marginParams.bottomMargin + measuredHeight;
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
            child.setTag(new Rect(rowsWidth - childWidth + marginParams.leftMargin, columnHeight + marginParams.topMargin, rowsWidth - marginParams.rightMargin, columnHeight + childHeight - marginParams.bottomMargin));
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

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    //需要重写这个方法，否则无法使用MarginLayoutParams
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
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
