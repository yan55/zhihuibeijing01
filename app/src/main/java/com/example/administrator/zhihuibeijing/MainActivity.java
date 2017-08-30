package com.example.administrator.zhihuibeijing;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.administrator.zhihuibeijing.fragment.ContentFragment;
import com.example.administrator.zhihuibeijing.fragment.LeftMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by Administrator on 2017/8/4/004
 * <p>
 * 主页面
 */

public class MainActivity extends SlidingFragmentActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //滑动侧边栏
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(250);
        initfragment();
    }

    /*
    * 初始化fragment
    * */
    public void initfragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_left_menu, new LeftMenu());
        transaction.replace(R.id.fl_main, new ContentFragment());
        transaction.commit();
    }
}
