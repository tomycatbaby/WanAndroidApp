package com.lzf.wanandroidapp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;
import com.lzf.wanandroidapp.mvp.presenter.HomePresenter;
import com.lzf.wanandroidapp.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showError(String errorMsg) {

    }

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