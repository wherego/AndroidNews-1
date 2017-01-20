package com.nice.qin.fire.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.PhotoGirl;
import com.nice.qin.fire.ui.news.activity.PhotosDetailActivity;
import com.nice.qin.fire.ui.news.contract.GirlsListContract;
import com.nice.qin.fire.ui.news.model.GirlsListModel;
import com.nice.qin.fire.ui.news.presenter.GirlsListPresenter;

import java.util.List;

import butterknife.Bind;


/**
 * Created by Qin on 2016-11-12-0012.
 */

public class GirlMainFragment extends BaseFragment<GirlsListPresenter,GirlsListModel>
        implements GirlsListContract.View, OnLoadMoreListener,OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /*@Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;*/
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private CommonRecycleViewAdapter<PhotoGirl>adapter;
    private static int SIZE = 20;
    private int mStartPage = 1;
    @Override
    protected int getLayoutResource() {
        return R.layout.act_photos_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //initSwipeRefreshLayout();
        initRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irc.smoothScrollToPosition(0);
            }
        });
        mPresenter.getGirlsListDataRequest(SIZE,mStartPage);
    }

    private void initRecyclerView() {
        adapter = new CommonRecycleViewAdapter<PhotoGirl>(getContext(), R.layout.item_photo) {
            @Override
            public void convert(ViewHolderHelper helper, final PhotoGirl photoGirl,int position) {
                final ImageView imageView = helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .centerCrop().override(1090,1090*3/4)
                        .crossFade().into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotosDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
            }
        };
        irc.setAdapter(adapter);
        irc.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
    }

    private void initSwipeRefreshLayout() {
        /*mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);*/
    }
    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getGirlsListDataRequest(SIZE,mStartPage);
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        mStartPage= 0;
        irc.setRefreshing(true);
        mPresenter.getGirlsListDataRequest(SIZE,mStartPage);
    }

    @Override
    public void returnGirlsListData(List<PhotoGirl> photoGirls) {
        if (photoGirls!=null){
            mStartPage +=1;
            //mSwipeRefreshLayout.setRefreshing(false);
            if (adapter.getPageBean().isRefresh()){
                irc.setRefreshing(false);
                adapter.replaceAll(photoGirls);
            }else{
                if (photoGirls.size()>0){
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(photoGirls);
                }else{
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if (adapter.getPageBean().isRefresh()){
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        //mSwipeRefreshLayout.setRefreshing(false);
        if( adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }
}
