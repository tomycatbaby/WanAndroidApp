package com.lzf.wanandroidapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.locks.ReentrantLock;

public class SplashActivity extends BaseActivity {

    private AlphaAnimation alphaAnimation;
    private View view;

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
        view = findViewById(R.id.layout_splash);
        //补间动画
        alphaAnimation = new AlphaAnimation(0.3F, 1.0F);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                joinToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(alphaAnimation);
    }

    @Override
    public void loaderData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx88888888";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }
    @Override
    public void initVariables() {

    }

    private void joinToMain() {
        Intent i = new Intent(this, KtActivity.class);
        startActivity(i);
        finish();
        //?
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
