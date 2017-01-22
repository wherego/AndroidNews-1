package com.nice.qin.fire.ui.news.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.CycleViewInfo;
import com.nice.qin.fire.bean.ZhiHuDataBean;
import com.nice.qin.fire.bean.ZhiHuTopBean;
import com.nice.qin.fire.ui.news.activity.ZhiHuDetailActivity;
import com.nice.qin.fire.ui.news.adapter.ZhiHuAdapter;
import com.nice.qin.fire.ui.news.contract.ZhiHuListContract;
import com.nice.qin.fire.ui.news.model.ZhiHuListModel;
import com.nice.qin.fire.ui.news.presenter.ZhiHuListPresenter;
import com.nice.qin.fire.util.MyUtils;
import com.nice.qin.fire.widget.CycleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Qin on 2017-01-17-0017.
 */

public class ZhiHuFragment extends BaseFragment<ZhiHuListPresenter, ZhiHuListModel>
        implements ZhiHuListContract.View, CycleViewPager.ImageCycleViewListener {
    @Bind(R.id.cycleViewPager)
    CycleViewPager mCycleViewPager;
    @Bind(R.id.recyclerView)
    EasyRecyclerView erc;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private ZhiHuAdapter adapter;
    private List<CycleViewInfo> mCycleInfoList = new ArrayList<>();
    private int mDayOffSet = 0;
    private String mCurrentDate;

    @Override
    protected int getLayoutResource() {
        return R.layout.framents_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrolltoTop();
            }
        });*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrolltoTop();
            }
        });
        /**初始化mCurrentDate*/
        setDayOffSet(mDayOffSet);
        mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
        mCycleViewPager.setDelay(4000);
        erc.setAdapter(adapter = new ZhiHuAdapter(getContext()));
        erc.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) DisplayUtil.dip2px(4));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        erc.addItemDecoration(itemDecoration);
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                mPresenter.getZhiHuDataList(mCurrentDate);
            }

            @Override
            public void onMoreClick() {

            }
        });
        erc.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setDayOffSet(0);
                mPresenter.getZhiHuDataList(mCurrentDate);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ZhiHuDataBean bean = adapter.getItem(position);
                startZhiHuDetail(String.valueOf(bean.getId()));
            }
        });
        if (mCycleInfoList.size() <= 0) {
            mPresenter.getZhiHuTopList();
        }
        if (adapter.getAllData().size() <= 0) {
            mPresenter.getZhiHuDataList(mCurrentDate);
        }
    }

    @Override
    public void returnZhiHuTopList(List<ZhiHuTopBean> zhiHuBean) {
        mCycleInfoList.clear();
        if (zhiHuBean != null) {
            for (int i = 0; i < zhiHuBean.size(); i++) {
                mCycleInfoList.add(new CycleViewInfo(zhiHuBean.get(i).getImage(), zhiHuBean.get(i).getTitle(), String.valueOf(zhiHuBean.get(i).getId())));
            }
        }
        mCycleViewPager.setData(mCycleInfoList, this);
    }

    @Override
    public void returnZhiHuDataList(List<ZhiHuDataBean> zhiHuBean) {
        if (zhiHuBean != null) {
            setDayOffSet(mDayOffSet - 1);
            adapter.addAll(zhiHuBean);
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
        ToastUitl.show(msg, 1);
    }

    public void setDayOffSet(int dayOffSet) {
        mDayOffSet = dayOffSet;
        mCurrentDate = MyUtils.getCurrentDate(mDayOffSet);
    }

    @Override
    public void onImageClick(CycleViewInfo info, int position, View imageView) {
        startZhiHuDetail(info.getId());
    }

    private void startZhiHuDetail(String id) {
        ZhiHuDetailActivity.startAction(getContext(), id);
    }
}
