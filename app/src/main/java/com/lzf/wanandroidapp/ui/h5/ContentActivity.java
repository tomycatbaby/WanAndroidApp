package com.lzf.wanandroidapp.ui.h5;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.utils.ToastUtil;
import com.lzf.wanandroidapp.widget.TopLayout;

import kotlin.jvm.Volatile;

public class ContentActivity extends BaseActivity {
    private WebView webView;
    private String TAG = "ContentActivity";
    private PopupWindow popupWindow;
    private ProgressBar progress;
    String url;

    @Override
    public void initView() {
        setContentView(R.layout.activity_content);
        webView = findViewById(R.id.webView);
        TopLayout topBar = findViewById(R.id.top_bar);
        progress = findViewById(R.id.progress);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: " + newProgress);
                progress.setProgress(newProgress);
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    view.loadUrl(url);
                    return false;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        ToastUtil.show("手机还没有安装支持打开此网页的应用！");
                    }
                }
                return true;
            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.CONTENT_URL_KEY);
        String id = intent.getStringExtra(Constant.CONTENT_ID_KEY);
        String title = intent.getStringExtra(Constant.CONTENT_TITLE_KEY);
        topBar.getTitle().setText(title);
        topBar.getLeftImg().setOnClickListener(v -> finish());
        topBar.getRightImg().setOnClickListener(v -> {
            showPopupWindow();
        });
        webView.loadUrl(url);
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_show_more, null, false);
        view.findViewById(R.id.fl_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //复制文本到粘贴板
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("Lable", url);
                cm.setPrimaryClip(cd);
                Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.fl_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //从其他浏览器打开
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.getContentView().setFocusable(true);
        popupWindow.getContentView().setFocusableInTouchMode(true);
        popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                final WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
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

    public static void actionStart(Context context, String link, String title) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(Constant.CONTENT_URL_KEY, link);
        intent.putExtra(Constant.CONTENT_TITLE_KEY, title);
        context.startActivity(intent);
    }
}
