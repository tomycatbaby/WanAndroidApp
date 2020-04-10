package com.lzf.wanandroidapp.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements IView{

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void loaderData();

    /**
     * 做初始化方面的工作,比如接收上一个界面的Intent
     */
    public abstract void initVariables();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initVariables();
        loaderData();
    }
}
