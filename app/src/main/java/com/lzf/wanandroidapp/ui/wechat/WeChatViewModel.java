package com.lzf.wanandroidapp.ui.wechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeChatViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public WeChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
