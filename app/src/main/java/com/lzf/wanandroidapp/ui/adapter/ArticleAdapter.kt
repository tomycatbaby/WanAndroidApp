package com.lzf.wanandroidapp.ui.adapter


import android.os.Debug
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.ifIsNullOrEmpty
import com.lzf.wanandroidapp.entity.Article
import com.lzf.wanandroidapp.utils.ResUtils

class ArticleAdapter(data: MutableList<Article?>?) : BaseQuickAdapter<Article?, BaseViewHolder>(R.layout.item_article, data) {
    override fun convert(holder: BaseViewHolder, item: Article?) {
        holder.setText(R.id.tv_article_author, ResUtils.getString(R.string.author, item?.author.ifIsNullOrEmpty("")))
        holder.setText(R.id.tv_article_title, item?.title.ifIsNullOrEmpty(""))
        holder.setText(R.id.tv_article_date, ResUtils.getString(R.string.time, item?.niceDate.ifIsNullOrEmpty("")))
    }
}