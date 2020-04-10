package com.lzf.wanandroidapp.mvp.contract;

import com.lzf.wanandroidapp.base.IView;
import com.lzf.wanandroidapp.core.BaseResponse;
import com.lzf.wanandroidapp.entity.BaseListResponseBody;
import com.lzf.wanandroidapp.entity.Rank;

import java.util.List;

import io.reactivex.Observable;

public interface RankContact {
    interface View extends IView {
        void showRankList(List<Rank> list);
    }

    interface Presenter {
        void getRankList(int page);
    }

    interface Model {
        Observable<BaseResponse<BaseListResponseBody<Rank>>> getRankList(int page);
    }
}
