package com.lzf.wanandroidapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.lzf.wanandroidapp.base.App;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ResUtils {
    private ResUtils() {
    }

    @SuppressLint("StaticFieldLeak")
    private static Context context = App.getContext();

    public static String getString(@StringRes int resId) {
        return context.getString(resId);
    }

    public static String getString(@StringRes int resId, Object... args) {
        return context.getString(resId, args);
    }

    public static void setContext(Context context) {
        ResUtils.context = context;
    }

    /**
     * 获得字符串资源
     *
     * @param resIds
     * @return
     */
    public static String[] getStringArrayByResIdArray(@StringRes int... resIds) {
        String result[] = new String[resIds.length];
        for (int i = 0; i < resIds.length; i++) {
            result[i] = getString(resIds[i]);
        }
        return result;
    }


    public static String[] getArrayString(@ArrayRes int resId) {
        return context.getResources().getStringArray(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId) {

        if (resId == 0) return null;
        return AppCompatResources.getDrawable(context, resId);
    }


    public static Drawable[] getDrawableArray(@DrawableRes int... resIds) {
        Drawable result[] = new Drawable[resIds.length];
        for (int i = 0; i < resIds.length; i++) {
            result[i] = getDrawable(resIds[i]);
        }
        return result;
    }

    /**
     * 获得图片资源
     *
     * @param resId
     * @return
     */
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(App.getContext().getResources(), resId);
    }

    public static int getColor(@ColorRes int resId) {
        return context.getResources().getColor(resId);
    }

    public static int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 读取raw
     *
     * @param resId
     * @return
     */
    public static String getFromRaw(int resId) {
        try {
            InputStreamReader inputReader = new InputStreamReader(App.getContext().getResources().openRawResource(resId));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder Result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                Result.append(line);
            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Assets
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(App.getContext().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder Result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                Result.append(line).append("\n");
            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int dp2pxInSize(float dpValue) {
        float v = dp2px(dpValue);
        final int res = (int) ((v >= 0) ? (v + 0.5f) : (v - 0.5f));
        if (res != 0) return res;
        if (dpValue == 0) return 0;
        if (dpValue > 0) return 1;
        return -1;

    }

    public static int dp2pxInOffset(float dpValue) {
        float v = dp2px(dpValue);
        return (int) v;
    }


    public static Drawable makeRecDrawable(@ColorInt int color, int radiusIndp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(dp2px(radiusIndp));
        drawable.setColor(color);
        return drawable;
    }

    public static Drawable makeCircleDrawable(@ColorInt int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        return drawable;
    }

    public static float getScaleDensity() {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    public static Drawable getDrawable(@DrawableRes int drawableId, @ColorRes int colorId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        ColorStateList colors = ColorStateList.valueOf(ContextCompat.getColor(context, colorId));
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}