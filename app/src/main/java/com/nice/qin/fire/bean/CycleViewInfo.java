package com.nice.qin.fire.bean;

import android.support.annotation.VisibleForTesting;

/**
 * Created by Gavin on 2016/8/31.
 */
public class CycleViewInfo {
    private String url;
    private String title;
    private String id;

    public CycleViewInfo(String url, String title, String id) {
        this.url = url;
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
