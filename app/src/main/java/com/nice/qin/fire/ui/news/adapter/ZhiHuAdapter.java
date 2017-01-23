package com.nice.qin.fire.ui.news.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.ZhiHuDataBean;
import com.nice.qin.fire.ui.news.activity.ZhiHuDetailActivity;
import com.nice.qin.fire.ui.news.fragment.ZhiHuFragment;
import com.nice.qin.fire.util.MyUtils;

/**
 * Created by Qin on 2017-01-21-0021.
 */

public class ZhiHuAdapter extends RecyclerArrayAdapter<ZhiHuDataBean> {
    public ZhiHuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhiHuViewHolder(parent);
    }
    public class ZhiHuViewHolder extends BaseViewHolder<ZhiHuDataBean>{
        private ImageView mImageView;
        private TextView mTextView;
        private TextView timeTextView;
        public ZhiHuViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_new);
            mImageView =$(R.id.news_picture_iv);
            mTextView = $(R.id.news_title_tv);
            timeTextView = $(R.id.news_ptime_tv);
        }

        @Override
        public void setData(ZhiHuDataBean zhiHuBean) {
            Glide.with(getContext()).load(zhiHuBean.getImages().get(0))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                    .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                    .centerCrop()
                    .crossFade().
                    into(mImageView);
            mTextView.setText(zhiHuBean.getTitle());
            timeTextView.setText(MyUtils.getFormatDate(zhiHuBean.getDate()));
        }
    }
}
