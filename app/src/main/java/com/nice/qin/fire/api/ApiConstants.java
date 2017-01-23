package com.nice.qin.fire.api;

public class ApiConstants {

    public static final String NETEAST_HOST = "http://c.m.163.com/";
    /**
     * 妹子图片
     * http://gank.io/api/data/福利/{size}/{page}
     */
    public static final String GANK_PHOTO = "http://gank.io/api/";

    /***
     * 知乎
     */
    public static final String ZHIHU = "http://news-at.zhihu.com/";

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.GANK_GIRL_PHOTO:
                host = GANK_PHOTO;
                break;
            case HostType.ZHIHU:
                host = ZHIHU;
                break;
            case HostType.NETEASE_NEWS_VIDEO:
                host = NETEAST_HOST;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
