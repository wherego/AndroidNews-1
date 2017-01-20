package com.nice.qin.fire.ui.news.model;

import com.nice.qin.fire.api.Api;
import com.nice.qin.fire.api.HostType;
import com.nice.qin.fire.bean.ZhiHuData;
import com.nice.qin.fire.ui.news.contract.ZhiHuListContract;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Qin on 2017-01-17-0017.
 */

public class ZhiHuListModel implements ZhiHuListContract.Model {
    /***
     * 获取知乎列表
     * @param date 日期
     * @return
     */
    @Override
    public Observable<ZhiHuData> getZhiHuDataList(String date) {
        return Api.getDefault(HostType.ZHIHU)
                .getZhiHuDataList(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 获取置顶的轮播知乎data
     * @return
     */
    @Override
    public Observable<ZhiHuData> getZhiHuTopList() {
        return Api.getDefault(HostType.ZHIHU)
                .getZhiHuTopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
