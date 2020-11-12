package com.lzf.wanandroidapp.ui.home;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.ui.h5.ContentActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Article> articles;
    private List<Banner> banners;

    private static final int BANNER = 1;
    private static final int ARTICLE = 2;

    public HomeAdapter(Context context, List<Article> list) {
        this.mContext = context;
        this.articles = list;
    }

    public void setArticles(List<Article> list) {
        this.articles = list;
        notifyDataSetChanged();
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    public void addArticles(List<Article> list) {
        this.articles.addAll(list);
        notifyItemRangeInserted(getItemCount() - list.size(), list.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == BANNER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, viewGroup, false);
            return new ViewHolderBanner(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_list, viewGroup, false);
            return new ViewHolderArticle(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("lzf", "onBindViewHolder: "+position);
        if (getItemViewType(position) == ARTICLE) {
            ViewHolderArticle holder = (ViewHolderArticle) viewHolder;
            final Article article = getArticle(position);
            if (article.isFresh()) {
                holder.fresh.setVisibility(View.VISIBLE);
            }
            if ("1".equals(article.getTop())) {
                holder.fresh.setVisibility(View.VISIBLE);
            }
            if (article.getTags().size() > 0) {
                holder.tag.setVisibility(View.VISIBLE);
                holder.tag.setText(article.getTags().get(0).getName());
            }
            holder.title.setText(article.getTitle());
            holder.date.setText(article.getNiceDate());
            holder.chapterName.setText(article.getChapterName());
            holder.author.setText(article.getAuthor());
            if (!TextUtils.isEmpty(article.getEnvelopePic())) {
                //

                Glide.with(mContext)
                        .load(article.getEnvelopePic())
                        .into(holder.thumbnail);
                holder.thumbnail.setVisibility(View.VISIBLE);
            } else {
                holder.thumbnail.setVisibility(View.GONE);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            mContext, ContentActivity.class);
                    intent.putExtra(Constant.CONTENT_URL_KEY, article.getLink());
                    intent.putExtra(Constant.CONTENT_TITLE_KEY, article.getTitle());
                    intent.putExtra(Constant.CONTENT_ID_KEY, article.getId());
                    mContext.startActivity(intent);
                }
            });
        } else if (getItemViewType(position) == BANNER) {
            ViewHolderBanner holder = (ViewHolderBanner) viewHolder;
            for (int i = 0; i < banners.size(); i++) {
                Banner banner = banners.get(i);
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setOnClickListener(v -> ContentActivity.actionStart(mContext, banner.getUrl(), banner.getTitle()));
                Glide.with(mContext)
                        .load(banner.getImagePath())
                        .into(imageView);
                holder.flipper.addView(imageView);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && banners != null && !banners.isEmpty()) {
            return BANNER;
        }
        return ARTICLE;
    }

    @Override
    public int getItemCount() {
        if (banners != null && !banners.isEmpty()) {
            return articles.size() + 1;
        }
        return articles.size();
    }

    private Article getArticle(int position){
        if (banners != null && !banners.isEmpty()) {
            return articles.get(position-1);
        }
        return articles.get(position);
    }

    static class ViewHolderArticle extends RecyclerView.ViewHolder {
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
        View editFrame;
        View cardFrame;

        public ViewHolderArticle(View itemView) {
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
            editFrame = itemView.findViewById(R.id.edit_frame);
            cardFrame = itemView.findViewById(R.id.card_frame);
        }
    }

    static class ViewHolderBanner extends RecyclerView.ViewHolder {

        ViewFlipper flipper;

        public ViewHolderBanner(@NonNull View itemView) {
            super(itemView);
            flipper = itemView.findViewById(R.id.banner);
        }
    }
/*
    private int mMaxTranslateW = 1;
    private float mCurrentTranslateX = 0f;
    private float mCurrentTranslateXWhenInactive = 0f;
    private float mInitXWhenInactive = 0f;
    private boolean mFirstInactive = false;
    private float lastDX = 0f;
    private boolean needReport = false;

    public void onItemSwiping(Canvas c, RecyclerView.ViewHolder viewHolder,
                              float dX, float dY, boolean isCurrentlyActive) {
        ViewHolder vh = (ViewHolder) viewHolder;
        mMaxTranslateW = vh.editFrame.getWidth();//首先获取后面这一块的宽度
        Log.d("lzf", "onItemSwiping: " + isCurrentlyActive);
        if (dX == 0f) {
            needReport = false;
            mCurrentTranslateX = vh.cardFrame.getTranslationX();
            mFirstInactive = true;
            lastDX = 0f;
        }
        float diff = dX - lastDX;
        if (isCurrentlyActive) {
            mCurrentTranslateX += diff;
            float pos;
            if (mCurrentTranslateX >= 0) {
                pos = 0f;
            } else {
                pos = mCurrentTranslateX;
            }
            if (pos <= -mMaxTranslateW) {
                pos = -mMaxTranslateW;
            }
            if (mCurrentTranslateX < -mMaxTranslateW && !needReport) {
                needReport = true;
            }
            vh.cardFrame.setTranslationX(pos);
        } else {
            if (mFirstInactive) {
                mFirstInactive = false;
                mCurrentTranslateXWhenInactive = vh.cardFrame.getTranslationX();
                mInitXWhenInactive = dX;
            }
            if (Math.abs(vh.cardFrame.getTranslationX()) < mMaxTranslateW) {
                if (mInitXWhenInactive != 0f)
                    vh.cardFrame.setTranslationX(mCurrentTranslateXWhenInactive * dX / mInitXWhenInactive);

            }
        }
        lastDX = dX;
    }

    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        lastDX = 0f;
        if (Math.abs(vh.cardFrame.getTranslationX()) < mMaxTranslateW)
            enableBottomBtn((ViewHolder) viewHolder, false);
        else enableBottomBtn((ViewHolder) viewHolder, true);
    }

    private void enableBottomBtn(ViewHolder holder, boolean enable
    ) {
//        holder.isClickable = enable
//        holder.setEnabled(R.id.btn_delete, enable)
    }*/
}
