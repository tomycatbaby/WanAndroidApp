package com.lzf.wanandroidapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.mvp.contract.RankContact;
import com.lzf.wanandroidapp.mvp.presenter.RankPresenter;

import java.util.List;

public class RankActivity extends BaseActivity implements RankContact.View {
    /**
     * 每页数据的个数
     */
    private static final int pageSize = 20;
    private RankPresenter rankPresenter;


    @Override
    public void initView() {
        setContentView(R.layout.activity_rank);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void loaderData() {
        rankPresenter.getRankList(1);
    }

    @Override
    public void initVariables() {
        rankPresenter = new RankPresenter(this);
    }


    @Override
    public void showRankList(List<Rank> list) {

    }
}
