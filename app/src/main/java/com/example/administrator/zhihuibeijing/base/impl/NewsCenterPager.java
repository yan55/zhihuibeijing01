package com.example.administrator.zhihuibeijing.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.MainActivity;
import com.example.administrator.zhihuibeijing.Utls.CacheUtils;
import com.example.administrator.zhihuibeijing.base.BasePager;
import com.example.administrator.zhihuibeijing.domain.News;
import com.example.administrator.zhihuibeijing.domain.NewsMenu;
import com.example.administrator.zhihuibeijing.domain.textclass;
import com.example.administrator.zhihuibeijing.fragment.LeftMenu;
import com.example.administrator.zhihuibeijing.global.GlobalConstans;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2017/8/30/030
 */

public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    /*
    * 初始化数据
    * */
    public void initDate() {
        TextView textView = new TextView(mActivity);
        textView.setText("新闻中心");
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);
        tv_text01.setText("新闻中心");

        String cache = CacheUtils.getcache(mActivity, GlobalConstans.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)) {

            Log.d("日志缓存", "initDate: 发现缓存啊！");
            processDate(cache);
        }
        //获取服务器，请求数据
        getDataFromServer();

    }


    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();

        utils.send(HttpRequest.HttpMethod.GET, GlobalConstans.CATEGORY_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.d("连接成功，代码200", "onSuccess: 连接成功！" + result);

                System.out.println(result);
                processDate(result);
                //写缓存
                CacheUtils.setcache(GlobalConstans.CATEGORY_URL, result, mActivity);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Log.d("连接失败！，代码404", "onFailure: 连接失败！");
            }
        });

    }

    /*
    *
    * 解析json数据
    * */
    protected void processDate(String json) {


        Gson gson = new Gson();
        textclass data = gson.fromJson(json, textclass.class);
        System.out.println("解析结果:" + data);
        //获取侧边栏对象
        MainActivity manUI= (MainActivity) mActivity;

        LeftMenu leftMenu = manUI.getleftmenuFragment();
        leftMenu.setmenudata(data.getData());


    }
}
