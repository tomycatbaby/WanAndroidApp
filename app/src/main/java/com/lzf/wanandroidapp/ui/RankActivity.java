package com.lzf.wanandroidapp.ui;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.mvp.contract.RankContact;
import com.lzf.wanandroidapp.mvp.presenter.RankPresenter;
import com.lzf.wanandroidapp.ui.adapter.RankAdapter;
import com.lzf.wanandroidapp.widget.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity implements RankContact.View {
    /**
     * 每页数据的个数
     */
    private static final int pageSize = 20;

    private List<Rank> rankList;
    private RankPresenter rankPresenter;
    private RankAdapter mAdapter;
    private RecyclerView rankListView;
    private LoadingLayout loadingLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_rank);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.score_list));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        rankListView = findViewById(R.id.recyclerView);
        loadingLayout = findViewById(R.id.fragment_refresh);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

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

    }

    @Override
    public void initVariables() {
        rankPresenter = new RankPresenter(this);
        List<Rank> list = new ArrayList<>();

        mAdapter = new RankAdapter(this,list);
        rankListView.setAdapter(mAdapter);
    }



    @Override
    public void loaderData() {
        loadingLayout.showLoading();
        rankPresenter.getRankList(1);
    }

    @Override
    public void showRankList(List<Rank> list) {

//        Log.d("lzf", " normal status: "+list.toString());
//
//        Collections.sort(list);//按字典序排列
//        Log.d("lzf", " Sorted status: "+list.toString());

        mAdapter.setRanks(list);
        mAdapter.notifyDataSetChanged();
        loadingLayout.showContent();
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
        loadingLayout.showError();
    }
}