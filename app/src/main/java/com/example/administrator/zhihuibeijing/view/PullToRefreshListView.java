package com.example.administrator.zhihuibeijing.view;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.zhihuibeijing.R;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/4/004
 * 下拉刷新的Listview
 */

public class PullToRefreshListView extends ListView {
    private static final int STATE_PULL_TO_REFRESH = 1;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH = 2;//正在刷新
    private static final int STATE_REFRESH = 3;//释放刷新

    private int mCurrentState = STATE_PULL_TO_REFRESH;//当前的状态

    private View mHeaderView;
    private int startY = -1;
    private int endy;
    private int mHeaderViewHeight;
    private TextView pull_tv_title01;
    private TextView pull_tv_time;
    private ImageView iv_arrow;
    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private ProgressBar pb_loading;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();

    }

    /*
    * 初始化头布局
    * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh, null);


        pull_tv_title01 = (TextView) mHeaderView.findViewById(R.id.pull_tv_title01);

        pull_tv_time = (TextView) mHeaderView.findViewById(R.id.pull_tv_time);

        iv_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);

        pb_loading = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);

        this.addHeaderView(mHeaderView);
        //隐藏头布局
        mHeaderView.measure(0, 0);

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();

        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
        initAnimaton();
        setCrrentTime();

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getY();
                }

                if (mCurrentState == STATE_REFRESH) {
                    //如果正在刷新，跳出循环

                    break;
                }

                endy = (int) ev.getY();

                int dy = endy - startY;

                int position = getFirstVisiblePosition();
                //  必须下拉显示当前的第一个下拉

                if (dy > 0 && position == 0) {
                    int padding = dy - mHeaderViewHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentState != STATE_RELEASE_TO_REFRESH) {
                        //当前刷新的状态
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (padding < 0 && mCurrentState != STATE_PULL_TO_REFRESH) {
                        //下拉刷新
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }


                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    mCurrentState = STATE_REFRESH;
                    refreshState();
                    //完整展示头布局
                    mHeaderView.setPadding(0, 0, 0, 0);

                    //4.进行回调
                    if (mlistener != null) {

                        mlistener.onRefresh();
                    }

                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {

                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                }

                break;

            default:
                break;

        }
        return super.onTouchEvent(ev);
    }

    /*
    *
    * 初始化旋转动画*/
    public void initAnimaton() {
        //向上的状态
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(500);
        animUp.setFillAfter(true);//保存当前的状态

        //向下的状态
        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(500);
        animDown.setFillAfter(true);//保存当前的状态
    }

    /*
    *
    * 根据当前页面来刷新状态
    * */
    private void refreshState() {
        switch (mCurrentState) {

            case STATE_PULL_TO_REFRESH:

                pull_tv_title01.setText("下拉刷新！");

                pb_loading.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);

                iv_arrow.setAnimation(animDown);

                break;

            case STATE_RELEASE_TO_REFRESH:
                pull_tv_title01.setText("松开刷新！");
                pb_loading.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);
                iv_arrow.setAnimation(animUp);
                break;

            case STATE_REFRESH:
                pull_tv_title01.setText("正在刷新......");
                iv_arrow.clearAnimation();//清除动画，否则无法隐藏
                pb_loading.setVisibility(View.VISIBLE);
                iv_arrow.setVisibility(View.INVISIBLE);

                break;

            default:

                break;
        }

    }


//设置刷新时间
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setCrrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        pull_tv_time.setText(time);
    }

    /*
    * 刷新结束，收起控件
    * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onRefrechComplete(boolean success) {

            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            pull_tv_title01.setText("下拉刷新");
            pb_loading.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);

        if (success){
            setCrrentTime();
        }


    }
    //接口的回调

    /*
    * 3.定义成员变量，接收监听对象
    * */

    private OnRefreshListener mlistener;

    /*
    * 2.暴露接口，更新测试
    * */
    public void setOnRefreshListener(OnRefreshListener listener) {

        mlistener = listener;
    }

    /*
    * 回调的接口
    * 1.下拉刷新的回调接口
    * */
    public interface OnRefreshListener {

        public void onRefresh();

    }

}


