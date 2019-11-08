package com.lzf.wanandroidapp.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.entity.Article;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> articles;

    public HomeAdapter(Context context, List<Article> list) {
        this.mContext = context;
        this.articles = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_list, viewGroup, false);
        Log.d("lzf", "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        Log.d("lzf", "onBindViewHolder: "+article.getEnvelopePic());
        holder.tag.setText(article.getAuthor());
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getNiceDate());
        holder.chapterName.setText(article.getChapterName());
        holder.author.setText(article.getAuthor());
        Glide.with(mContext)
                .load(article.getEnvelopePic())
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        TextView date;
        TextView author;
        ImageView thumbnail;
        TextView title;
        TextView chapterName;
        ImageView like;

        public ViewHolder(View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tv_article_tag);
            date = itemView.findViewById(R.id.tv_article_date);
            author = itemView.findViewById(R.id.tv_article_author);
            thumbnail = itemView.findViewById(R.id.iv_article_thumbnail);
            title = itemView.findViewById(R.id.tv_article_title);
            chapterName = itemView.findViewById(R.id.tv_article_chapterName);
            like = itemView.findViewById(R.id.iv_like);
        }
    }
}
