package com.lzf.wanandroidapp.http.api;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Login;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.entity.ScoreCost;
import com.lzf.wanandroidapp.entity.ScoreInfo;
import com.lzf.wanandroidapp.entity.Tree;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("/banner/json")
    Observable<BaseResponse<List<Banner>>> getBanner();

    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResponse<Login>> login(@Field("username") String username, @Field("password") String password);

    @GET("/lg/coin/userinfo/json")
    Observable<BaseResponse<ScoreInfo>> getScoreInfo();

    /**
     * 获取积分排行榜
     * https://www.wanandroid.com/coin/rank/1/json
     *
     * @param page 页码 从1开始
     */
    @GET("/coin/rank/{page}/json")
    Observable<BaseResponse<BaseListResponseBody<Rank>>> getRankList(@Path("page") int page);

    @GET("/tree/json")
    Observable<BaseResponse<List<Tree>>> getTree();

    @GET("/article/list/{page}/json")
    Observable<BaseResponse<BaseListResponseBody<Article>>> getTreeArticle(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取公众号列表,查看某个公众号历史数据
     */
    @GET("/wxarticle/chapters/json")
    Observable<BaseResponse<List<Tree>>> getWXChapters();

    @GET("/wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<BaseListResponseBody<Article>>> getWXArticleList(@Path("page") int page, @Path("id") int id);

    /**
     *
     * 获取个人积分明细列表
     *
     */
    @GET("/lg/coin/list/{page}/json")
    Observable<BaseResponse<BaseListResponseBody<ScoreCost>>> getScoreCostList(@Path("page") int page);

}
