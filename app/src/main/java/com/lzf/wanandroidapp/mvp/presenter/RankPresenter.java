package com.lzf.wanandroidapp.mvp.presenter;

import android.util.Log;

import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.mvp.contract.RankContact;
import com.lzf.wanandroidapp.mvp.model.RankModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankPresenter implements RankContact.Presenter {
    private RankContact.Model model = new RankModel();
    private RankContact.View rankView;

    public RankPresenter(RankContact.View rankView) {
        this.rankView = rankView;
    }

    @Override
    public void getRankList(int page) {
        Observable<BaseResponse<BaseListResponseBody<Rank>>> observable = model.getRankList(page);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<BaseResponse<BaseListResponseBody<Rank>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<BaseListResponseBody<Rank>> baseListResponseBodyBaseResponse) {
                BaseListResponseBody<Rank> responseBody = baseListResponseBodyBaseResponse.getData();
                rankView.showRankList(responseBody.getDatas());
                Log.d("lzf", "onNext: " + responseBody.getDatas().toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
