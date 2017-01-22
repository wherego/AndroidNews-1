package com.nice.qin.fire.ui.news.fragment;

import com.jaydenxiao.common.base.BaseFragment;
import com.nice.qin.fire.bean.VideoData;
import com.nice.qin.fire.ui.news.contract.VideosListContract;
import com.nice.qin.fire.ui.news.model.VideoListModel;
import com.nice.qin.fire.ui.news.presenter.VideoListPresenter;

import java.util.List;

/**
 * Created by Qin on 2017-01-21-0021.
 */

public class VideoFragment extends BaseFragment<VideoListPresenter,VideoListModel>
implements VideosListContract.View{
    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void returnVideosListData(List<VideoData> newsSummaries) {

    }

    @Override
    public void scrolltoTop() {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
