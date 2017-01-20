package com.nice.qin.fire.api;


import com.nice.qin.fire.bean.GirlData;
import com.nice.qin.fire.bean.ZhiHuData;
import com.nice.qin.fire.bean.ZhiHuDetail;


import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import retrofit2.http.Query;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {
    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getGirlsList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);
    @GET("api/4/news/before/{date}")
    Observable<ZhiHuData>getZhiHuDataList(@Path("date")String date);
    @GET("api/4/news/latest")
    Observable<ZhiHuData>getZhiHuTopList();
    @GET("api/4/news/{id}")
    Observable<ZhiHuDetail>getZhiHuDetail(@Path("id")String id);
}

