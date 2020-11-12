package com.lzf.wanandroidapp.mvp.model;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.core.RepositoryManager;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.entity.Tree;
import com.lzf.wanandroidapp.http.RetrofitHelper;
import com.lzf.wanandroidapp.http.api.GeeksApis;

import java.util.List;

import io.reactivex.Observable;

public class KnowLedgeModel {
    public Observable<BaseResponse<List<Tree>>> getTree() {
        return RepositoryManager.getInstance().obtainService(GeeksApis.class).getTree();
    }

    public Observable<BaseResponse<BaseListResponseBody<Article>>> getArticleList(int page, int cid) {
        return RepositoryManager.getInstance().obtainService(GeeksApis.class).getTreeArticle(page, cid);
    }

    public Observable<BaseResponse<List<Tree>>> getWXChapters() {
        return RepositoryManager.getInstance().obtainService(GeeksApis.class).getWXChapters();
    }

    public Observable<BaseResponse<BaseListResponseBody<Article>>> getWXArticleList(int page, int id) {
        return RepositoryManager.getInstance().obtainService(GeeksApis.class).getWXArticleList(page, id);
    }
}
