package com.lzf.wanandroidapp.ui.mine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.BaseActivity
import com.lzf.wanandroidapp.base.Constant
import com.lzf.wanandroidapp.utils.insert
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val vm: MineViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    override fun initView() {
        setContentView(R.layout.activity_login)
        top_bar.leftImg.setOnClickListener {
            finish()
        }
        loginButton.setOnClickListener {
            vm.login(et_username.text.toString(), et_password.text.toString())
        }
    }

    override fun loaderData() {
        vm.loginSuccess.observe(this, Observer {
            insert(Constant.USERNAME, it.username)
            insert(Constant.IS_LOGIN, true)
            finish()
        })
        vm.loginFailed.observe(this, Observer {
            Toast.makeText(this, "登陆失败", Toast.LENGTH_LONG).show()

        })
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

    companion object {

        @JvmStatic
        fun actionStart(context: Context) {
            val i1 = Intent(context, LoginActivity::class.java)
            context.startActivity(i1)
        }
    }
}