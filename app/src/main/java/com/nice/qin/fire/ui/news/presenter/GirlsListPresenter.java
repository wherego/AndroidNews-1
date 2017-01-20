package com.nice.qin.fire.ui.news.presenter;


import com.jaydenxiao.common.baserx.RxSubscriber;
import com.nice.qin.fire.R;
import com.nice.qin.fire.api.Api;
import com.nice.qin.fire.bean.GirlData;
import com.nice.qin.fire.bean.PhotoGirl;
import com.nice.qin.fire.ui.news.contract.GirlsListContract;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class GirlsListPresenter extends GirlsListContract.Presenter {
    @Override
    public void getGirlsListDataRequest(int size, int page) {
        mRxManage.add(mModel.getGirlsListData(size,page)
                .subscribe(new RxSubscriber<List<PhotoGirl>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(List<PhotoGirl> photoGirls) {
                        mView.returnGirlsListData(photoGirls);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                }));
    }
}
