package com.maxin.p2p.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxin.p2p.utils.UIUtils;
import com.maxin.p2p.view.LoadingPager;

import butterknife.ButterKnife;

import static com.maxin.p2p.common.MyApplication.context;

/**
 * Created by shkstart on 2017/6/21.
 */

public abstract class BaseFragment extends Fragment {
    private LoadingPager loadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() == 0){
            TextView view = new TextView(getActivity());
            view.setText("空白空白");
            return view;
        }
        loadingPager=new LoadingPager(context) {
            @Override
            public View getLayoutId() {
                View view = View.inflate(getActivity(),
                        BaseFragment.this.getLayoutId(),null);
                ButterKnife.inject(BaseFragment.this,view);
                return view;
            }

            @Override
            protected void setResult(View successView, String json) {
                setContent(json);
                //保证在主线程中执行此方法
                UIUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initTitle();
                        initView();
                        initListener();
                        initData();
                    }
                });
            }

            @Override
            protected String getUrl() {
                return getChildUrl();
            }
        };

        return loadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getLayoutId() != 0){
            //连网
            loadingPager.loadNet();
        }
        if (TextUtils.isEmpty(getChildUrl())){
            initTitle();
            initView();
            initListener();
            initData();
        }

    }

    public abstract void setContent(String url);

    public abstract String getChildUrl();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public void initListener() {

    }

    public void initView() {

    }

    public abstract void initData();

    public abstract void initTitle();

    public abstract int getLayoutId();
}
