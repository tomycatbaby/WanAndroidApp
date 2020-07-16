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


class KtActivity : AppCompatActivity() {

    var thisFile :String  = "KtActivity"
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
    }

    override fun onResume() {
        super.onResume()
    }
    
}
