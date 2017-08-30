package com.example.administrator.zhihuibeijing.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.R;

/**
 * Created by Administrator on 2017/8/30/030
 * 五个标签页的基类
 */

public class BasePager {
    public Activity mActivity;

    public TextView tv_text01;

    public FrameLayout fl_content;

    public ImageButton btn_menu;
    public View mRootView;// 当前页面的布局对象

    public BasePager(Activity mActivity) {

        this.mActivity = mActivity;
        mRootView = iniView();
    }


    public View iniView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tv_text01 = (TextView) view.findViewById(R.id.tv_text01);
        btn_menu = (ImageButton) view.findViewById(R.id.btn_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;

    }

    public void initDate() {


    }
}
