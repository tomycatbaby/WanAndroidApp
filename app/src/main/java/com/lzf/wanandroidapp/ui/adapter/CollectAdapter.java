package com.lzf.wanandroidapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.entity.Collect;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.ui.ContentActivity;

import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {

    private List<Collect> collects;
    private Context mContext;

    public CollectAdapter(@NonNull Context context, List<Collect> list) {
        mContext = context;
        collects = list;
    }

    public List<Collect> getCollects() {
        return collects;
    }

    public void setCollects(List<Collect> collects) {
        this.collects = collects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collect_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Collect collect = collects.get(i);
        Log.d("lzf", "onBindViewHolder: " + collect.toString());
        holder.title.setText(collect.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mContext, ContentActivity.class);
                intent.putExtra(Constant.CONTENT_URL_KEY, collect.getLink());
                intent.putExtra(Constant.CONTENT_TITLE_KEY, collect.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.tv_title);
        }

    }
}
