package com.fangyi.sample.mvp.mvp.presenter;

import com.fangyi.fymvp.basebean.BaseResponse;
import com.fangyi.fymvp.callback.JsonBaseCallback;
import com.fangyi.fymvp.callback.JsonBaseConvert;
import com.fangyi.sample.mvp.api.ApiParams;
import com.fangyi.sample.mvp.api.ApiUrls;
import com.fangyi.sample.mvp.bean.LoginBean;
import com.fangyi.sample.mvp.callback.DialogCallback;
import com.fangyi.sample.mvp.mvp.contract.MVPContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;
import com.socks.library.KLog;

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

        //将来改成全局监听
        if (!mView.isNetworkConnected()) {
            mView.onErrorView("没有网络");
            return;
        }

        if (!mView.isNetworkConnected())

            KLog.e("==========回家啊哈哈===");

        mModel.doSuccess(ApiParams.doSuccess(name, password))
                //自带Dialog的回调
                .execute(new DialogCallback<BaseResponse<LoginBean>>(mActivity) {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {

                        //回调都在主线程中，可以在这里直接更新UI

                        //判断View还存在不，以免碰到请求网络的过程中、Activity销毁
                        if (isViewAttached()) {
                            mView.onSuccessView(response.body().data);
                        }

                    }


                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);
                        if (isViewAttached()) {
                            mView.onErrorView("失败了");
                        }
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

        //不需要 mModel 层的请求方式
        OkGo.<BaseResponse<LoginBean>>get(url)
                .params(ApiParams.doSuccess(name, password))
                .execute(new JsonBaseCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                        if (isViewAttached()) {
                            mView.onSuccessView(response.body().data);
                        }
                    }


                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);
                        if (isViewAttached()) {
                            mView.onErrorView(response.getException().getMessage());
                        }

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