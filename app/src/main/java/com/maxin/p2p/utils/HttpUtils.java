package com.maxin.p2p.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by shkstart on 2017/6/21.
 */

public class HttpUtils {

    private static HttpUtils httpUtils = new HttpUtils();

    public static HttpUtils getInstance(){
        return httpUtils;
    }

    private OnHttpClientListener onHttpClientListener;
    public void get(String url, final OnHttpClientListener onHttpClientListener){

        this.onHttpClientListener = onHttpClientListener;
        OkHttpUtils.get().url(url).build().execute(handler);
    }


    StringCallback handler = new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {
            if (onHttpClientListener != null){
                onHttpClientListener.onFailure(e);
            }
        }

        @Override
        public void onResponse(String response, int id) {
            if (onHttpClientListener != null){
                onHttpClientListener.onSuccess(response);
            }
        }
    };

    public interface OnHttpClientListener{
        void onSuccess(String json);
        void onFailure(Exception e);
    }
}
