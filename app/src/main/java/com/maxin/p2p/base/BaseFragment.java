package com.maxin.p2p.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by shkstart on 2017/6/21.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getLayoutId()==0) {
            TextView view=new TextView(getActivity());
            view.setText("布局未设置");
            return view;
        }
        View view=View.inflate(getActivity(),getLayoutId(),null);

        ButterKnife.inject(this,view);
        initTitle();
        initView();
        initListener();
        initData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void initListener() {

    }

    private void initView() {

    }

    protected abstract void initData();

    protected abstract void initTitle();

    public abstract int getLayoutId();
}
