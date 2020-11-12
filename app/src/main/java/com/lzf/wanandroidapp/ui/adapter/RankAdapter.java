package com.lzf.wanandroidapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.entity.Rank;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RankAdapter extends BaseQuickAdapter<Rank, BaseViewHolder> {


    public RankAdapter(int layoutResId, @Nullable List<Rank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Rank rank) {
        baseViewHolder.setText(R.id.tv_username, rank.getUsername());
        baseViewHolder.setText(R.id.tv_score, String.valueOf(rank.getCoinCount()));
        baseViewHolder.setText(R.id.tv_ranking, String.valueOf(baseViewHolder.getAdapterPosition() + 1));
    }

}
