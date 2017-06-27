package com.maxin.p2p.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseActivity;

import butterknife.InjectView;

public class SplashActivity extends BaseActivity {

    @InjectView(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @InjectView(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;
    @InjectView(R.id.activity_splash)
    RelativeLayout activitySplash;

    public void initListener() {

    }

    public void initData() {
        AlphaAnimation al = new AlphaAnimation(0, 1);
        al.setDuration(2000);
        al.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isLogin()) {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,LoaginActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivWelcomeIcon.startAnimation(al);
    }

    public void initView() {
        tvWelcomeVersion.setText(getNum());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    private String getNum() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String i=packageInfo.versionName;
            return i;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }
    private boolean isLogin() {
        String name = getUser().getName();
        if (name.equals("admin")){
            return false;
        }else{
            return true;
        }
    }
}
