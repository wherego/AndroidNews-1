package com.nice.qin.fire.ui.news.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.nice.qin.fire.api.Api;
import com.nice.qin.fire.api.ApiService;
import com.nice.qin.fire.api.HostType;
import com.nice.qin.fire.bean.GirlData;
import com.nice.qin.fire.bean.PhotoGirl;
import com.nice.qin.fire.ui.news.contract.GirlsListContract;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class GirlsListModel implements GirlsListContract.Model{

    @Override
    public Observable<List<PhotoGirl>> getGirlsListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getGirlsList(Api.getCacheControl(),size, page)
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                })
                .compose(RxSchedulers.<List<PhotoGirl>>io_main());
    }
}
