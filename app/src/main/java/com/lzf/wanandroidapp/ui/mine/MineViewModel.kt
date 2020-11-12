package com.lzf.wanandroidapp.ui.mine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzf.wanandroidapp.core.BaseResponse
import com.lzf.wanandroidapp.entity.BaseListResponseBody
import com.lzf.wanandroidapp.entity.Login
import com.lzf.wanandroidapp.entity.ScoreCost
import com.lzf.wanandroidapp.entity.ScoreInfo
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MineViewModel : ViewModel() {
    private val model = MineModel()

    val loginSuccess = MutableLiveData<Login>()
    val scoreInfo = MutableLiveData<ScoreInfo>()
    val loginFailed = MutableLiveData<String>()
    val scoreCost = MutableLiveData<BaseListResponseBody<ScoreCost>>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        model.login(username, password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(object : Observer<BaseResponse<Login>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseResponse<Login>) {
                Log.d("lzf", "onNext: " + t.toString())
                if (t.errorCode == 0) {
                    loginSuccess.value = t.data
                } else {
                    loginFailed.value = t.errorMsg
                }
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    fun getScoreInfo() {
        model.getScoreInfo().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(object : Observer<BaseResponse<ScoreInfo>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseResponse<ScoreInfo>) {
                Log.d("lzf", "onNext: " + t.toString())
                if (t.errorCode == 0) {
                    scoreInfo.value = t.data
                }
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    fun getScoreCostList(page: Int) {
        model.getScoreCostList(page).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(object : Observer<BaseResponse<BaseListResponseBody<ScoreCost>>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseResponse<BaseListResponseBody<ScoreCost>>) {
                        if (t.errorCode == 0) {
                            scoreCost.value = t.data
                        } else {
                            error.value = t.errorMsg
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }
}