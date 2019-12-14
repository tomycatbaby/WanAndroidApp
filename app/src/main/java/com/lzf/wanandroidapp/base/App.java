package com.lzf.wanandroidapp.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class App extends Application {

    private static Context mContext;

    /**
     * 内存不够时调用
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 当终止程序时调用 但是不能保证一定调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 在内存清理时触发
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 在配置被改变时触发
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        mContext = this;
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogsAboutCrashHandler.getInstance().init();
    }

    public static Context getContext() {
        return mContext;
    }
}
