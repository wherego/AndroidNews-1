package com.nice.qin.fire.ui.news.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.OnItemClickListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.CycleViewInfo;
import com.nice.qin.fire.bean.ZhiHuDataBean;
import com.nice.qin.fire.bean.ZhiHuTopBean;
import com.nice.qin.fire.ui.news.activity.ZhiHuDetailActivity;
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
        implements ZhiHuListContract.View, OnRefreshListener, OnLoadMoreListener,
        CycleViewPager.ImageCycleViewListener, OnItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.cycleViewPager)
    CycleViewPager mCycleViewPager;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
   /* @Bind(R.id.fab)
    FloatingActionButton fab;*/
    private CommonRecycleViewAdapter<ZhiHuDataBean> adapter;
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
        /*final int leftRightPadding = getResources().getDimensionPixelSize(R.dimen.padding_size_l);
        final int topBottomPadding = getResources().getDimensionPixelOffset(R.dimen.padding_size_s);*/
        mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
        mCycleViewPager.setDelay(4000);
        /*irc.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(leftRightPadding,topBottomPadding,leftRightPadding,topBottomPadding);
            }
        });*/
        adapter = new CommonRecycleViewAdapter<ZhiHuDataBean>(getContext(), R.layout.item_new) {
            @Override
            public void convert(ViewHolderHelper helper, ZhiHuDataBean zhiHuBean,int position) {
                Log.d("wcq","position="+position);
                ImageView imageView = helper.getView(R.id.news_picture_iv);
                TextView news_title_tv = helper.getView(R.id.news_title_tv);
                Glide.with(getContext()).load(zhiHuBean.getImages().get(0))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .centerCrop()
                        .crossFade().
                        into(imageView);
                news_title_tv.setText(zhiHuBean.getTitle());
            }
        };
        adapter.setOnItemClickListener(this);
        adapter.openLoadAnimation(new ScaleInAnimation());
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        irc.setAdapter(adapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        if (adapter.getSize() <= 0) {
            mCurrentDate = MyUtils.getCurrentDate(mDayOffSet);
            mPresenter.getZhiHuDataList(mCurrentDate);
        }
        if (mCycleInfoList.size() <= 0) {
            mPresenter.getZhiHuTopList();
        }
    }

    @Override
    public void returnZhiHuTopList(List<ZhiHuTopBean> zhiHuBean) {
        mCycleInfoList.clear();
        if (zhiHuBean != null) {
            for (int i = 0; i < zhiHuBean.size(); i++) {
                mCycleInfoList.add(new CycleViewInfo(zhiHuBean.get(i).getImage(),zhiHuBean.get(i).getTitle(),String.valueOf(zhiHuBean.get(i).getId())));
            }
        }
        mCycleViewPager.setData(mCycleInfoList, this);
    }

    @Override
    public void returnZhiHuDataList(List<ZhiHuDataBean> zhiHuBean) {
        if (zhiHuBean != null) {
            setDayOffSet(mDayOffSet-1);
            if (adapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                adapter.replaceAll(zhiHuBean);
            } else {
                if (zhiHuBean.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(zhiHuBean);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void scrolltoTop() {
        irc.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        setDayOffSet(0);
        irc.setRefreshing(true);
        mPresenter.getZhiHuTopList();
        mPresenter.getZhiHuDataList(mCurrentDate);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getZhiHuDataList(mCurrentDate);
    }

    @Override
    public void showLoading(String title) {
        if (adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if (adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }
    public void setDayOffSet(int dayOffSet) {
        mDayOffSet = dayOffSet;
        mCurrentDate = MyUtils.getCurrentDate(mDayOffSet);
    }
    @Override
    public void onImageClick(CycleViewInfo info, int position, View imageView) {
        ZhiHuDetailActivity.startAction(getContext(),info.getId());
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        Log.d("wcq","click position="+position);
        ZhiHuDataBean bean = (ZhiHuDataBean) o;
        ZhiHuDetailActivity.startAction(getContext(),String.valueOf(bean.getId()));
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }
}
