package com.fangyi.fymvp.mvp;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * ================================================
 */
public interface IPresenter<V extends IView, M extends IModel> {

    void attachView(V view);

    void attachModel(M model);

    void detachView();

    boolean isViewAttached();

}
