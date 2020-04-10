package com.lzf.wanandroidapp.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.entity.Rank;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private List<Rank> ranks;
    private Context mContext;

    public RankAdapter(@NonNull Context context, List<Rank> list) {
        mContext = context;
        ranks = list;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Rank rank = ranks.get(i);
        Log.d("lzf", "onBindViewHolder: " + rank.toString());
        holder.username.setText(rank.getUsername());
        holder.score.setText(String.valueOf(rank.getCoinCount()));
        holder.ranking.setText(String.valueOf(i + 1));
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView score;
        TextView ranking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username);
            score = itemView.findViewById(R.id.tv_score);
            ranking = itemView.findViewById(R.id.tv_ranking);
        }

    }
}
