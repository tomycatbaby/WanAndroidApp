package com.lzf.wanandroidapp.mvp.contract;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.entity.HttpResult;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContact {
    interface View {
        void scrollTopTop();
        void setBanner(List<Banner> banner);
        void setArticles(List<Article> articles);
    }

    interface Presenter {
        void requestBanner();
        void requestHomeData();
        void requestArticle(int num);
    }

    interface Model {
        Observable<HttpResult> requestBanner();
        Observable<BaseResponse<ArticleList>> requestArticle(int num);
    }
}
