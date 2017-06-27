package com.maxin.p2p.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseFragment;
import com.maxin.p2p.fragment.investFragment.InvestAllFragment;
import com.maxin.p2p.fragment.investFragment.InvestHotFragment;
import com.maxin.p2p.fragment.investFragment.InvestRecomFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by shkstart on 2017/6/20.
 */

public class InvestFragment extends BaseFragment {


    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @InjectView(R.id.tabpage_invest)
    TabPageIndicator tabpageInvest;
    @InjectView(R.id.vp_invest)
    ViewPager vpInvest;
    private List<BaseFragment> list;


    @Override
    public void setContent(String url) {

    }

    @Override
    public String getChildUrl() {
        return null;
    }

    @Override
    public void initData() {
        intiFragment();
        vpInvest.setAdapter(new MyAdapter(getFragmentManager()));
        tabpageInvest.setViewPager(vpInvest);
    }

    private void intiFragment() {
        list=new ArrayList<>();
        list.add(new InvestAllFragment());
        list.add(new InvestRecomFragment());
        list.add(new InvestHotFragment());
    }

    @Override
    public void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "全部理财";
            }else if(position == 1){
                return "推荐理财";
           }else if(position == 2){
                return "热门理财";
            }
            return super.getPageTitle(position);
        }
    }
}
