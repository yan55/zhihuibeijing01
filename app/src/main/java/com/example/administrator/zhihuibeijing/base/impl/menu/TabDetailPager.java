package com.example.administrator.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;
import com.example.administrator.zhihuibeijing.domain.textclass;


/**
 * 页签详情页
 * Created by Administrator on 2017/9/1/001
 */

public class TabDetailPager extends BeseMenuDetaiPager {
    private textclass.DataBean.ChildrenBean mTaData;
    private TextView textView;
    private Context mContext;

    public TabDetailPager(Activity activity, textclass.DataBean.ChildrenBean childrenBean) {

        super(activity);
        mTaData = childrenBean;
        this.mContext = activity.getApplicationContext();
    }

    @Override
    public View initView() {

        textView = new TextView(mactivity);
      // textView.setText(mTaData.getTitle());//会报空指针异常
        textView.setText("haha");
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    @Override
    public void initData() {
        textView.setText(mTaData.getTitle());
    }
}
