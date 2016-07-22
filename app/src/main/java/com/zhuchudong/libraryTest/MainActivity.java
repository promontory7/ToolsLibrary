package com.zhuchudong.librarytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zhuchudong.toollibrary.DialogUtils;
import com.zhuchudong.toollibrary.L;
import com.zhuchudong.toollibrary.StatusBarUtil;
import com.zhuchudong.toollibrary.okHttpUtils.OkHttpUtils;
import com.zhuchudong.toollibrary.okHttpUtils.callback.JsonCallBack;
import com.zhuchudong.toollibrary.okHttpUtils.callback.StringCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpUtils.getInstance();
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_netTest).setOnClickListener(this);
        findViewById(R.id.btn_diaTest).setOnClickListener(this);
        findViewById(R.id.btn_xrecyclerview).setOnClickListener(this);
        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.colorPrimary), 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_netTest:
                OkHttpUtils
                        .get()
                        .addParams("account", "12")
                        .addParams("pwd", "56")
                        .url(" http://www.rratchet.com/api/user/login")
                        .build()
                        .connTimeOut(1000)
                        .enqueue(MyJsonCallBack);
//                OkHttpUtils
//                        .post()
//                        .url("http://www.rratchet.com/api/user/login")
//                        .addParams("account","12")
//                        .addParams("pwd","56")
//                        .build()
//                        .enqueue(MyJsonCallBack);
                break;
            case R.id.btn_diaTest:
                DialogUtils.showPrompt(MainActivity.this,"干啥子");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("hehe", "123456");
                    jsonObject.put("hehe", "被修改了");
                    JSONArray jsonArray =new JSONArray();
                    jsonArray.put(1);
                    jsonArray.put(2);
                    jsonArray.put(3);
                    jsonArray.put(1);
                    jsonObject.put("array",jsonArray);

                    JSONArray jsonArray1 =jsonObject.optJSONArray("array");
                    jsonArray1.put(5);
                    jsonObject.put("array",jsonArray1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((TextView) (findViewById(R.id.tv_detail))).setText(jsonObject.toString());
                break;
            case R.id.btn_xrecyclerview:
                startActivity(new Intent(MainActivity.this, com.zhuchudong.librarytest.XRecyclerviewActivity.class));
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
            L.isShowLog = true;
            L.d("D 测试");
            L.e("E 测试");
            L.i("I 测试");
            L.w("W 测试");
            new L().startWriteLogToSdcard(getExternalCacheDir() + "log.txt", true);
        }
    };
}
