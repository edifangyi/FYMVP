package com.fangyi.sample.main;


import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.mvp.IModel;
import com.fangyi.fymvp.mvp.IView;
import com.fangyi.fymvp.basebean.BaseResponse;
import com.fangyi.sample.bean.LoginBean;
import com.lzy.okgo.request.GetRequest;

import java.util.Map;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/24
 * 说    明：
 * ================================================
 */
public interface MainContract {

    interface Model extends IModel {

        GetRequest<BaseResponse<LoginBean>> doSuccess(Map<String, String> stringStringMap);

    }

    interface View extends IView {
        void onSuccessView(LoginBean data);

        void onErrorView(String message);
    }


    abstract class Presenter extends BasePresenter<View, Model> {

        abstract void doSuccess(String name, String password);

        abstract void doError(String name, String password);

        abstract void doRx2Success(String name, String password);
    }

}
