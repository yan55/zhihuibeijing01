package com.example.administrator.zhihuibeijing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.zhihuibeijing.Utls.PrefUtils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/8/4/004
 * 新手引导页面
 */

public class GuideActivity extends Activity {

    protected ViewPager vp_guide;
    //引导页面图片设置
    private int[] mImageIds = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};
    protected ArrayList<ImageView> mImageViewList;
    private LinearLayout ll_container;
    private Button bt_start;
    //小红点移动的距离
    private int mpointDis;
    private ImageView id_red_point;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.guide_activity);

        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        //得到小圆点
        ll_container = (LinearLayout) findViewById(R.id.ll_container);

        id_red_point = (ImageView) findViewById(R.id.id_red_point);

        bt_start = (Button) findViewById(R.id.bt_start);

        initDate();
        vp_guide.setAdapter(new GuideAdapter());

        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //更新小红点参数
                int leftmargin = (int) (mpointDis * positionOffset) + mpointDis * position;//计算小红点的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) id_red_point.getLayoutParams();
                params.leftMargin = leftmargin;
                id_red_point.setLayoutParams(params);


            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    bt_start.setVisibility(View.VISIBLE);

                } else {

                    bt_start.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //更新小红点的距离


            }
        });
        // 计算两个圆点的距离
        // 移动距离=第二个圆点left值 - 第一个圆点left值
        // measure->layout(确定位置)->draw(activity的onCreate方法执行结束之后才会走此流程)
        //mpointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();

        id_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除监听，避免重复回调
                id_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mpointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();
                Log.d("hha", "onGlobalLayout: " + mpointDis);
            }
        });

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setBoolean(getApplicationContext(),"is_first_enter", false);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }


    //初始化数据
    public void initDate() {
        mImageViewList = new ArrayList<>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);
            ll_container.addView(point);//给界面添加小圆点

        }

    }

    class GuideAdapter extends PagerAdapter {

        @Override
        //item的个数
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //初始化item的布局
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        //销毁布局
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}

