package com.zhuchudong.librarytest;

import android.content.Context;
import android.widget.Toast;

import com.zhuchudong.toollibrary.L;
import com.zhuchudong.toollibrary.ToastUtils;
import com.zhuchudong.toollibrary.async.AsyncExecutor;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ExampleTask extends AsyncExecutor.Worker {
    private Context context;

    public ExampleTask(Context context) {
        this.context = context;
    }


    @Override
    protected Object doInBackground() {
        int sum = 8;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        L.d(data + "");
        ToastUtils.showToast(context, data + "");
    }
}
