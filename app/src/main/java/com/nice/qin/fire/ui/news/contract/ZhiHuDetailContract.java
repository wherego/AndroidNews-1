package com.nice.qin.fire.ui.news.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.nice.qin.fire.bean.ZhiHuData;
import com.nice.qin.fire.bean.ZhiHuDetail;

import rx.Observable;

/**
 * Created by Qin on 2017-01-18-0018.
 */

public interface ZhiHuDetailContract {
    interface Model extends BaseModel {
        //请求获取
        Observable<ZhiHuDetail> getZhiHuDetail(String id);
    }
    interface View extends BaseView {
        //返回获取
        void returnZhiHuDetail(ZhiHuDetail zhiHuDetail);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取请求
        public abstract void getZhiHuDetail(String id);
    }
}
