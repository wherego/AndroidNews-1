package com.nice.qin.fire.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.swipe.SwipeRefreshLayout;
import com.nice.qin.fire.R;
import com.nice.qin.fire.app.AppConstant;
import com.nice.qin.fire.bean.VideoData;
import com.nice.qin.fire.ui.news.adapter.VideoAdapter;
import com.nice.qin.fire.ui.news.contract.VideosListContract;
import com.nice.qin.fire.ui.news.model.VideoListModel;
import com.nice.qin.fire.ui.news.presenter.VideoListPresenter;

import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Qin on 2017-01-21-0021.
 */

public class VideoFragment extends BaseFragment<VideoListPresenter,VideoListModel>
implements VideosListContract.View{
    @Bind(R.id.recyclerView)
    EasyRecyclerView erc;
    private VideoAdapter adapter;
    private String mVideoType;
    private int mStartPage = 0;
    @Override
    protected int getLayoutResource() {
        return R.layout.framents_videos;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        erc.setAdapter(adapter = new VideoAdapter(getContext()));
        erc.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                mPresenter.getVideosListDataRequest(mVideoType,mStartPage);
            }

            @Override
            public void onMoreClick() {

            }
        });
        erc.setRefreshListener(new android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStartPage = 0;
                mPresenter.getVideosListDataRequest(mVideoType,mStartPage);
            }
        });
        if (adapter.getAllData().size() <= 0) {
            mPresenter.getVideosListDataRequest(mVideoType,mStartPage);
        }
    }

    @Override
    public void returnVideosListData(List<VideoData> videoDatas) {
        if (videoDatas != null) {
            adapter.addAll(videoDatas);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void scrolltoTop() {
        erc.scrollToPosition(0);
    }

    @Override
    public void showLoading(String title) {
        erc.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        erc.setRefreshing(false);
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.show(msg,1);
    }
}
