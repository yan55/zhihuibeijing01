package com.example.administrator.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;

/**
 * Created by Administrator on 2017/8/31/031
 */

public class NewsMenuDetailPager extends BeseMenuDetaiPager {
    public NewsMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(mactivity);
        textView.setText("菜单详情页——专题");
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }


}
