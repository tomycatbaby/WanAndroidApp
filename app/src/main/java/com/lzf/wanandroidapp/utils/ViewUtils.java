package com.lzf.wanandroidapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewUtils {
    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        int scrollY = editText.getScrollY();
        int scrollRange = editText.getLayout().getHeight();
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        int scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    /**
     * 判断scrollview是否可滑动
     *
     * @param scrollView
     * @return
     */
    public static boolean canScroll(ScrollView scrollView) {
        View child = scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = child.getHeight();
            return scrollView.getHeight() < childHeight;
        }
        return false;
    }


    /**
     * 获取焦点
     *
     * @param view the view
     */
    public static void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }


    /**
     * 设置view的margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v==null||v.getLayoutParams() == null) {
            return;
        }

        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int[] getMargins(View v) {
        if (v.getLayoutParams() == null) {
            return null;
        }
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int[] margins = new int[]{p.leftMargin, p.topMargin, p.rightMargin, p.bottomMargin};
            return margins;
        }
        return null;
    }


    /**
     * 测量textview的宽度
     *
     * @param textView
     * @param text
     * @return
     */
    public static float getTextWidth(TextView textView, String text) {
        if (text == null) {
            return 0;
        }
        TextPaint textPaint = textView.getPaint();
        return textPaint.measureText(text);
    }

    /**
     * 设置view的高度和宽度
     *
     * @param v
     * @param height
     * @param width
     */
    public static void setHeightWidth(View v, int height, int width) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) v.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (layoutParams == null) {
            layoutParams = new ViewGroup.MarginLayoutParams(width, height);
        } else {
            layoutParams.height = height;
            layoutParams.width = width;
        }
        v.setLayoutParams(layoutParams);
    }

    /**
     * 设置view的高度
     *
     * @param v      the v
     * @param height the height
     */
    public static void setHeight(View v, int height) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) v.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (layoutParams == null) {
            layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        } else {
            layoutParams.height = height;
        }
        v.setLayoutParams(layoutParams);
    }

    /**
     * 设置view的宽度
     *
     * @param v     the v
     * @param width the width
     */
    public static void setWidth(View v, int width) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) v.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (layoutParams == null) {
            layoutParams = new ViewGroup.MarginLayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            layoutParams.width = width;
        }
        v.setLayoutParams(layoutParams);
    }


    /**
     * 计算view的宽度
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T extends View> int getViewWidthDirectly(T view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);

        return view.getMeasuredWidth();
    }

    /**
     * 计算view的高度
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T extends View> int getViewHeightDirectly(T view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);

        return view.getMeasuredHeight();
    }


    /**
     * 获得View所在的activity
     *
     * @param view
     * @return
     */
    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }


    /**
     * 递归获得ViewGroup中的所有子类，以平铺list的方式返回结果
     *
     * @param viewGroup
     * @return
     */
    public static List<View> getViews(ViewGroup viewGroup) {
        List<View> viewList = new ArrayList<>();
        viewList.add(viewGroup);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof ViewGroup) {
                viewList.addAll(getViews((ViewGroup) child));
            } else {
                viewList.add(child);
            }
        }

        return viewList;
    }

    /**
     * 设置ViewGroup及其所有子类的透明度
     *
     * @param viewGroup
     * @param alpha
     */
    public static void setViewGroupAndChildrenAlpha(ViewGroup viewGroup, int alpha) {
        List<View> viewList = getViews(viewGroup);

        for (View child : viewList) {
            if (child.getBackground() != null) {
                child.getBackground().mutate().setAlpha(alpha);
            }

            if (child instanceof ImageView) {
                if (((ImageView) child).getDrawable() != null) {
                    ((ImageView) child).getDrawable().mutate().setAlpha(alpha);
                }
            } else if (child instanceof TextView) {
                int color = ((TextView) child).getCurrentTextColor();

                int red = (color & 0xff0000) >> 16;
                int green = (color & 0x00ff00) >> 8;
                int blue = (color & 0x0000ff);

                int newColor = Color.argb(alpha, red, green, blue);
                ((TextView) child).setTextColor(newColor);
            }
        }
    }

    /**
     * 删除RecyclerView的所有ItemDecoration
     *
     * @param recyclerView
     */
    public static void clearAllItemDecoration(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        for (int i = recyclerView.getItemDecorationCount() - 1; i >= 0; i--) {
            recyclerView.removeItemDecorationAt(i);
        }
    }

    /**
     * 获得view在屏幕中的坐标
     *
     * @param view
     * @return
     */
    public static int[] getViewLocAtWindow(View view) {
        int[] location = new int[2];
        if (view == null) {
            return location;
        }
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获得view在屏幕中Top
     *
     * @param view
     * @return
     */
    public static int getViewTopAtWindow(View view) {
        return getViewLocAtWindow(view)[1];
    }

    /**
     * 获得view在屏幕中Left
     *
     * @param view
     * @return
     */
    public static int getViewLeftAtWindow(View view) {
        return getViewLocAtWindow(view)[0];
    }


}
