package com.fangyi.fymvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public abstract class SimpleBaseFragment extends Fragment {
    public View rootView;

    protected SimpleBaseActivity mActivity;
    public Context mContext;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutId(), container, false);

        mActivity = (SimpleBaseActivity) getActivity();
        mContext = getActivity();


        mUnbinder = ButterKnife.bind(this, rootView);
        init(savedInstanceState);

        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        mUnbinder = null;
    }
}
