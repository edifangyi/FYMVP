package com.fangyi.fymvp.base;

import android.os.Bundle;

import com.fangyi.fymvp.utils.ClassUtil;
import com.fangyi.fymvp.utils.NetworkUtils;


/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/23
 * 说    明：带有Mvp的Activity
 * ================================================
 */
public abstract class MvpBaseFragment<P extends BasePresenter, M extends BaseModel> extends SimpleBaseFragment implements IView {

    public P mPresenter;
    public M mModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ClassUtil.getT(this, 0);
        mModel = ClassUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.attachActivity(getActivity());
            mPresenter.attachView(this);
            mPresenter.attachModel(mModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            //断开View引用
            mPresenter.detachView();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getContext());
    }


}