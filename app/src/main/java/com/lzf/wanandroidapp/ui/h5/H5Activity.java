package com.lzf.wanandroidapp.ui.h5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzf.wanandroidapp.R;

public class H5Activity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        webView = findViewById(R.id.webView);
        loadWeb();
    }

    public void loadWeb() {
        //String url = "https://www.baidu.com/";
        String url = "file:///android_asset/index.html";
        //此方法可以在webview中打开链接而不会跳转到外部浏览器
        WebViewClient webViewClient = new WebViewClient() {
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            /**
             * 在webview加载URL的时候可以截获这个动作, 这里主要说它的返回值的问题：
             *  1、返回: return true;  webview处理url是根据程序来执行的。
             *  2、返回: return false; webview处理url是在webview内部执行。
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null)
                    return false;
                try {
                    if (url.startsWith("main://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            /**
             * 在webview开始加载页面的时候回调该方法
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        };
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        //支持alert弹窗
        webView.setWebChromeClient(new WebChromeClient() {

        });

    }


    //重载onKeyDown的函数，使其在页面内回退,而不是直接退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
