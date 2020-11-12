package com.lzf.wanandroidapp.ui.wxarticle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzf.wanandroidapp.core.BaseResponse
import com.lzf.wanandroidapp.entity.Article
import com.lzf.wanandroidapp.entity.BaseListResponseBody
import com.lzf.wanandroidapp.entity.Tree
import com.lzf.wanandroidapp.mvp.model.KnowLedgeModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WXArticleViewModel : ViewModel() {
    private val model = KnowLedgeModel()
    var chapters = MutableLiveData<List<Tree>>()
    val article = MutableLiveData<BaseListResponseBody<Article>>()

    fun getWXChapters() {
        model.wxChapters.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<BaseResponse<List<Tree>>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseResponse<List<Tree>>) {
                chapters.value = t.data
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    fun getArticleList(page: Int, id: Int) {
        model.getWXArticleList(page, id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<BaseResponse<BaseListResponseBody<Article>>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseResponse<BaseListResponseBody<Article>>) {
                article.value = t.data
            }

            override fun onError(e: Throwable) {
            }

        })
    }
}