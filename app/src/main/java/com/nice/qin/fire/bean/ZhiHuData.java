package com.nice.qin.fire.bean;

import java.util.List;

/**
 * Created by Qin on 2017-01-17-0017.
 */

public class ZhiHuData {
    private String date;
    private List<ZhiHuDataBean> stories;
    private List<ZhiHuTopBean> top_stories;

    public List<ZhiHuDataBean> getStories() {
        return stories;
    }

    public void setStories(List<ZhiHuDataBean> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhiHuTopBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhiHuTopBean> top_stories) {
        this.top_stories = top_stories;
    }
}
