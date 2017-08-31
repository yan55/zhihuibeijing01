package com.example.administrator.zhihuibeijing.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.MainActivity;
import com.example.administrator.zhihuibeijing.R;
import com.example.administrator.zhihuibeijing.domain.textclass;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30/030
 * 侧边栏的页面
 */

public class LeftMenu extends BaseFragment {
    private int mcurrentPos;

    private ListView lv_list;
    private List<textclass.DataBean> mNewsMenuData;
    private LeftMenu.leftmenuAdaper leftmenuAdaper;

    @Override
    public View initview() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);

        lv_list = (ListView) view.findViewById(R.id.lv_list);

        return view;
    }

    @Override
    public void initData() {


    }


    public void setmenudata(List<textclass.DataBean> date) {
        //更新页面

        leftmenuAdaper = new leftmenuAdaper();
        mNewsMenuData = date;

        lv_list.setAdapter(leftmenuAdaper);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mcurrentPos = position;
//                leftmenuAdaper.notifyDataSetChanged();
                lv_list.setAdapter(leftmenuAdaper);// 很强啥的

                //收起侧边栏
                toggle();
            }
        });


    }

    /*
    * 打开和关闭侧边栏
    * */
    private void toggle() {
        //收起和打开侧边栏的方法
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();

    }


    class leftmenuAdaper extends BaseAdapter {


        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(mActivity, R.layout.list_left_menu, null);
                TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);
                textclass.DataBean data = mNewsMenuData.get(position);
                tv_menu.setText(data.getTitle());
                //设置颜色是否为选中状态
                if (position == mcurrentPos) {
                    tv_menu.setEnabled(true);
                } else {
                    tv_menu.setEnabled(false);
                }
            }
            return view;
        }
    }
}
