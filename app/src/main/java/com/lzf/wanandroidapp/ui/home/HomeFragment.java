package com.lzf.wanandroidapp.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSeekBar;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.core.DataManager;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.ArticleList;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;
import com.lzf.wanandroidapp.mvp.presenter.HomePresenter;
import com.lzf.wanandroidapp.widget.RefreshLayout;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment implements HomeContact.View {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private List<Article> datas = new ArrayList<>();
    private List<Banner> bannerDatas;
    private HomePresenter mHomePresenter;
    HomeAdapter homeAdapter;
    private View BannerView;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;


    @Override
    public void initView() {

    }

    @Override
    public void loaderData() {

    }

    public void toTop(){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void initVariables() {
        mHomePresenter = new HomePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.init();
        handler = new MyHandler();
        refreshLayout.setOnRefresh(new RefreshLayout.CallBack() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                handler.sendEmptyMessageDelayed(1,2000);
            }
        });
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(), datas);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);
        mHomePresenter.requestArticle(1);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void scrollTopTop() {

    }

    @Override
    public void setBanner(List<Banner> banner) {

    }

    @Override
    public void setArticles(List<Article> articles) {
        Log.d(TAG, "setArticles: ");
        recyclerView.setVisibility(View.VISIBLE);
        datas = articles;
        homeAdapter.setArticles(datas);
        homeAdapter.notifyDataSetChanged();
    }
    private MyHandler handler;
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG, "stop: ");
                    refreshLayout.stop();
                    break;
            }
        }
    }
}