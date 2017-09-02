package com.example.administrator.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.MainActivity;
import com.example.administrator.zhihuibeijing.R;
import com.example.administrator.zhihuibeijing.base.BeseMenuDetaiPager;
import com.example.administrator.zhihuibeijing.domain.textclass;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/8/31/031
 */

public class NewsMenuDetailPager extends BeseMenuDetaiPager {

    private ArrayList<textclass.DataBean.ChildrenBean> mTabData;//页签网路数据
    private ArrayList<TabDetailPager> mpagers;//页面标签页集合
    private ViewPager vp_news_menu_detail;

    private TabPageIndicator mIndicator;
    private ImageButton btn_next;

    public NewsMenuDetailPager(Activity activity, ArrayList<textclass.DataBean.ChildrenBean> children) {
        super(activity);
        mTabData = children;
    }


    @Override
    public View initView() {

        View view = View.inflate(mactivity, R.layout.pager_news_men_detail, null);
        vp_news_menu_detail = (ViewPager) view.findViewById(R.id.vp_news_menu_detail);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        btn_next = (ImageButton) view.findViewById(R.id.btn_next);

        //ViewUtils.inject(this, view);

        return view;
    }

    @Override
    /*
    * 初始化数据
    * */
    public void initData() {
        //初始化叶签
        mpagers = new ArrayList<>();
        for (int i = 0; i < mTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mactivity, mTabData.get(i));
            mpagers.add(pager);

        }
        vp_news_menu_detail.setAdapter(new NewsMenuDetailAdaper());
        //
        mIndicator.setViewPager(vp_news_menu_detail);


        vp_news_menu_detail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("当前位置" + position);

                if (position == 0) {
                    // 开启侧边栏
                    setSlidingMenuEnable(true);
                } else {
                    // 禁用侧边栏
                    setSlidingMenuEnable(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vp_news_menu_detail.getCurrentItem();
                currentItem++;
                vp_news_menu_detail.setCurrentItem(currentItem);
            }
        });
    }

    class NewsMenuDetailAdaper extends PagerAdapter {

        //指示器的标题
        @Override
        public CharSequence getPageTitle(int position) {

            textclass.DataBean.ChildrenBean data = mTabData.get(position);

            return data.getTitle();
        }

        @Override
        public int getCount() {
            return mpagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mpagers.get(position);

            View view = pager.view;
            pager.initData();

//            TextView textView = new TextView(mactivity);
//            textView.setText("31231231");

                container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 开启或禁用侧边栏
     *
     * @param enable
     */
    protected void setSlidingMenuEnable(boolean enable) {
        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mactivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }


}
