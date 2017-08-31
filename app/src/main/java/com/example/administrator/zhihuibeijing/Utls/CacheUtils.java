package com.example.administrator.zhihuibeijing.Utls;

import android.content.Context;

/**
 * Created by Administrator on 2017/8/31/031
 * 数据缓存的一个工具类
 */

public class CacheUtils {
    /*
    * 以url为key，json为vlaue保存在本地
    * */
    public static  void setcache(String url, String json, Context context) {
        PrefUtils.setString(context, url, json);
    }

    public static String getcache(Context context, String url) {

        String cache = PrefUtils.getString(context, url, null);

        return cache;
    }

}
