package com.nice.qin.fire.ui.news.model;

import com.nice.qin.fire.bean.VideoData;
import com.nice.qin.fire.ui.news.contract.VideosListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Qin on 2017-01-21-0021.
 */

public class VideoListModel implements VideosListContract.Model {
    @Override
    public Observable<List<VideoData>> getVideosListData(String type, int startPage) {
        return null;
    }
}
