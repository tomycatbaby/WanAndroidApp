package com.lzf.wanandroidapp.mvp.model;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.http.RetrofitHelper;
import com.lzf.wanandroidapp.mvp.contract.RankContact;

import io.reactivex.Observable;

public class RankModel implements RankContact.Model {
    @Override
    public Observable<BaseResponse<BaseListResponseBody<Rank>>> getRankList(int page) {
        return RetrofitHelper.getInstance().getApis().getRankList(page);
    }
}
