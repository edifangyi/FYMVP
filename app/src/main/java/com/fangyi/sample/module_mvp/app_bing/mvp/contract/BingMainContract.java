package com.fangyi.sample.module_mvp.app_bing.mvp.contract;

import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.mvp.IModel;
import com.fangyi.fymvp.mvp.IView;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public interface BingMainContract {
    interface View extends IView {

    }

    interface Model extends IModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

    }
}
