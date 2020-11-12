package com.lzf.wanandroidapp.ui.knowledge

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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class KnowledgeViewModel : ViewModel() {
    private val model = KnowLedgeModel()
    var tree = MutableLiveData<List<Tree>>()
    val article = MutableLiveData<BaseListResponseBody<Article>>()
    fun getTreeList() {
        model.tree.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(object : Observer<BaseResponse<List<Tree>>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: BaseResponse<List<Tree>>) {
                tree.value = t.data
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    fun getArticleList(page: Int, cid: Int) {
        Log.d("lzf", ":$page  :$cid")
        model.getArticleList(page, cid).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(object : Observer<BaseResponse<BaseListResponseBody<Article>>> {
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

fun createFlow(): Flow<Int> = flow {
    for (i in 1..10)
        emit(i)
}