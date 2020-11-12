package com.lzf.wanandroidapp.http;

import com.lzf.wanandroidapp.base.App;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.http.api.GeeksApis;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static Retrofit retrofit;
    private static GeeksApis apis;
    private static RetrofitHelper helper;
    private static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.MINUTES);

    private RetrofitHelper() {

    }

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public synchronized static RetrofitHelper getInstance() {
        if (helper == null) {
            helper = new RetrofitHelper();
        }
        return helper;
    }

    public GeeksApis getApis() {
        if (apis == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apis = retrofit.create(GeeksApis.class);
        }
        return apis;
    }

    private OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(App.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, Constant.MAX_CACHE_SIZE);
        builder.addInterceptor(interceptor).cache(cache)
                .connectionPool(connectionPool)
                .connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }
}
