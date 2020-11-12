package com.lzf.wanandroidapp.ui.knowledge

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.ifIsNullOrEmpty
import com.lzf.wanandroidapp.entity.Tree
import com.lzf.wanandroidapp.utils.ResUtils

class TreeAdapter(list: MutableList<Tree?>?) : BaseQuickAdapter<Tree?, BaseViewHolder>(R.layout.item_tree_node, list) {

    var curIndex = 0
    var action: ((tree: Tree?) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: Tree?) {
        holder.setText(R.id.tv_text, item?.name.ifIsNullOrEmpty(""))
        holder.getView<TextView>(R.id.tv_text).setOnClickListener {
            curIndex = holder.adapterPosition
            notifyDataSetChanged()
            action?.invoke(item)
        }
        if (holder.adapterPosition == curIndex) {
            holder.setBackgroundResource(R.id.tv_text, R.drawable.bg_3975f6)
            holder.setTextColor(R.id.tv_text, ResUtils.getColor(R.color.White))
        } else {
            holder?.setBackgroundResource(R.id.tv_text, R.drawable.background_roundcorners_ccc)
            holder?.setTextColor(R.id.tv_text, ResUtils.getColor(R.color.color_666666))
        }
    }
}