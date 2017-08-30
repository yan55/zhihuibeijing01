package com.example.administrator.zhihuibeijing;


import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.example.administrator.zhihuibeijing.Utls.PrefUtils;


public class SplashActivity extends Activity {

    protected ConstraintLayout rl_root;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_root = (ConstraintLayout) findViewById(R.id.rl_root);

        //动画的旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);//设置时间
        rotateAnimation.setFillAfter(true);//播放结束的状态
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        //动画集合
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        //启动动画
        rl_root.startAnimation(animationSet);


        //设置一个动画的监听，当动画结束时跳转到其它页面
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画
                //动画结束
                //如果第一次进入的话的 跳转到引导界面
                //否则就跳转的主页面
               /*
                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);

                boolean is_first_enter = sp.getBoolean("is_first_enter", true);
                */
                boolean is_first_enter = PrefUtils.getBoolean(SplashActivity.this,
                        "is_first_enter", true);
                Intent intent;
                if (is_first_enter) {
                    //新手页面
                    intent = new Intent(getApplicationContext(), GuideActivity.class);
                } else {
                    //主页面
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

}
