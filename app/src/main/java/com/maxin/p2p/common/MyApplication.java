package com.maxin.p2p.common;

import android.app.Application;

/**
 * Created by shkstart on 2017/6/20.
 */

public class MyApplication extends Application {

    public static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
