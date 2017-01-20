package com.nice.qin.fire.ui.news.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.ZhiHuDetail;
import com.nice.qin.fire.ui.news.contract.ZhiHuDetailContract;

/**
 * Created by Qin on 2017-01-18-0018.
 */

public class ZhiHuDetailPresenter extends ZhiHuDetailContract.Presenter {
    @Override
    public void getZhiHuDetail(String id) {
        mRxManage.add(mModel.getZhiHuDetail(id)
        .subscribe(new RxSubscriber<ZhiHuDetail>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ZhiHuDetail zhiHuDetail) {
                mView.returnZhiHuDetail(zhiHuDetail);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
