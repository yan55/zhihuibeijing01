package com.example.administrator.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhihuibeijing.R;
import com.example.administrator.zhihuibeijing.Utls.CacheUtils;
import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;
import com.example.administrator.zhihuibeijing.domain.NewsBean;
import com.example.administrator.zhihuibeijing.domain.NewsTaBean;
import com.example.administrator.zhihuibeijing.domain.TopnewsBean;
import com.example.administrator.zhihuibeijing.domain.textclass;
import com.example.administrator.zhihuibeijing.global.GlobalConstans;
import com.example.administrator.zhihuibeijing.view.PullToRefreshListView;
import com.example.administrator.zhihuibeijing.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 页签详情页
 * Created by Administrator on 2017/9/1/001
 */

public class TabDetailPager extends BeseMenuDetaiPager {
    private TextView mtv_taile;
    private textclass.DataBean.ChildrenBean mTaData;
    private Context mContext;
    public View view;
    private TopNewsViewPager mViewPager;
    private final String mUrl;
    private List<TopnewsBean> mTopnews;
    private CirclePageIndicator mindicator;
    private PullToRefreshListView  lvlist;
    private List<NewsBean> mNewsList;
    private NewsAdaper mNewsAdaper;
    private View mHeaderView;


    public TabDetailPager(Activity activity, textclass.DataBean.ChildrenBean childrenBean) {

        super(activity);
        mTaData = childrenBean;

        this.mContext = activity.getApplicationContext();

        mUrl = GlobalConstans.SERVER_URL + mTaData.getUrl();
    }

    @Override
    public View initView() {


        view = View.inflate(mactivity, R.layout.pager_tab_detail, null);

        lvlist = (PullToRefreshListView ) view.findViewById(R.id.lv_list);


        mHeaderView = View.inflate(mactivity, R.layout.list_item_header, null);

        mViewPager = (TopNewsViewPager) mHeaderView.findViewById(R.id.vp_top_news);

        mtv_taile = (TextView) mHeaderView.findViewById(R.id.tv_taile);

        mindicator = (CirclePageIndicator) mHeaderView.findViewById(R.id.indicator);

        lvlist.addHeaderView(mHeaderView);

        return view;
    }

    @Override
    public void initData() {

        String getcache = CacheUtils.getcache(mactivity, mUrl);
        if (!TextUtils.isEmpty(getcache)) {
            procesData(getcache);
        }
        //连接网路
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

                CacheUtils.setcache(mUrl, result, mactivity);
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
        NewsTaBean newsTaBean = gson.fromJson(result, NewsTaBean.class);

        //获取头条新闻数据
        mTopnews = newsTaBean.getData().getTopnews();

        Log.d("标注", "procesData: 头条新闻的数据：" + mTopnews);

        if (mTopnews != null) {
            mViewPager.setAdapter(new TopNewsAdaper());

            mindicator.setViewPager(mViewPager);
            mindicator.setSnap(true);//快照方式展示

            //设置
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    TopnewsBean topNews = mTopnews.get(position);
                    //更新头条新闻的标题
                    mtv_taile.setText(topNews.getTitle());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //更新第一个头条新闻
            mtv_taile.setText(mTopnews.get(0).getTitle());
            //默认小圆点是从第一个开始的
            mindicator.onPageSelected(0);

        }
        //获取列表新闻
        mNewsList = newsTaBean.getData().getNews();
        if (mNewsList != null) {

            mNewsAdaper = new NewsAdaper();

            lvlist.setAdapter(mNewsAdaper);

        }


    }


    //头条新闻适配器
    class TopNewsAdaper extends PagerAdapter {
        private final BitmapUtils mbitmapUtils;

        public TopNewsAdaper() {

            mbitmapUtils = new BitmapUtils(mactivity);
            //没有加载时默认一张图片
            mbitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);

        }

        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView view = new ImageView(mactivity);

            view.setImageResource(R.drawable.topnews_item_default);

            view.setScaleType(ImageView.ScaleType.FIT_XY);

            //下载图片，将图片设置给ImageView-避免内存溢出-缓存
            //BitMapxutils
            String imageUrl = mTopnews.get(position).getTopimage();//图片下载链接

            ArrayList<String> arrlist = new ArrayList<String>();
            // String imageUrl = "http://192.168.112.1:8080/zhbj/10007/1452327318UU91.jpg";//图片下载链接

            mbitmapUtils.display(view, imageUrl);
//            TextView textView = new TextView(mactivity);
//            textView.setText("321321312");
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /*
    * listView的适配器
    * */
    class NewsAdaper extends BaseAdapter {
        private final BitmapUtils bitmapUtils;

        public NewsAdaper() {

            bitmapUtils = new BitmapUtils(mactivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }


        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public NewsBean getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;

            if (convertView != null) {
                view = convertView;

                holder = (ViewHolder) view.getTag();


            } else {
                view = View.inflate(mactivity, R.layout.list_item_news, null);
                holder = new ViewHolder();

                holder.iv_con = (ImageView) view.findViewById(R.id.iv_con);

                holder.tv_title = (TextView) view.findViewById(R.id.tv_title);

                holder.tv_data = (TextView) view.findViewById(R.id.tv_data);

                view.setTag(holder);

            }
            //设置数据
            NewsBean news = getItem(position);

            holder.tv_title.setText(news.getTitle());

            holder.tv_data.setText(news.getPubdate());
            bitmapUtils.display(holder.iv_con, news.getListimage());

            return view;
        }
    }

    static class ViewHolder {

        private ImageView iv_con;
        private TextView tv_title;
        private TextView tv_data;

    }
}
