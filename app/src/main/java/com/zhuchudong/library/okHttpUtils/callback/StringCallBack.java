package com.zhuchudong.library.okHttpUtils.callback;

import android.util.Log;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/12.
 */
public abstract class StringCallBack extends BaseCallBack<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        String data=response.body().string();
        return data;
    }

}
