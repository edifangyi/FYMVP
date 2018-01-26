package com.fangyi.fymvp.base;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
public interface IView {

    void showLoading();

    void showLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void onError(String message);

    boolean isNetworkConnected();


}
