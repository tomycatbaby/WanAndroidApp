package com.lzf.wanandroidapp.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.koin.core.Koin;

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
        UMConfigure.init(this, "5f45fe16625cab21451467cf", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "41746723b56bc2e25488affb071e6b4b");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        CrashReport.initCrashReport(getApplicationContext(), "330143809b", true);
        MMKV.initialize(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("lzf", s + "," + s1 + "");
            }
        });
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
