package com.maxin.p2p.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.maxin.p2p.common.MyApplication;

/**
 * Created by shkstart on 2017/6/23.
 */

public class UIUtils {
    /*
  * 加载布局
  * */
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }


    /*
    * 返回一个上下文
    * */
    private static Context getContext() {

        return MyApplication.context;
    }


    /*
    * 格式化字符串 - 占位字符
    * */
    public static String stringFormat(int id,String value){
        String versionName = String.format(getString(id), value);
        return versionName;
    }

    /*
    * 从string文件获取字符串
    * */
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    //与屏幕分辨率相关的
    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);

    }

    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
    //保证runnable中的操作在主线程中执行
    public static void runOnUiThread(Runnable runnable) {
        if(isInMainThread()){
            runnable.run();
        }else{
            UIUtils.getHandler().post(runnable);
        }
    }
    //判断当前线程是否是主线程
    private static boolean isInMainThread() {
        int currentThreadId = android.os.Process.myTid();
        return MyApplication.mainThreadId == currentThreadId;

    }
    public static Handler getHandler(){
        return MyApplication.handler;
    }
}
