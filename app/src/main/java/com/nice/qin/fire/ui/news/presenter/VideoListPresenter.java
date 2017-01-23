package com.nice.qin.fire.ui.news.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.VideoData;
import com.nice.qin.fire.ui.news.contract.VideosListContract;

import java.util.List;

/**
 * Created by Qin on 2017-01-21-0021.
 */

public class VideoListPresenter extends VideosListContract.Presenter {
    @Override
    public void getVideosListDataRequest(String type, int startPage) {
        mRxManage.add(mModel.getVideosListData(type,startPage)
        .subscribe(new RxSubscriber<List<VideoData>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getResources().getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<VideoData> videoDatas) {
                mView.returnVideosListData(videoDatas);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
