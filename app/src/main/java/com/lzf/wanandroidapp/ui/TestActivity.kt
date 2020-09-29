package com.lzf.wanandroidapp.ui

import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.BaseActivity
import com.lzf.wanandroidapp.widget.IndicatorDrawable
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {
    override fun initView() {
        setContentView(R.layout.activity_test)

    }

    override fun loaderData() {}
    override fun initVariables() {}
    override fun showLoading() {}
    override fun hideLoading() {}
    override fun showDefaultMsg(msg: String) {}
    override fun showMsg(msg: String) {}
    override fun showError(errorMsg: String) {}
}