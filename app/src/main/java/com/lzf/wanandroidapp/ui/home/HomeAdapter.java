package com.lzf.wanandroidapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.ui.ContentActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> articles;

    public HomeAdapter(Context context, List<Article> list) {
        this.mContext = context;
        this.articles = list;
    }

    public void setArticles(List<Article> list) {
        this.articles = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article article = articles.get(position);
        if (article.isFresh()){
            holder.fresh.setVisibility(View.VISIBLE);
        }
        if ("1".equals(article.getTop())){
            holder.fresh.setVisibility(View.VISIBLE);
        }
        if (article.getTags().size()>0){
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(article.getTags().get(0).getName());
        }
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getNiceDate());
        holder.chapterName.setText(article.getChapterName());
        holder.author.setText(article.getAuthor());
        if (!TextUtils.isEmpty(article.getEnvelopePic())) {
            Glide.with(mContext)
                    .load(article.getEnvelopePic())
                    .into(holder.thumbnail);
        }else {
            holder.thumbnail.setVisibility(View.GONE);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mContext, ContentActivity.class);
                intent.putExtra(Constant.CONTENT_URL_KEY,article.getLink());
                intent.putExtra(Constant.CONTENT_TITLE_KEY,article.getTitle());
                intent.putExtra(Constant.CONTENT_ID_KEY,article.getId());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        TextView top;
        TextView fresh;
        TextView date;
        TextView author;
        ImageView thumbnail;
        TextView title;
        TextView chapterName;
        ImageView like;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tag = itemView.findViewById(R.id.tv_article_tag);
            top = itemView.findViewById(R.id.tv_article_top);
            fresh = itemView.findViewById(R.id.tv_article_fresh);
            date = itemView.findViewById(R.id.tv_article_date);
            author = itemView.findViewById(R.id.tv_article_author);
            thumbnail = itemView.findViewById(R.id.iv_article_thumbnail);
            title = itemView.findViewById(R.id.tv_article_title);
            chapterName = itemView.findViewById(R.id.tv_article_chapterName);
            like = itemView.findViewById(R.id.iv_like);
        }
    }
}
