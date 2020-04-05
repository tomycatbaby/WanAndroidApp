package com.lzf.wanandroidapp.http.api;

import com.google.gson.JsonObject;
import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Rank;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GeeksApis {
    String HOST = "https://www.wanandroid.com/";

    /**
     * 获取feed文章列表
     *
     * @param num 页数
     * @return feed文章列表数据
     */
    @GET("article/list/{num}/json")
    Observable<BaseResponse<ArticleList>> getArticleList(@Path("num") int num);

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<JsonObject> login(@Path("id") String id, @Path("page") String page);

    /**
     * 获取积分排行榜
     * https://www.wanandroid.com/coin/rank/1/json
     *
     * @param page 页码 从1开始
     */
    @GET("/coin/rank/{page}/json")
    Observable<BaseResponse<BaseListResponseBody<Rank>>> getRankList(@Path("page") int page);

}
