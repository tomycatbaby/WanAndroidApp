package com.lzf.wanandroidapp.core;

import com.lzf.wanandroidapp.base.App;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.http.RetrofitHelper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryManager {
    private static volatile RepositoryManager instance;
    private RxJava2CallAdapterFactory rxJava2CallAdapterFactory;
    private static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.MINUTES);

    public static RepositoryManager getInstance() {
        if (instance == null) {
            synchronized (RepositoryManager.class) {
                if (instance == null) {
                    instance = new RepositoryManager();
                }
            }
        }
        return instance;
    }

    private RepositoryManager() {
        rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)               //主域  .baseUrl(Constant.Net.DOMAIN_MARBLE)
                .client(getOkHttpClient())
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T obtainRetrofitService(Class<T> service) {
        T retrofitService = createService(service);
        return retrofitService;
    }

    public <T> T obtainService(Class<T> service) {
        return getInstance().obtainRetrofitService(service);
    }

    private Retrofit mRetrofit;
    private HttpCookieJar cookieJar = new HttpCookieJar();

    private <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
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
                .cookieJar(cookieJar)
                .writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }
}
