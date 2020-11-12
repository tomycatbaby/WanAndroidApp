package com.lzf.wanandroidapp.ui.mine.score

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.BaseActivity
import com.lzf.wanandroidapp.ui.mine.MineViewModel
import com.lzf.wanandroidapp.utils.ToastUtil
import com.lzf.wanandroidapp.utils.WanDividerItemDecoration
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : BaseActivity() {

    var curPage = 1

    private val adapter = ScoreAdapter()
    private val vm: MineViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    override fun initView() {
        setContentView(R.layout.activity_score)
        top_bar.leftImg.setOnClickListener {
            finish()
        }
        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.addItemDecoration(WanDividerItemDecoration(this, WanDividerItemDecoration.VERTICAL))
        rv_data.adapter = adapter
        srl_refresh.setOnLoadMoreListener {
            curPage++
            vm.getScoreCostList(curPage)
        }
        srl_refresh.setOnRefreshListener {
            curPage = 1
            vm.getScoreCostList(curPage)
        }
    }

    override fun loaderData() {
        ld_score.showLoading()
        vm.scoreCost.observe(this, Observer {
            srl_refresh.finishLoadMore()
            srl_refresh.finishRefresh()
            if (curPage == 1) {
                ld_score.showContent()
                adapter.setList(it.datas)
            } else {
                adapter.addData(it.datas)
            }
            if (curPage >= it.pageCount) {
                srl_refresh.setEnableLoadMore(false)
                adapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.no_more_foot, null), 0, LinearLayout.VERTICAL)
            }
        })
        vm.error.observe(this, Observer {
            ToastUtil.show(it)
        })
        vm.getScoreCostList(curPage)
    }

    override fun initVariables() {
    }

    override fun showLoading() {
    }

    override fun showMsg(msg: String?) {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String?) {
    }

    override fun showDefaultMsg(msg: String?) {
    }
}