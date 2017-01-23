package com.nice.qin.fire.ui.main.activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaydenxiao.common.base.BaseActivity;
import com.nice.qin.fire.R;
import com.nice.qin.fire.app.AppConstant;
import com.nice.qin.fire.bean.TabEntity;
import com.nice.qin.fire.ui.main.fragment.GirlMainFragment;
import com.nice.qin.fire.ui.main.fragment.VideoMainFragment;
import com.nice.qin.fire.ui.news.fragment.ZhiHuFragment;

import java.util.ArrayList;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private String[] mTitles = {"首页", "妹纸", "视频", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private static int tabLayoutHeight;

    GirlMainFragment girlMainFragment;
    ZhiHuFragment zhihuFragment;
    VideoMainFragment videoFragment;
    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTab();
        //监听菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean hideOrShow) {
                startAnimation(hideOrShow);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0, 0);
        tabLayoutHeight = tabLayout.getMeasuredHeight();
    }

    /**
     * 初始化TabLayout
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    /**
     * 菜单显示隐藏动画
     *
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {

        } else {
            girlMainFragment = new GirlMainFragment();
            zhihuFragment = new ZhiHuFragment();
            videoFragment = new VideoMainFragment();

            transaction.add(R.id.fl_body, zhihuFragment, "zhihuFragment");
            transaction.add(R.id.fl_body, girlMainFragment, "girlsMainFragment");
            transaction.add(R.id.fl_body, videoFragment, "videoFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 跳转
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.show(zhihuFragment);
                transaction.hide(girlMainFragment);
                transaction.hide(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(zhihuFragment);
                transaction.show(girlMainFragment);
                transaction.hide(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(zhihuFragment);
                transaction.hide(girlMainFragment);
                transaction.show(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(zhihuFragment);
                transaction.hide(girlMainFragment);
                transaction.hide(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
