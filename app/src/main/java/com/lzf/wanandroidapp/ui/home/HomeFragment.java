package com.lzf.wanandroidapp.ui.home;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;
import com.lzf.wanandroidapp.mvp.presenter.HomePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContact.View {
    private static final String TAG = "HomeFragment";
    private List<Article> datas = new ArrayList<>();
    private List<Banner> bannerDatas;
    private HomePresenter mHomePresenter;
    HomeAdapter homeAdapter;
    private View BannerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private int curPage = 0;

    @Override
    public void initView() {

    }

    @Override
    public void loaderData() {

    }

    @Override
    public void initVariables() {
        mHomePresenter = new HomePresenter(this);
    }

    //调用了hide之后会调用的
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.rv_data);
        refreshLayout = root.findViewById(R.id.srl_refresh);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                curPage = 0;
                mHomePresenter.requestArticle(curPage);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mHomePresenter.requestArticle(curPage);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(20);//设置看不见的item的缓存数量，默认是2
        homeAdapter = new HomeAdapter(getActivity(), datas);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);
        mHomePresenter.requestBanner();
        mHomePresenter.requestArticle(curPage);
//        //给RecyclerView设置ItemTouchHelper
//        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(homeAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
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
        homeAdapter.setBanners(banner);
    }


    @Override
    public void finishRefresh(List<Article> articles) {
        Log.d(TAG, "finishRefresh: ");
        refreshLayout.finishRefresh();
        homeAdapter.setArticles(articles);
    }

    @Override
    public void finishLoadMore(List<Article> articles) {
        Log.d(TAG, "finishLoadMore: ");
        refreshLayout.finishLoadMore();
        homeAdapter.addArticles(articles);
    }

    @Override
    public void hideLoadMore() {
        refreshLayout.setEnableLoadMore(false);
    }

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
}