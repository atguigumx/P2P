package com.maxin.p2p.common;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by shkstart on 2017/6/22.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {


    private static CrashHandler crashHandler;
    private Context context;

    public static CrashHandler getIncetance(){
        if(crashHandler==null) {
            crashHandler=new CrashHandler();
        }
        return  crashHandler;
    }
    public void init(Context context){
        //告诉系统异常由我们来捕获
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context=context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        /*
        * 第一 提醒用户
        * 第二 收集异常
        * 第三 退出应用
        * */
        new Thread(){
            @Override
            public void run() {
                Looper.prepare(); //在这两个方法间 的代码在主线程执行
                Toast.makeText(context, "系统崩溃了", Toast.LENGTH_SHORT).show();
                Looper.loop(); //
            }
        }.start();

        collection(throwable);

        AppManager.getInstance().removeAll();
        //杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
        //参数 除了0以外都表示非正常退出
        System.exit(0);//退出虚拟机
    }

    private void collection(Throwable throwable) {
        //在这个方法里收集异常信息
    }
}
