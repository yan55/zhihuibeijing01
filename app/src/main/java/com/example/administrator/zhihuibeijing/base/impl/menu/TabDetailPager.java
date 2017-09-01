package com.example.administrator.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.content.Context;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.administrator.zhihuibeijing.R;
import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;
import com.example.administrator.zhihuibeijing.domain.NewsTaBean;


import com.example.administrator.zhihuibeijing.domain.textclass;
import com.example.administrator.zhihuibeijing.global.GlobalConstans;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * 页签详情页
 * Created by Administrator on 2017/9/1/001
 */

public class TabDetailPager extends BeseMenuDetaiPager {
    private textclass.DataBean.ChildrenBean mTaData;
    private Context mContext;
    private View view;
    private ViewPager mViewPager;
    private final String mUrl;

    public TabDetailPager(Activity activity, textclass.DataBean.ChildrenBean childrenBean) {

        super(activity);
        mTaData = childrenBean;

        this.mContext = activity.getApplicationContext();

        mUrl = GlobalConstans.SERVER_URL + mTaData.getUrl();
    }

    @Override
    public View initView() {

       /* textView = new TextView(mactivity);
      // textView.setText(mTaData.getTitle());//会报空指针异常
        textView.setText("haha");
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);*/
        view = View.inflate(mactivity, R.layout.pager_tab_detail, null);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_top_news);


        return view;
    }

    @Override
    public void initData() {
        // textView.setText(mTaData.getTitle());
        //重点，再一次获取网路数据
        getDataFromServer();

    }

    //用库获取连接网路通信
    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                System.out.println("网路连接成功！");
                String result = responseInfo.result;
                procesData(result);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Toast.makeText(mactivity, "网路连接失败！", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //解析网路数据
    private void procesData(String result) {
        Gson gson = new Gson();
        gson.fromJson(result, NewsTaBean.class);


    }


    //头条新闻适配器
    class TopNewsAdaper extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
