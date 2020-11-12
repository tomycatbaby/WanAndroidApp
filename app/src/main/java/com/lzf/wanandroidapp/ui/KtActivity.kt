package com.lzf.wanandroidapp.ui

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.BaseActivity
import com.lzf.wanandroidapp.utils.ResUtils
import com.lzf.wanandroidapp.utils.WindowUtils

import kotlinx.android.synthetic.main.activity_kt.*
import java.util.*


class KtActivity : BaseActivity() {

    var thisFile: String = "KtActivity"
    var flag = false
    override fun hideLoading() {
    }

    override fun showDefaultMsg(msg: String?) {
    }

    override fun initVariables() {
    }

    override fun loaderData() {
    }

    override fun initView() {
    }

    override fun showMsg(msg: String?) {
    }

    override fun showError(errorMsg: String?) {
    }

    override fun showLoading() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
        WindowUtils.setTransparentStatusBar(window)
        WindowUtils.setLightStatus(window)

    }

    fun createVideoThumbnail(filePath: String, kind: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, Hashtable())
            } else {
                retriever.setDataSource(filePath)
            }
            bitmap = retriever.getFrameAtTime(-1)
        } catch (ex: RuntimeException) {
            // Assume this is a corrupt video file
            ex.printStackTrace()
        } // Assume this is a corrupt video file.
        finally {
            try {
                retriever.release()
            } catch (ex: RuntimeException) {
                // Ignore failures while cleaning up.
                ex.printStackTrace()
            }
        }
//        if (bitmap == null) return null
//        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
//            // Scale down the bitmap if it's too large.
//            val width = bitmap.width
//            val height = bitmap.height
//            val max = Math.max(width, height)
//            if (max > 512) {
//                val scale = 512f / max
//                val w = Math.round(scale * width)
//                val h = Math.round(scale * height)
//                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true)
//            }
//        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
//                    96,
//                    96,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT)
//        }
        return bitmap
    }

}
