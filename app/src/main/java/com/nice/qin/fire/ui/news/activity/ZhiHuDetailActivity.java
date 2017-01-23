package com.nice.qin.fire.ui.news.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaydenxiao.common.base.BaseActivity;
import com.nice.qin.fire.R;
import com.nice.qin.fire.app.AppConstant;
import com.nice.qin.fire.bean.ZhiHuDetail;
import com.nice.qin.fire.ui.news.contract.ZhiHuDetailContract;
import com.nice.qin.fire.ui.news.model.ZhiHuDetailModel;
import com.nice.qin.fire.ui.news.presenter.ZhiHuDetailPresenter;
import com.nice.qin.fire.util.HtmlUtil;

import butterknife.Bind;

public class ZhiHuDetailActivity extends BaseActivity<ZhiHuDetailPresenter,ZhiHuDetailModel>
implements ZhiHuDetailContract.View, View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.detail_bar_image)
    ImageView detailBarImg;
    @Bind(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @Bind(R.id.wv_detail_content)
    WebView detailContentWV;
    private String findId;

    public static void startAction(Context mContext,String id){
        Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
        intent.putExtra(AppConstant.ZHIHU_DETAIL_ID, id);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext,view,
                            AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            mContext.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }*/
        mContext.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_zhihu_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        SetTranslanteBar();
        findId =  getIntent().getStringExtra(AppConstant.ZHIHU_DETAIL_ID);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab.setOnClickListener(this);
        initWebViewClient();
        mPresenter.getZhiHuDetail(findId);
    }

    private void initWebViewClient() {
        WebSettings settings = detailContentWV.getSettings();
        settings.setBlockNetworkImage(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        detailContentWV.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void returnZhiHuDetail(ZhiHuDetail zhiHuDetail) {
        String imgUrl = zhiHuDetail.getImage();
        Glide.with(this).load(imgUrl).into(detailBarImg);
        mToolbar.setTitle(zhiHuDetail.getTitle());
        String htmlData = HtmlUtil.createHtmlData(zhiHuDetail.getBody(),zhiHuDetail.getCss(),zhiHuDetail.getJs());
        detailContentWV.loadData(htmlData,HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:break;
        }
    }
}
