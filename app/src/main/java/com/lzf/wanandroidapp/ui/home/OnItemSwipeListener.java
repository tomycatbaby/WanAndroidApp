package com.lzf.wanandroidapp.ui.home;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemSwipeListener {
    void clearView(RecyclerView.ViewHolder viewHolder, int pos);
    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos);
}
