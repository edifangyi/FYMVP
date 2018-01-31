package com.fangyi.fymvp.mvp;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * ================================================
 */
public interface IView {

    void showLoading();

    void showLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void onError(String message);

    boolean isNetworkConnected();


}
