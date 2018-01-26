package com.fangyi.fymvp.base;

/**
 * Created by jess on 16/4/28.
 */
public interface IPresenter<V extends IView, M extends IModel> {

    void attachView(V view);

    void attachModel(M model);

    void detachView();

    boolean isViewAttached();

}
