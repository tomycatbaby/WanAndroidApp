package com.lzf.wanandroidapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lzf.wanandroidapp.R
import kotlinx.android.synthetic.main.activity_h5.*
import kotlinx.android.synthetic.main.activity_kt.*
import kotlinx.android.synthetic.main.activity_kt.webView

class KtActivity : AppCompatActivity() {

    var thisFile :String  = "KtActivity"
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)

        Glide.with(this).applyDefaultRequestOptions(RequestOptions.circleCropTransform()).load(R.drawable.background).into(role);
        webView?.let {
            it.loadUrl("http://www.baidu.com")
            it.webViewClient = WebViewClient()
            it.settings.javaScriptEnabled = true
            it.settings.useWideViewPort = true
        }
        attention.setOnClickListener(View.OnClickListener {
            if (!flag){
                flag = true
                attention.text = "已关注"
                webView.reload()
            }else{
                flag = false
                attention.text = "+  关注"
            }

        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
