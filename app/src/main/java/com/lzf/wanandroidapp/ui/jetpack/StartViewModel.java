package com.lzf.wanandroidapp.ui.jetpack;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel类是被设计用来以可感知生命周期的方式存储和管理 UI 相关数据，
 * ViewModel中数据会一直存活即使 activity configuration发生变化，比如横竖屏切换的时候
 * 1、解决数据持久化问题
 * 打破原先使用onSaveInstanceState()机制保存和恢复数据的，只能保存少量可序列化和反序列化的数据的方式
 * 使用它甚至可以保存一个比较大的bitmap list
 * 2、异步回调问题
 * 通常当我们 app异步请求比较耗时的数据时，，
 * 之前我们在 Activity 或 fragment里接收这些回调。所以不得不考虑潜在的内存泄漏情况，
 * 比如 Activity 被销毁后接口请求才返回。处理这些问题，会给我们增添好多复杂的工作。
 * 但现在我们利用 ViewModel 处理数据回调，可以完美的解决此痛点
 */
public class StartViewModel extends ViewModel {
    private MutableLiveData<String> mutableLiveData;
    private MutableLiveData<List<String>> liveData;

    public MutableLiveData<String> getMutableLiveData() {
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public MutableLiveData<List<String>> getLiveData() {
        if (liveData == null){
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
}
