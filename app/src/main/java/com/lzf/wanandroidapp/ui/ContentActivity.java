package com.lzf.wanandroidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.base.Constant;

public class ContentActivity extends BaseActivity {
    private WebView webView;
    private String TAG = "ContentActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_content);
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.CONTENT_URL_KEY);
        String id = intent.getStringExtra(Constant.CONTENT_ID_KEY);
        String title = intent.getStringExtra(Constant.CONTENT_TITLE_KEY);
        webView.loadUrl(url);
        BasicActivity basicActivity =  new BasicActivity();
        basicActivity.new Entry();
        basicActivity.getEntry().gr();//
        Bundle bundle;
    }

    @Override
    public void loaderData() {

    }

    @Override
    public void initVariables() {

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
