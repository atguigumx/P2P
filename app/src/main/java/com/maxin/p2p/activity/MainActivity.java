package com.maxin.p2p.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maxin.p2p.R;
import com.maxin.p2p.fragment.HomeFragment;
import com.maxin.p2p.fragment.InvestFragment;
import com.maxin.p2p.fragment.MoreFragment;
import com.maxin.p2p.fragment.ProperyFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.main_rg)
    RadioGroup mainRg;
    private ArrayList<Fragment> fragments;
    private Fragment tempFragment;
    private int position;
    private boolean isExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();
        initListener();
    }

    private void initListener() {
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_main :
                        position=0;
                        break;
                    case R.id.rb_invest :
                        position=1;
                        break;
                    case R.id.rb_propert :
                        position=2;
                        break;
                    case R.id.rb_more :
                        position=3;
                        break;
                }
                Fragment fragment = fragments.get(position);
                switchFragment(fragment);
            }
        });
        mainRg.check(R.id.rb_main);
    }

    private void switchFragment(Fragment currentFragment) {
        if (currentFragment != tempFragment) {

            if (currentFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                //如果没有添加就添加
                if (!currentFragment.isAdded()) {
                    //隐藏之前的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }

                    //添加Fragment
                    ft.add(R.id.fl_mian, currentFragment);

                }
                //如果添加了就隐藏
                else {
                    //隐藏上次显示的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }

                    //显示
                    ft.show(currentFragment);
                }

                //最后统一提交
                ft.commit();
                //重新赋值
                tempFragment = currentFragment;
            }

        }
    }
    private void initFragment() {
        fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new InvestFragment());
        fragments.add(new ProperyFragment());
        fragments.add(new MoreFragment());

        switchFragment(fragments.get(position));
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isExit) {
                finish();
                return true;
            }
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            isExit = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    isExit = false;
                }
            }, 2000);

            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
