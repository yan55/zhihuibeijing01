package com.example.administrator.zhihuibeijing.Utls;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/8/4/004
 * 对sharedprefernesc的封装
 */

public class PrefUtils {
    public static boolean getBoolean(Context context, String key, boolean defVale) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defVale);


    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
         sp.edit().putBoolean(key,value).apply();
    }
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }

    public static String getString(Context context, String key, String defVale) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
         return sp.getString(key,defVale);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).apply();
    }

    public static int getInt(Context context, String key, int defVale) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getInt(key,defVale);
    }


}
