package com.fangyi.sample.base;

import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.base.MvpBaseActivity;
import com.fangyi.fymvp.mvp.IView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends MvpBaseActivity<P, M> implements IView {

    private MaterialDialog mDialog;

    @Override
    public void showLoading() {
        showLoading("Loading...");
    }

    @Override
    public void showLoading(String message) {
        mDialog = new MaterialDialog.Builder(this)
                .content(message)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .show();
    }

    @Override
    public void dismissLoading() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mContext, "showToast " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(mContext, "onError " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }


}
