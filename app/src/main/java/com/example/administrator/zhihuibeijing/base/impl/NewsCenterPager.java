package com.example.administrator.zhihuibeijing.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.base.BasePager;
import com.example.administrator.zhihuibeijing.global.GlobalConstans;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2017/8/30/030
 */

public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity mActivity) {
        super(mActivity);
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

        //获取服务器，请求数据
        getDataFromServer();
    }


    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils(1000*5);

        utils.configSoTimeout(1000*5);
        utils.configRequestRetryCount(4);
        utils.configHttpCacheSize(0);

        utils.send(HttpRequest.HttpMethod.GET, GlobalConstans.CATEGORY_URL , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.d("连接成功，代码200", "onSuccess: 连接成功！"+result);


            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Log.d("连接失败！，代码404", "onSuccess: 连接失败！");
            }
        });

    }
}
