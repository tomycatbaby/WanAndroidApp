package com.lzf.wanandroidapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.lzf.wanandroidapp.R
import kotlinx.android.synthetic.main.activity_kt.*

class KtActivity : AppCompatActivity() {

    var thisFile :String  = "KtActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
        kotlin_test_button.run {
            setOnClickListener {
                text = "收到点击事件X"
                Log.d(thisFile, "")
            }
        }
        Glide.with(this).asGif();
        when(thisFile){
            "1"->{ }
            "2"->{ }
            else -> {
                Log.d(thisFile, "啥也不是X")

            }
        }
    }

    fun Test() {

    }
}
