package com.lzf.wanandroidapp.utils;

import android.widget.Toast;

import com.lzf.wanandroidapp.base.App;

public class ToastUtil {
    public static void show(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
