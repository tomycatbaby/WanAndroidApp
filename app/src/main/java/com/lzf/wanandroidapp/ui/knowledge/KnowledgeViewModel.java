package com.lzf.wanandroidapp.ui.knowledge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KnowledgeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public KnowledgeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}