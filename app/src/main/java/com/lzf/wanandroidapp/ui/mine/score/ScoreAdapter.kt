package com.lzf.wanandroidapp.ui.mine.score

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.entity.ScoreCost

class ScoreAdapter(layoutResId: Int = R.layout.item_score) : BaseQuickAdapter<ScoreCost, BaseViewHolder>(layoutResId) {
    override fun convert(holder: BaseViewHolder, item: ScoreCost) {
        holder.setText(R.id.tv_title, item.reason)
        holder.setText(R.id.tv_desc, item.desc)
    }

}