package com.example.administrator.zhihuibeijing.Utls;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/31/031.
 */

public class GsonUtils {

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }
}
