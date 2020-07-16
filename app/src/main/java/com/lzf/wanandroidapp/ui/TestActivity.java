package com.lzf.wanandroidapp.ui;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.ui.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_test);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add("频道"+i);
        }
        TestAdapter testAdapter = new TestAdapter(list);
        recyclerView.setAdapter(testAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.HORIZONTAL,false));
    }

    @Override
    public void loaderData() {

    }

    @Override
    public void initVariables() {

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
