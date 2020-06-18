package com.lzf.wanandroidapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lzf.wanandroidapp.R
import kotlinx.android.synthetic.main.activity_kt.*

class KtActivity : AppCompatActivity() {

    var thisFile :String  = "KtActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)

        //Glide.with(this).applyDefaultRequestOptions(RequestOptions.circleCropTransform()).load(R.mipmap.ic_launcher).into(role);
    }

}
