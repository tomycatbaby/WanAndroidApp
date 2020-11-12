package com.lzf.wanandroidapp.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lzf.wanandroidapp.utils.WindowUtils

abstract class BaseActivity() : AppCompatActivity(), IView {
    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 加载数据
     */
    abstract fun loaderData()

    /**
     * 做初始化方面的工作,比如接收上一个界面的Intent
     */
    abstract fun initVariables()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //transparentStatusBar()

        initView()
        initVariables()
        loaderData()
    }

    /**
     * 将状态栏设置成透明。只适配Android 5.0以上系统的手机。
     */
    private fun transparentStatusBar() {
        if (AndroidVersion.hasLollipop()) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}

/**
 * 以更加可读的方式提供Android系统版本号的判断方法。
 *
 */
object AndroidVersion {

    /**
     * 判断当前手机系统版本API是否是16以上。
     * @return 16以上返回true，否则返回false。
     */
    fun hasJellyBean(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 判断当前手机系统版本API是否是17以上。
     * @return 17以上返回true，否则返回false。
     */
    fun hasJellyBeanMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    /**
     * 判断当前手机系统版本API是否是18以上。
     * @return 18以上返回true，否则返回false。
     */
    fun hasJellyBeanMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    /**
     * 判断当前手机系统版本API是否是19以上。
     * @return 19以上返回true，否则返回false。
     */
    fun hasKitkat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    /**
     * 判断当前手机系统版本API是否是21以上。
     * @return 21以上返回true，否则返回false。
     */
    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 判断当前手机系统版本API是否是22以上。
     * @return 22以上返回true，否则返回false。
     */
    fun hasLollipopMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    /**
     * 判断当前手机系统版本API是否是23以上。
     * @return 23以上返回true，否则返回false。
     */
    fun hasMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * 判断当前手机系统版本API是否是24以上。
     * @return 24以上返回true，否则返回false。
     */
    fun hasNougat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    /**
     * 以更加可读的方式提供Android系统版本号的判断方法。
     *
     */
    object AndroidVersion {

        /**
         * 判断当前手机系统版本API是否是16以上。
         * @return 16以上返回true，否则返回false。
         */
        fun hasJellyBean(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        }

        /**
         * 判断当前手机系统版本API是否是17以上。
         * @return 17以上返回true，否则返回false。
         */
        fun hasJellyBeanMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        }

        /**
         * 判断当前手机系统版本API是否是18以上。
         * @return 18以上返回true，否则返回false。
         */
        fun hasJellyBeanMR2(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
        }

        /**
         * 判断当前手机系统版本API是否是19以上。
         * @return 19以上返回true，否则返回false。
         */
        fun hasKitkat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        }

        /**
         * 判断当前手机系统版本API是否是21以上。
         * @return 21以上返回true，否则返回false。
         */
        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        /**
         * 判断当前手机系统版本API是否是22以上。
         * @return 22以上返回true，否则返回false。
         */
        fun hasLollipopMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
        }

        /**
         * 判断当前手机系统版本API是否是23以上。
         * @return 23以上返回true，否则返回false。
         */
        fun hasMarshmallow(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        /**
         * 判断当前手机系统版本API是否是24以上。
         * @return 24以上返回true，否则返回false。
         */
        fun hasNougat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

    }
}