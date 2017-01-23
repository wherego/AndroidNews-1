package com.nice.qin.fire.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaydenxiao.common.base.BaseFragmentAdapter;
import com.nice.qin.fire.R;
import com.nice.qin.fire.app.AppConstant;
import com.nice.qin.fire.bean.VideoChannelTable;
import com.nice.qin.fire.db.VideosChannelTableManager;
import com.nice.qin.fire.ui.news.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class VideoMainFragment extends Fragment{
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private BaseFragmentAdapter fragmentAdapter;
    private View mRootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.app_bar_video, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        List<String> channelNames = new ArrayList<>();
        List<VideoChannelTable> videoChannelTables = VideosChannelTableManager.loadVideosChannelsMine();
        List<Fragment> videoFragmentList = new ArrayList<>();
        for (int i = videoChannelTables.size() - 1; i >= 0; i--) {
            channelNames.add(videoChannelTables.get(i).getChannelName());
            videoFragmentList.add(CreateFragment(videoChannelTables.get(i)));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),videoFragmentList,channelNames);
        mViewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(mViewPager);
        setPageChangeListener();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rxbus
            }
        });
    }

    private void setPageChangeListener() {

    }

    private Fragment CreateFragment(VideoChannelTable videoChannelTable) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.VIDEO_TYPE,videoChannelTable.getChannelId());
        fragment.setArguments(bundle);
        return fragment;
    }

}
