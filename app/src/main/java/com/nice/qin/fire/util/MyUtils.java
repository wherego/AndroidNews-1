/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.nice.qin.fire.util;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import com.jaydenxiao.common.commonutils.TimeUtil;
import com.nice.qin.fire.app.MyApplication;

import java.util.Calendar;
import java.util.Date;


/**
 * @author 咖枯
 * @version 1.0 2016/5/31
 */
public class MyUtils {

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWith() {
        return MyApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /***
     * 获取系统日期 格式20170102
     *
     * @param dayOffSet 日期偏移量 正数向前，负数向后
     * @return
     */
    public static String getCurrentDate(int dayOffSet) {
        Calendar c = Calendar.getInstance();
        String cYear, cMonth, cDay;
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + dayOffSet);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        cYear = String.valueOf(year);
        cMonth = String.valueOf(month);
        cDay = String.valueOf(day);
        if (month / 10 == 0) cMonth = "0" + cMonth;
        if (day / 10 == 0) cDay = "0" + cDay;
        return cYear + cMonth + cDay;
    }

    public static String getFormatDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, date.length());
        return year + "-" + month + "-" + day;
    }
}
