package com.maxin.p2p.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseFragment;

import butterknife.InjectView;

/**
 * Created by shkstart on 2017/6/20.
 */

public class MoreFragment extends BaseFragment {


    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        tvTitle.setText("更多");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

}
