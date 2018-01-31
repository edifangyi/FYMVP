package com.fangyi.sample.mvp.mvp.presenter;

import com.fangyi.fymvp.basebean.BaseResponse;

import com.fangyi.fymvp.mvp.IModel;
import com.fangyi.sample.mvp.api.ApiParams;
import com.fangyi.sample.mvp.api.ApiUrls;
import com.fangyi.sample.mvp.bean.LoginBean;
import com.fangyi.sample.mvp.callback.JsonBaseCallback;
import com.fangyi.sample.mvp.callback.JsonBaseConvert;
import com.fangyi.sample.mvp.mvp.contract.MVPContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class MVPPresenter extends MVPContract.Presenter {


    @Override
    public void doSuccess(String name, String password) {

        if (!mView.isNetworkConnected()) {
            mView.onError("没有网络");
            return;
        }


        mModel.<BaseResponse<LoginBean>>doSuccer(
                ApiUrls.doSuccess,
                ApiParams.doSuccess(name, password),
                new IModel.Listener<BaseResponse<LoginBean>>() {
                    @Override
                    public void onResult(BaseResponse<LoginBean> response) {
                        mView.onSuccessView(response.data);
                    }

                    @Override
                    public void onError(String mssage) {
                        mView.onError(mssage);
                    }
                });


    }

    @Override
    public void doError(String name, String password) {


        if (!mView.isNetworkConnected()) {
            mView.onError("没有网络");
            return;
        }

        String url;

        if (name.equals(password)) {
            //未知错误码
            url = ApiUrls.doError_1;
        } else {
            //已知错误码
            url = ApiUrls.doError;
        }

//        //不需要 mModel 层的请求方式
//        OkGo.<BaseResponse<LoginBean>>get(url)
//                .params(ApiParams.doSuccess(name, password))
//                .execute(new JsonBaseCallback<BaseResponse<LoginBean>>() {
//                    @Override
//                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
//                        if (isViewAttached()) {
//                            mView.onSuccessView(response.body().data);
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(Response<BaseResponse<LoginBean>> response) {
//                        super.onError(response);
//                        if (isViewAttached()) {
//                            mView.onErrorView(response.getException().getMessage());
//                        }
//
//                    }
//                });


        mModel.<BaseResponse<LoginBean>>get(
                ApiUrls.doSuccess,
                ApiParams.doSuccess(name, password)).
                execute(new JsonBaseCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {


                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);

                        mView.onError(response.getException().getMessage());
                    }

                });


    }

    @Override
    public void doRx2Success(String name, String password) {

        if (!mView.isNetworkConnected()) {
            mView.onError("没有网络");
            return;
        }


        //Rx同理
        OkGo.<BaseResponse<LoginBean>>get(ApiUrls.doSuccess)
                .params(ApiParams.doSuccess(name, password))
                .converter(new JsonBaseConvert<BaseResponse<LoginBean>>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mView.showLoading())
                .map(response -> response.data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (isViewAttached()) {
                            mView.onSuccessView(loginBean);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            mView.onErrorView(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                })

        ;

    }
}