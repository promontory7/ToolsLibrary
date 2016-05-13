package com.zhuchudong.library.okHttpUtils;

import android.os.Handler;
import android.os.Looper;

import com.zhuchudong.library.okHttpUtils.builder.GetBuilder;
import com.zhuchudong.library.okHttpUtils.builder.PostFileBuilder;
import com.zhuchudong.library.okHttpUtils.builder.PostFormBuilder;
import com.zhuchudong.library.okHttpUtils.builder.PostStringBuilder;
import com.zhuchudong.library.okHttpUtils.callback.BaseCallBack;
import com.zhuchudong.library.okHttpUtils.request.RequestCall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/12.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 5000;

    Handler mDelivery;
    private static OkHttpUtils mInstances;
    private OkHttpClient mOkhttpClient;

    public OkHttpUtils() {
        if (mOkhttpClient == null) {
            mOkhttpClient = new OkHttpClient();
        }
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public static OkHttpUtils getInstance() {
        if (mInstances == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstances == null) {
                    mInstances = new OkHttpUtils();
                }
            }
        }
        return mInstances;
    }

    public OkHttpClient getOkhttpClient() {
        return mOkhttpClient;
    }


    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }


    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostStringBuilder PostString() {
        return new PostStringBuilder();
    }

    ;

    public void cancelTag(Object tag) {
        for (Call call : mOkhttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkhttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void enqueue(RequestCall requestCall, final BaseCallBack callback) {
        requestCall.getCall().enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (callback != null) {
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(call, e);
                            callback.onAfter();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                try {
                    final Object object = callback.parseNetworkResponse(response);

                    if (callback != null) {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(object);
                                callback.onAfter();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
