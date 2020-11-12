package com.lzf.wanandroidapp.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lzf.wanandroidapp.R
import com.lzf.wanandroidapp.base.Constant
import com.lzf.wanandroidapp.base.SettingUtil
import com.lzf.wanandroidapp.ui.BasicActivity
import com.lzf.wanandroidapp.ui.RankActivity
import com.lzf.wanandroidapp.ui.TestActivity
import com.lzf.wanandroidapp.ui.activity.CollectActivity
import com.lzf.wanandroidapp.ui.mine.score.ScoreActivity
import com.lzf.wanandroidapp.utils.query
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.no_more_foot.*

class MineFragment : Fragment() {
    private val vm: MineViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
    }

    private fun initData() {
        tv_score.setOnClickListener {
            val intent = Intent(requireActivity(), ScoreActivity::class.java)
            startActivity(intent)
        }
        tv_like.setOnClickListener {
            val i = Intent(requireActivity(), CollectActivity::class.java)
            startActivity(i)
        }
        tv_night.setOnClickListener {
            if (SettingUtil.getIsNightMode()) {
                SettingUtil.setNightMode(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                SettingUtil.setNightMode(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            requireActivity().window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
            requireActivity().recreate()
        }
        tv_about.setOnClickListener {
            val i1 = Intent(requireActivity(), TestActivity::class.java)
            startActivity(i1)
        }
        iv_rank.setOnClickListener {
            val i1 = Intent(requireActivity(), RankActivity::class.java)
            startActivity(i1)
        }
        tv_username.setOnClickListener {
            if (query(Constant.IS_LOGIN, false)) {
                val username = query(Constant.USERNAME,"")
                tv_username.text = username
            } else {
                LoginActivity.actionStart(requireActivity())
            }
        }
        if (query(Constant.IS_LOGIN, false)) {
            val username = query(Constant.USERNAME,"")
            tv_username.text = username
            vm.getScoreInfo()
        }else{
            tv_username.text = "去登录"
        }

        vm.scoreInfo.observe(this, Observer {
            tv_user_grade.text = it.coinCount.toString()
            tv_user_rank.text = it.rank.toString()
            tv_username.text = it.username.toString()
        })
    }
}