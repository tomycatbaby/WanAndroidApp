package com.lzf.wanandroidapp.ui.wxarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lzf.wanandroidapp.R
import kotlinx.android.synthetic.main.fragment_slideshow.*

class WXArticleFragment : Fragment() {
    private val vm: WXArticleViewModel by lazy {
        ViewModelProviders.of(this).get(WXArticleViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        initData()
        return root
    }

    private fun initData() {
        vm.chapters.observe(this, Observer {
            tab_indicator.setupWithViewPager(view_pager)
            view_pager.currentItem = 0
            view_pager.adapter = ArticleFragmentPagerAdapter(fragmentManager, it)
        })
        vm.getWXChapters()
    }

}