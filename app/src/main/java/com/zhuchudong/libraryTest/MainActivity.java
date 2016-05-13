package com.zhuchudong.librarytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zhuchudong.toollibrary.okHttpUtils.OkHttpUtils;
import com.zhuchudong.toollibrary.okHttpUtils.callback.JsonCallBack;
import com.zhuchudong.toollibrary.okHttpUtils.callback.StringCallBack;

import org.json.JSONObject;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpUtils.getInstance();
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_netTest).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_netTest:
                OkHttpUtils
                        .get()
                        .addParams("account","12")
                        .addParams("pwd","56")
                        .url(" http://www.rratchet.com/api/user/login")
                        .build()
                        .enqueue(MyJsonCallBack);
//                OkHttpUtils
//                        .post()
//                        .url("http://www.rratchet.com/api/user/login")
//                        .addParams("account","12")
//                        .addParams("pwd","56")
//                        .build()
//                        .enqueue(MyJsonCallBack);
                break;
            default:
                break;
        }
    }

    StringCallBack MyStringCallBack = new StringCallBack() {
        @Override
        public void onError(Call call, Exception e) {
            ((TextView) (findViewById(R.id.tv_detail))).setText(e.toString());

        }

        @Override
        public void onResponse(String response) {
            ((TextView) (findViewById(R.id.tv_detail))).setText(response);

        }
    };

    JsonCallBack MyJsonCallBack = new JsonCallBack() {
        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(JSONObject response) {
            ((TextView) (findViewById(R.id.tv_detail))).setText(response.toString());
        }
    };
}
