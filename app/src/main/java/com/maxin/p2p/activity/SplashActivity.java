package com.maxin.p2p.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxin.p2p.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends AppCompatActivity {

    @InjectView(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @InjectView(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;
    @InjectView(R.id.activity_splash)
    RelativeLayout activitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        initView();
        initData();
        initListener();
    }

    private void initListener() {

    }
    private boolean isLoading=true;
    private void initData() {
        AlphaAnimation al = new AlphaAnimation(0, 1);
        al.setDuration(2000);
        al.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isLoading) {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,LoadingActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivWelcomeIcon.startAnimation(al);
    }

    private void initView() {
        tvWelcomeVersion.setText(getNum());
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

}
