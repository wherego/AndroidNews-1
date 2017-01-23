package com.nice.qin.fire.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
        //滑动出屏幕外解决办法https://github.com/lipangit/JieCaoVideoPlayer/issues/381
        erc.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int _firstItemPosition = -1, _lastItemPosition;
            private View fistView, lastView;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    //获取可见view的总数
                    int visibleItemCount = linearManager.getChildCount();

                    if (_firstItemPosition < firstItemPosition) {
                        _firstItemPosition = firstItemPosition;
                        _lastItemPosition = lastItemPosition;
                        GCView(fistView);
                        fistView = recyclerView.getChildAt(0);
                        lastView = recyclerView.getChildAt(visibleItemCount - 1);
                    } else if (_lastItemPosition > lastItemPosition) {
                        _firstItemPosition = firstItemPosition;
                        _lastItemPosition = lastItemPosition;
                        GCView(lastView);
                        fistView = recyclerView.getChildAt(0);
                        lastView = recyclerView.getChildAt(visibleItemCount - 1);
                    }
                }
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
    public void GCView(View gcView) {
        if (gcView != null && gcView.findViewById(R.id.videoplayer) != null) {
            JCVideoPlayerStandard video = (JCVideoPlayerStandard) gcView.findViewById(R.id.videoplayer);
            if (video != null && (video.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING
                    || video.currentState == JCVideoPlayer.CURRENT_STATE_ERROR)) {
                video.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE);
                JCVideoPlayerStandard.releaseAllVideos();
            }
        }
    }
}
