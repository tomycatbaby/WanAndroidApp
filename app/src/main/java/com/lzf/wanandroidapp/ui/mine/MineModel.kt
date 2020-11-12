package com.lzf.wanandroidapp.ui.mine

import com.lzf.wanandroidapp.core.BaseResponse
import com.lzf.wanandroidapp.core.RepositoryManager
import com.lzf.wanandroidapp.entity.BaseListResponseBody
import com.lzf.wanandroidapp.entity.Login
import com.lzf.wanandroidapp.entity.ScoreCost
import com.lzf.wanandroidapp.entity.ScoreInfo
import com.lzf.wanandroidapp.http.api.GeeksApis
import io.reactivex.Observable

class MineModel {
    fun login(username: String, password: String): Observable<BaseResponse<Login>> {
        return RepositoryManager.getInstance().obtainRetrofitService(GeeksApis::class.java).login(username, password)
    }

    fun getScoreInfo(): Observable<BaseResponse<ScoreInfo>> {
        return RepositoryManager.getInstance().obtainRetrofitService(GeeksApis::class.java).getScoreInfo()
    }

    fun getScoreCostList(page: Int): Observable<BaseResponse<BaseListResponseBody<ScoreCost>>> {
        return RepositoryManager.getInstance().obtainRetrofitService(GeeksApis::class.java).getScoreCostList(page)
    }
}