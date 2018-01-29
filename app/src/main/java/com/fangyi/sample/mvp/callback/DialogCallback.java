package com.fangyi.sample.mvp.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.fangyi.fymvp.callback.JsonBaseCallback;
import com.lzy.okgo.request.base.Request;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：对于网络请求是否需要弹出进度对话框
 * ================================================
 */
public abstract class DialogCallback<T> extends JsonBaseCallback<T> {

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }




    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
