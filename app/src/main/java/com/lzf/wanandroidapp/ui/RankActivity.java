package com.lzf.wanandroidapp.ui;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;

import android.view.View;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.mvp.contract.RankContact;
import com.lzf.wanandroidapp.mvp.presenter.RankPresenter;
import com.lzf.wanandroidapp.ui.adapter.RankAdapter;
import com.lzf.wanandroidapp.widget.LoadingLayout;
import com.lzf.wanandroidapp.widget.WanRefreshFooter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity implements RankContact.View {

    String TAG = "RankActivity";
    private RankPresenter rankPresenter;
    private RankAdapter mAdapter;
    private RecyclerView rankListView;
    private LoadingLayout loadingLayout;
    private SmartRefreshLayout refreshLayout;
    private int curPage = 1;

    @Override
    public void initView() {
        setContentView(R.layout.activity_rank);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.score_list));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        rankListView = findViewById(R.id.rv_data);
        loadingLayout = findViewById(R.id.loading_refresh);
        refreshLayout = findViewById(R.id.srl_refresh);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rankListView.setItemAnimator(null);
        rankListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rankListView.setLayoutManager(linearLayoutManager);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                loaderData();
            }
        });
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setRefreshFooter(new WanRefreshFooter(this));
    }

    @Override
    public void initVariables() {
        rankPresenter = new RankPresenter(this);
        List<Rank> list = new ArrayList<>();

        mAdapter = new RankAdapter(R.layout.item_rank_list, list);
        rankListView.setAdapter(mAdapter);
        showLoading();
    }


    @Override
    public void loaderData() {
        rankPresenter.getRankList(curPage);
    }

    @Override
    public void showRankList(List<Rank> list) {

//        Log.d("lzf", " normal status: "+list.toString());
//
//        Collections.sort(list);//按字典序排列
//        Log.d("lzf", " Sorted status: "+list.toString());

        mAdapter.addData(list);
        loadingLayout.showContent();
    }

    @Override
    public void hideLoadMore() {
        //refreshLayout.setEnableLoadMore(false);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void showLoading() {

        loadingLayout.showLoading();
//        Intent i  = new Intent("");
//        i.addCategory("11");
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse(""),"");
//        PackageManager packageManager = getPackageManager();
//        //去判断是否有这个activity，后面的标志位表示只去判断category声明为default的activity
//        packageManager.resolveActivity(i,PackageManager.MATCH_DEFAULT_ONLY);

    }


    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showError(String errorMsg) {
        loadingLayout.showError();
    }
}
