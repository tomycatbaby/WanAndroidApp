package com.lzf.wanandroidapp.ui.wechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class WeChatFragment extends BaseFragment {
    private WeChatViewModel weChatViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        weChatViewModel =
                ViewModelProviders.of(this).get(WeChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_knowledge, container, false);
        final TextView textView = root.findViewById(R.id.text_knowledge);
        weChatViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void initView() {

    }

    @Override
    public void loaderData() {

    }

    @Override
    public void initVariables() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
