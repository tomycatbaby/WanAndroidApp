package com.lzf.wanandroidapp.base;

import android.app.Application;
import android.content.Context;

public class WanApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogsAboutCrashHandler.getInstance().init();
    }
}
