package com.fangyi.sample.mvp.mvp.contract;

import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.basebean.BaseResponse;
import com.fangyi.fymvp.mvp.IModel;
import com.fangyi.fymvp.mvp.IView;
import com.fangyi.sample.mvp.bean.LoginBean;

import java.util.Map;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public interface MVPContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onSuccessView(LoginBean data);

        void onErrorView(String message);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {


        void doSuccer(String doSuccess, Map<String, String> stringStringMap, IModel.Listener<BaseResponse<LoginBean>> listener);


    }

    //方法
    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void doSuccess(String name, String password) throws Throwable;

        public abstract void doError(String name, String password);

        public abstract void doRx2Success(String name, String password);
    }


}
