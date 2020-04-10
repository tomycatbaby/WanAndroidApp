package com.lzf.wanandroidapp.base;

public interface IView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 使用默认的样式显示信息: CustomToast
     */
    void showDefaultMsg(String msg);

    /**
     * 显示信息
     */
    void showMsg(String msg);

    /**
     * 显示错误信息
     */
    void showError(String errorMsg);
}
