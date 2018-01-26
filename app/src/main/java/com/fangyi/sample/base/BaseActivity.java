package com.fangyi.sample.base;

import android.widget.Toast;

import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.mvp.IView;
import com.fangyi.fymvp.base.MvpBaseActivity;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends MvpBaseActivity<P, M> implements IView {

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onError(String message) {

        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
