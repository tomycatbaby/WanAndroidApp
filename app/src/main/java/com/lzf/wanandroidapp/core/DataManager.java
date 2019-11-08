package com.lzf.wanandroidapp.core;

import android.util.Log;

import com.google.gson.JsonObject;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.http.api.GeeksApis;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager {
    private Retrofit retrofit;
    private static DataManager dataManager;

    private void init() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
    }

    private DataManager() {
        init();
    }

    public synchronized static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public Observable<BaseResponse<ArticleList>> getArticleList(int pageNum) {
        GeeksApis geeksApis = retrofit.create(GeeksApis.class);
        Observable<BaseResponse<ArticleList>> observable = geeksApis.getArticleList(pageNum);
        return observable;
    }

    public Observable<JsonObject> login(String page, String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        GeeksApis geeksApis = retrofit.create(GeeksApis.class);
        Observable<JsonObject> observable = geeksApis.login(page, id).doOnNext(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) {

            }
        });
        return observable;
    }
}
