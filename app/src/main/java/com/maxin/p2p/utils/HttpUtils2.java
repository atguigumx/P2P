package com.maxin.p2p.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 * Created by shkstart on 2017/6/26.
 */

public class HttpUtils2 {
    private AsyncHttpClient httpClient;

    private HttpUtils2(){
        httpClient = new AsyncHttpClient();
    }

    private static HttpUtils2 httpUtils = new HttpUtils2();

    public static HttpUtils2 getInstance(){
        return httpUtils;
    }


    private OnHttpClientListener onHttpClientListener;
    public void get(String url,  OnHttpClientListener onHttpClientListener){

        this.onHttpClientListener = onHttpClientListener;
        httpClient.get(url,handler);
    }

    public void post(String url, Map<String,String> map,
                      OnHttpClientListener onHttpClientListener){
        this.onHttpClientListener = onHttpClientListener;
        RequestParams params = new RequestParams(map);
        httpClient.post(url,params,handler);
    }

    AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, String content) {
            super.onSuccess(statusCode, content);

            if (onHttpClientListener != null){
                onHttpClientListener.onSuccess(content);
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            if (onHttpClientListener != null){
                onHttpClientListener.onFailure(content);
            }

        }
    };

    public interface OnHttpClientListener{
        void onSuccess(String json);
        void onFailure(String message);
    }

}
