package com.example.administrator.zhihuibeijing.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.MainActivity;
import com.example.administrator.zhihuibeijing.Utls.CacheUtils;
import com.example.administrator.zhihuibeijing.base.BasePager;
import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;
import com.example.administrator.zhihuibeijing.base.impl.menu.InteraMenuDetailPager;
import com.example.administrator.zhihuibeijing.base.impl.menu.NewsMenuDetailPager;
import com.example.administrator.zhihuibeijing.base.impl.menu.PhotosMenuDetailPager;
import com.example.administrator.zhihuibeijing.base.impl.menu.TopicMenuDetailPager;
import com.example.administrator.zhihuibeijing.domain.Data;
import com.example.administrator.zhihuibeijing.domain.textclass;
import com.example.administrator.zhihuibeijing.fragment.LeftMenu;
import com.example.administrator.zhihuibeijing.global.GlobalConstans;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/30/030
 */

public class NewsCenterPager extends BasePager {
    private ArrayList<BeseMenuDetaiPager> menuDetaiPagers;
    private textclass data;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    /*
    * 初始化数据
    * */
    public void initDate() {

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
        data = gson.fromJson(json, textclass.class);
        System.out.println("解析结果:" + data);
        //获取侧边栏对象
        MainActivity manUI = (MainActivity) mActivity;

        LeftMenu leftMenu = manUI.getleftmenuFragment();
        leftMenu.setmenudata(data.getData());

        //初始化四个菜单
        menuDetaiPagers = new ArrayList<>();

        menuDetaiPagers.add(new NewsMenuDetailPager(mActivity,data.getData().get(0).getChildren()));
        menuDetaiPagers.add(new TopicMenuDetailPager(mActivity));
        menuDetaiPagers.add(new PhotosMenuDetailPager(mActivity));
        menuDetaiPagers.add(new InteraMenuDetailPager(mActivity));
        setcurrentDetailPager(0);

    }

    //设置新闻菜单详情页
    public void setcurrentDetailPager(int position) {
        BeseMenuDetaiPager pager = menuDetaiPagers.get(position);
        View view = pager.mrootview;//当前页面布局

        //清除之前旧的布局
        fl_content.removeAllViews();
        //给贞布局添加页面布局
        fl_content.addView(view);


        //初始化页面数据
        pager.initData();
        //更新标题
        tv_text01.setText(data.getData().get(position).getTitle());
    }
}
