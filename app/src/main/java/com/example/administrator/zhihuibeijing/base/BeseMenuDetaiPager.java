package com.example.administrator.zhihuibeijing.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017/8/31/031
 * 菜单详情页基类
 */

public abstract class BeseMenuDetaiPager {
    public Activity mactivity;
    public View mrootview;

    public BeseMenuDetaiPager(Activity activity) {

        mactivity = activity;
        mrootview = initView();
    }

    public abstract View initView();


    public void initData() {

    }

}
