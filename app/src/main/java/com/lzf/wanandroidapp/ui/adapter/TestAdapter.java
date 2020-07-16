package com.lzf.wanandroidapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lzf.wanandroidapp.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestAdapter(List<String> data) {
        super(R.layout.item_test_list, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_username, s);
    }
}
