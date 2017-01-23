package com.nice.qin.fire.db;

import com.nice.qin.fire.R;
import com.nice.qin.fire.app.MyApplication;
import com.nice.qin.fire.bean.VideoChannelTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Qin on 2017-01-22-0022.
 */

public class VideosChannelTableManager {
    public static List<VideoChannelTable>  loadVideosChannelsMine(){
        List<VideoChannelTable> list = new ArrayList<>();
        List<String> ChannelNameList = Arrays.asList(MyApplication.getInstance().getResources().getStringArray(R.array.video_channel_name));
        List<String> ChannelIdList = Arrays.asList(MyApplication.getInstance().getResources().getStringArray(R.array.video_channel_id));
        for (int i = 0; i < ChannelNameList.size(); i++) {
            VideoChannelTable bean = new VideoChannelTable(ChannelIdList.get(i),ChannelNameList.get(i));
            list.add(bean);
        }
        return list;
    }
}
