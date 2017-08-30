package com.example.administrator.zhihuibeijing.fragment;

import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.administrator.zhihuibeijing.MainActivity;
import com.example.administrator.zhihuibeijing.R;
import com.example.administrator.zhihuibeijing.base.BasePager;
import com.example.administrator.zhihuibeijing.base.impl.GovAffairsPager;
import com.example.administrator.zhihuibeijing.base.impl.HomePager;
import com.example.administrator.zhihuibeijing.base.impl.NewsCenterPager;
import com.example.administrator.zhihuibeijing.base.impl.SettingPager;
import com.example.administrator.zhihuibeijing.base.impl.SmartServicePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/30/030
 * 这是主页面
 */

public class ContentFragment extends BaseFragment {

    private ViewPager vp_content;
    private ArrayList<BasePager> mpagers;
    private RadioGroup rg_group;

    @Override
    public View initview() {
        View view = View.inflate(mActivity, R.layout.content_menu, null);

        vp_content = (ViewPager) view.findViewById(R.id.vp_content);

        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);

        return view;
    }

    @Override
    public void initData() {
        mpagers = new ArrayList<>();
        mpagers.add(new HomePager(mActivity));
        mpagers.add(new NewsCenterPager(mActivity));
        mpagers.add(new SmartServicePager(mActivity));
        mpagers.add(new GovAffairsPager(mActivity));
        mpagers.add(new SettingPager(mActivity));

        vp_content.setAdapter(new ContentAdaper());


        //低栏切换监听
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vp_content.setCurrentItem(0);
                        break;
                    case R.id.rb_news:
                        vp_content.setCurrentItem(1);
                        break;
                    case R.id.rb_marts:
                        vp_content.setCurrentItem(2);
                        break;
                    case R.id.rb_gov:
                        vp_content.setCurrentItem(3);
                        break;
                    case R.id.rb_setting:
                        vp_content.setCurrentItem(4);
                        break;

                }

            }
        });


        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                BasePager pager = mpagers.get(position);
                pager.initDate();

                if (position == 0 || position == mpagers.size() - 1) {
                    setSlidingMenuEnable(false);
                } else {
                    setSlidingMenuEnable(true);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        //加载第一条数据
        mpagers.get(0).initDate();
        setSlidingMenuEnable(false);

    }

    public void setSlidingMenuEnable(boolean slidingMenuEnable) {
        MainActivity mainUI= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (slidingMenuEnable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }


    }


    class ContentAdaper extends PagerAdapter {


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
            BasePager pager = mpagers.get(position);
            //pager.initDate();//初始化数据 为了用户的的流量不能再此初始化数据
            View view = pager.mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
