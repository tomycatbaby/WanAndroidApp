package com.lzf.wanandroidapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Banner;
import com.lzf.wanandroidapp.mvp.contract.HomeContact;
import com.lzf.wanandroidapp.mvp.presenter.HomePresenter;
import com.lzf.wanandroidapp.widget.RefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContact.View {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private List<Article> datas = new ArrayList<>();
    private List<Banner> bannerDatas;
    private HomePresenter mHomePresenter;
    HomeAdapter homeAdapter;
    private View BannerView;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;


    @Override
    public void initView() {

    }

    @Override
    public void loaderData() {

    }

    public void toTop(){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void initVariables() {
        mHomePresenter = new HomePresenter(this);
    }

    //调用了hide之后会调用的
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.init();
        refreshLayout.setOnRefresh(new RefreshLayout.CallBack() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.stop();
                    }
                },1000);
            }
        });
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(20);//设置看不见的item的缓存数量，默认是2
        homeAdapter = new HomeAdapter(getActivity(), datas);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);
        mHomePresenter.requestArticle(0);
        //给RecyclerView设置ItemTouchHelper
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(homeAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void scrollTopTop() {

    }

    @Override
    public void setBanner(List<Banner> banner) {

    }

    @Override
    public void setArticles(List<Article> articles) {
        recyclerView.setVisibility(View.VISIBLE);
        datas = articles;
        homeAdapter.setArticles(datas);
        homeAdapter.notifyDataSetChanged();
    }
    private Handler handler = new Handler();

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