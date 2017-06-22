package com.maxin.p2p.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by shkstart on 2017/6/22.
 */

public class AppManager {

    private static AppManager appManager;


    public AppManager(){}

    public static AppManager getInstance(){
        if(appManager==null) {
            appManager=new AppManager();
        }
        return appManager;
    }
    private Stack<Activity> stack =new Stack<>();

    //添加activity
    public void addActivity(Activity activity){
        if(activity!=null) {
            stack.add(activity);
        }

    }
    //删除Activity
    public void removeActivity(Activity activity){
        if(activity!=null) {
            for(int i = stack.size(); i >=0 ; i--) {
                Activity currentActivity = stack.get(i);
                if(activity==currentActivity) {
                    currentActivity.finish();//结束这个Activity
                    stack.remove(i);//移除
                }
            }
        }
    }
    //移除所有Acitivity
    public void removeAll(){
        for(int i = stack.size(); i >=0 ; i--) {
            Activity activity = stack.get(i);
            activity.finish();
            stack.remove(i);
        }
    }
}
