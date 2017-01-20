package com.nice.qin.fire.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaydenxiao.common.base.BaseFragment;
import com.nice.qin.fire.R;
import com.nice.qin.fire.ui.news.fragment.ZhiHuFragment;


import butterknife.Bind;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class ZhiHuMainFragment extends BaseFragment{
    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.zhihuContent,new ZhiHuFragment());
        transaction.commit();
    }
}
