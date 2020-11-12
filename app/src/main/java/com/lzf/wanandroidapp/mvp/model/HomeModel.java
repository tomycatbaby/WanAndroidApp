package com.lzf.wanandroidapp.mvp.model;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.entity.HttpResult;
import com.lzf.wanandroidapp.http.RetrofitHelper;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;

import java.util.List;

import io.reactivex.Observable;

/**
 * DAO层，获取数据
 */
public class HomeModel implements HomeContact.Model {
    @Override
    public Observable<BaseResponse<List<Banner>>> requestBanner() {
        Observable<BaseResponse<List<Banner>>> observable = RetrofitHelper.getInstance().getApis().getBanner();
        return observable;
    }

    @Override
    public Observable<BaseResponse<ArticleList>> requestArticle(int num) {
        Observable<BaseResponse<ArticleList>> observable = RetrofitHelper.getInstance().getApis().getArticleList(num);
        return observable;
    }
}
