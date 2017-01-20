package com.nice.qin.fire.ui.news.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nice.qin.fire.R;
import com.nice.qin.fire.app.AppConstant;
import com.nice.qin.fire.util.MyUtils;
import com.nice.qin.fire.util.SystemUiVisibilityUtil;
import com.nice.qin.fire.widget.PullBackLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotosDetailActivity extends AppCompatActivity implements PullBackLayout.Callback {

    @Bind(R.id.pull_back_layout)
    PullBackLayout pullBackLayout;
    @Bind(R.id.photoView)
    PhotoView photoView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;

    public static void startAction(Context context, String url) {
        Intent intent = new Intent(context, PhotosDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photos_detail);
        ButterKnife.bind(this);
        SetToolTransparant();
        initView();
    }
    private void SetToolTransparant(){
        /**这个是设置透明状态栏，属性设置没用！*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        pullBackLayout.setCallback(this);
        initToolbar();
        initBackground();
        loadPhotoIv();
        setPhotoViewClickEvent();
    }
    private void initBackground() {
        /**为什么在不设置这个的情况下dragger下拉没反应*/
        mBackground = new ColorDrawable(Color.BLACK);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackground);
    }
    private void setPhotoViewClickEvent() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolBar();
                hideOrShowStatusBar();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //mPresenter.savePhoto(mPhotoUrl);
                return true;
            case R.id.action_share:
                //mPresenter.sharePhoto(mPhotoUrl);
                return true;
            case R.id.action_set_wallpaper:
                //mPresenter.setWallpaper(mPhotoUrl);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPhotoIv() {
        String url = getIntent().getStringExtra(AppConstant.PHOTO_DETAIL);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                .crossFade().into(photoView);
    }

    protected void hideOrShowToolBar() {
        mIsToolBarHidden = !mIsToolBarHidden;
        if (mIsToolBarHidden) {
            startAnimation(true, 1.0f, 0.0f);
        } else {
            startAnimation(false, 0.1f, 1.0f);
        }
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotosDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(PhotosDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }
    private void startAnimation(final boolean endState, float startValue, float endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1;
                if (endState) {
                    float v1 = 0 - animation.getAnimatedFraction();
                    y1 = v1 * toolbar.getHeight();
                } else {
                    float v2 = animation.getAnimatedFraction() - 1;
                    y1 = v2 * toolbar.getHeight();
                }
                toolbar.setTranslationY(y1);
            }
        });
        animator.start();
    }
    private void toolbarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolBar();
    }

    private void toolbarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolBar();
    }

    @Override
    public void onPullStart() {
        toolbarFadeOut();

        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolbarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }
}
