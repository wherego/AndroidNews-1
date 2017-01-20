package com.nice.qin.fire.app;

import android.content.Context;

import com.jaydenxiao.common.baseapp.BaseApplication;

/**
 * Created by Qin on 2016-11-12-0012.
 */

public class MyApplication extends BaseApplication {
    private static Context mInstance;

    public static Context getInstance(){
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
