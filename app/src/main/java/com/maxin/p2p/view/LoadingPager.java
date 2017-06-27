package com.maxin.p2p.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.maxin.p2p.R;
import com.maxin.p2p.utils.HttpUtils;
import com.maxin.p2p.utils.UIUtils;

/**
 * Created by shkstart on 2017/6/23.
 */

public abstract class LoadingPager extends FrameLayout {
    private View loadingView;
    private View errorView;
    private View successView;
    private static final int STATE_LOADING = 0;
    private static final int STATE_ERROR = 1;
    private static final int STATE_SUCCESS = 2;
    private int state_current = STATE_LOADING;//默认情况下，当前状态为正在加载


    public LoadingPager(Context context) {
        super(context);
        init();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        loadingView= UIUtils.inflate(R.layout.page_loading);
        addView(loadingView);
        errorView=UIUtils.inflate(R.layout.page_error);
        addView(errorView);

        showSafePager();
    }

    private void showSafePager() {
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // state_current = STATE_SUCCESS;
                showPage();
            }
        });
    }

    private void showPage() {
        loadingView.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        errorView.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);

        if(successView==null) {
            successView=getLayoutId();
            addView(successView);
        }
        successView.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
    }

    public abstract View getLayoutId();

    public void loadNet(){
        final String url=getUrl();
        if (TextUtils.isEmpty(url)) {
            state_current = STATE_SUCCESS;
            showSafePager();
            return;
        }
        HttpUtils.getInstance().get(url, new HttpUtils.OnHttpClientListener() {
            @Override
            public void onSuccess(String json) {
                state_current=STATE_SUCCESS;
                setResult(successView,json);
                showSafePager();
            }

            @Override
            public void onFailure(Exception e) {
                state_current=STATE_ERROR;
                showSafePager();
            }
        });
    }

    protected abstract void setResult(View successView, String url);

    protected abstract String getUrl();


}
