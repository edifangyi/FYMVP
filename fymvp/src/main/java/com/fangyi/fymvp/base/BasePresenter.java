package com.fangyi.fymvp.base;

import android.app.Activity;

import com.fangyi.fymvp.mvp.IModel;
import com.fangyi.fymvp.mvp.IPresenter;
import com.fangyi.fymvp.mvp.IView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/24
 * 说    明：
 * ================================================
 */
public class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V, M> {


    public Activity mActivity;

    public V mView;
    public M mModel;


    /**
     * 绑定view，一般在初始化中调用该方法
     */
    @Override
    public void attachView(V mvpView) {
        this.mView = mvpView;
    }

    /**
     * 绑定Model，一般在初始化中调用该方法
     */
    @Override
    public void attachModel(M model) {
        this.mModel = model;
    }

    public void attachActivity(Activity activity) {
        mActivity = activity;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {
        this.mView = null;
    }


    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }


}
