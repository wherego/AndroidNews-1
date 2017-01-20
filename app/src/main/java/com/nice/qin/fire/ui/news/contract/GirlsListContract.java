package com.nice.qin.fire.ui.news.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.nice.qin.fire.bean.PhotoGirl;

import java.util.List;

import rx.Observable;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public interface GirlsListContract {
     interface Model extends BaseModel {
        //请求获取图片
        Observable<List<PhotoGirl>> getGirlsListData(int size, int page);
    }

    interface View extends BaseView {
        //返回获取的图片
        void returnGirlsListData(List<PhotoGirl> photoGirls);
    }
     abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取图片请求
        public abstract void getGirlsListDataRequest(int size, int page);
    }
}
