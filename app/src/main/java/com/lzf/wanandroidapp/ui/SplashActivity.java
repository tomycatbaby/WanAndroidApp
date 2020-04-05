package com.lzf.wanandroidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private AlphaAnimation alphaAnimation;
    private View view;
    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
        view = findViewById(R.id.layout_splash);

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

    @Override
    public void initVariables() {

    }

    private void joinToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        //?
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
