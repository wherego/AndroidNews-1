package com.nice.qin.fire.ui.news.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.ZhiHuData;
import com.nice.qin.fire.bean.ZhiHuDataBean;
import com.nice.qin.fire.ui.news.contract.ZhiHuListContract;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Qin on 2017-01-17-0017.
 */

public class ZhiHuListPresenter extends ZhiHuListContract.Presenter {
    @Override
    public void getZhiHuTopList() {
        mRxManage.add(mModel.getZhiHuTopList()
                .subscribe(new RxSubscriber<ZhiHuData>(mContext, false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(ZhiHuData zhiHuData) {
                        mView.returnZhiHuTopList(zhiHuData.getTop_stories());
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                }));
    }

    @Override
    public void getZhiHuDataList(String date) {
        mRxManage.add(mModel.getZhiHuDataList(date)
                .subscribe(new RxSubscriber<ZhiHuData>(mContext, false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }
                    @Override
                    protected void _onNext(ZhiHuData zhiHuData) {
                        List<ZhiHuDataBean> list = new ArrayList<ZhiHuDataBean>();
                        for (ZhiHuDataBean zhiHuDataBean : zhiHuData.getStories()) {
                            zhiHuDataBean.setDate(zhiHuData.getDate());
                            list.add(zhiHuDataBean);
                        }
                        mView.returnZhiHuDataList(list);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                }));
    }
}
