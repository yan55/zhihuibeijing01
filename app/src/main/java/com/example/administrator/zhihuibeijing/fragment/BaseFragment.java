package com.example.administrator.zhihuibeijing.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/30/030
 * baseFragment的基类
 */

public abstract class BaseFragment extends Fragment {

    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();//获取当前的所依赖的activity

    }

    @Nullable
    @Override
    /*
    * 初始化fragment的布局
    * */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = initview();
        return view;

    }

    @Override
    /*
    * fragment所依赖的activity的onCreate方法执行结束
    * */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据

        initData();

    }

    //初始化布局
    public abstract View initview();

    //初始化数据
    public abstract void initData();

}
