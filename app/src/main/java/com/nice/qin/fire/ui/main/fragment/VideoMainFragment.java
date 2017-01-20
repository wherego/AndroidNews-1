package com.nice.qin.fire.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jaydenxiao.common.base.BaseFragment;
import com.nice.qin.fire.R;

import butterknife.Bind;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class VideoMainFragment extends BaseFragment {
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_video;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }
}
