package com.nice.qin.fire.ui.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nice.qin.fire.R;
import com.nice.qin.fire.bean.VideoData;

import java.util.Random;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Qin on 2017-01-22-0022.
 */

public class VideoAdapter extends RecyclerArrayAdapter<VideoData> {
    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(parent);
    }

    public class VideoViewHolder extends BaseViewHolder<VideoData>{
        private ImageView mLogo;
        private TextView tv_from;
        private TextView tv_time;
        private JCVideoPlayerStandard player;

        public VideoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video);
            mLogo =$(R.id.iv_logo);
            tv_from = $(R.id.tv_from);
            tv_time = $(R.id.tv_play_time);
            player = $(R.id.videoplayer);
        }

        @Override
        public void setData(VideoData videoData) {
            Glide.with(getContext()).load(videoData.getTopicImg()).into(mLogo);

            tv_from.setText(videoData.getTopicName());
            tv_time.setText(String.valueOf(new Random().nextInt(10000)+"次播放"));

            player.setUp(videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());
            Glide.with(getContext()).load(videoData.getCover())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                    .crossFade()
                    .into(player.thumbImageView);

        }
    }
}
