package com.lzf.wanandroidapp.ui.wxarticle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.Constant
import com.lzf.wanandroidapp.entity.Article
import com.lzf.wanandroidapp.ui.h5.ContentActivity
import com.lzf.wanandroidapp.ui.adapter.ArticleAdapter
import com.lzf.wanandroidapp.utils.WanDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_article.*
import java.util.*
import kotlin.collections.ArrayList

class ArticleFragment : Fragment() {

    var wxid: Int = 0
    var page = 1
    val adapter = ArticleAdapter(ArrayList())

    private val vm: WXArticleViewModel by lazy {
        ViewModelProviders.of(this).get(WXArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("id").run {
            wxid = this!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_article.layoutManager = LinearLayoutManager(activity)
        rv_article.addItemDecoration(WanDividerItemDecoration(Objects.requireNonNull(activity), DividerItemDecoration.VERTICAL,false))
        rv_article.adapter = adapter
        srl_article.setOnRefreshListener {
            page = 0
            vm.getArticleList(page, wxid)
        }
        srl_article.setOnLoadMoreListener {
            page++
            adapter.removeAllFooterView()
            vm.getArticleList(page, wxid)
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            run {
                val article = adapter.getItem(position) as Article
                val intent = Intent(
                        activity, ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_URL_KEY, article.link)
                intent.putExtra(Constant.CONTENT_TITLE_KEY, article.title)
                intent.putExtra(Constant.CONTENT_ID_KEY, article.id)
                startActivity(intent)
            }
        }
        initData()
    }



    private fun initData() {
        adapter.setNewInstance(ArrayList())
        vm.article.observe(this, Observer {
            adapter.addData(it.datas)
            srl_article.finishLoadMore()
            srl_article.finishRefresh()
            if (it.curPage >= it.pageCount) {
                srl_article.setEnableLoadMore(false)
                adapter.setFooterView(LayoutInflater.from(context).inflate(R.layout.no_more_foot,null),0,LinearLayout.VERTICAL)
            }
        })
        vm.getArticleList(page, wxid)
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int): Fragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }
}