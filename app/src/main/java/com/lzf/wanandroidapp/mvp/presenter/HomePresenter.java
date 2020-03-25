package com.lzf.wanandroidapp.mvp.presenter;

import android.util.Log;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;
import com.lzf.wanandroidapp.mvp.model.HomeModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Service层，用来处理返回的数据，处理好的数据赋给View展示
 */
public class HomePresenter implements HomeContact.Presenter {

    private HomeContact.View homeView;
    private HomeContact.Model model = new HomeModel();
    private String TAG = "HomePresenter";

    public HomePresenter(HomeContact.View view) {
        this.homeView = view;
    }

    @Override
    public void requestBanner() {

    }

    @Override
    public void requestHomeData() {

    }

    @Override
    public void requestArticle(int num) {
        Observable<BaseResponse<ArticleList>> observable = model.requestArticle(num);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse<ArticleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticleList> baseResponse) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                        ArticleList articleList = baseResponse.getData();
                        homeView.setArticles(articleList.getDatas());
                        //datas = articleList.getDatas();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
