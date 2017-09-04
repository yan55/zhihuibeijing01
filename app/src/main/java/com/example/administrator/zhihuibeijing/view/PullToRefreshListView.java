package com.example.administrator.zhihuibeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.zhihuibeijing.R;

/**
 * Created by Administrator on 2017/9/4/004
 * 下拉刷新的Listview
 */

public class PullToRefreshListView extends ListView {

    private View mHeaderView;
    private int startY=-1;
    private int endy;
    private int mHeaderViewHeight;

    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /*
    * 初始化头布局
    * */
    public void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh, null);
        this.addHeaderView(mHeaderView);
        //隐藏头布局
        mHeaderView.measure(0, 0);

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();

        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY = (int) ev.getY();
                }

                endy = (int) ev.getY();

                int dy = endy - startY;

                int position = getFirstVisiblePosition();

                if (dy>0&&position==0){
                    int i = dy -mHeaderViewHeight;
                    mHeaderView.setPadding(0,i,0,0);

                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:


                break;

            default:
                break;

        }
        return super.onTouchEvent(ev);
    }
}


