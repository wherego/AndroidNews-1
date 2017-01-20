package com.nice.qin.fire.ui.news.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.nice.qin.fire.api.Api;
import com.nice.qin.fire.api.HostType;
import com.nice.qin.fire.bean.ZhiHuData;
import com.nice.qin.fire.bean.ZhiHuDetail;
import com.nice.qin.fire.ui.news.contract.ZhiHuDetailContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Qin on 2017-01-18-0018.
 */

public class ZhiHuDetailModel implements ZhiHuDetailContract.Model {
    @Override
    public Observable<ZhiHuDetail> getZhiHuDetail(String id) {
        return Api.getDefault(HostType.ZHIHU)
                .getZhiHuDetail(id)
                .compose(RxSchedulers.<ZhiHuDetail>io_main());
    }
}
