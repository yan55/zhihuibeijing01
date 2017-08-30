package com.example.administrator.zhihuibeijing.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.base.BasePager;

/**
 * Created by Administrator on 2017/8/30/030
 */

public class SettingPager extends BasePager {

    public SettingPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    /*
    * 初始化数据
    * */
    public void initDate() {
        TextView textView = new TextView(mActivity);
        textView.setText("设置");
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);

        tv_text01.setText("设置");
        btn_menu.setVisibility(View.INVISIBLE);
    }
}
