package com.nice.qin.fire.ui.news.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.nice.qin.fire.bean.ZhiHuDataBean;
import com.nice.qin.fire.bean.ZhiHuTopBean;
import com.nice.qin.fire.bean.ZhiHuData;

import java.util.List;

import rx.Observable;


/**
 * Created by Qin on 2017-01-17-0017.
 */

public interface ZhiHuListContract {
    interface Model extends BaseModel {
        Observable<ZhiHuData> getZhiHuDataList(String date);
        Observable<ZhiHuData> getZhiHuTopList();
    }
    interface View extends BaseView {
        //返回获取的知乎列表
        public void returnZhiHuTopList(List<ZhiHuTopBean> zhiHuBean);
        public void returnZhiHuDataList(List<ZhiHuDataBean> zhiHuBean);
        //返回顶部
        void scrolltoTop();
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取知乎请求
        public abstract void getZhiHuTopList();
        public abstract void getZhiHuDataList(String date);
    }
}
