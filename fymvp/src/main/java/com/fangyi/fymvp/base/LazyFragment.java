package com.fangyi.fymvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：懒加载的Fragment
 * ================================================
 */
public abstract class LazyFragment extends BaseFragment {

    private boolean isPrepared;

    private boolean isVisible;

    private boolean isLoadData = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        onVisible();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            if (isLoadData) {
                onInvisible();
            }
        }
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        lazyLoad();
        isLoadData = true;
    }

    /**
     * 当视图初始化并且对可见时加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当该视图对用户不可见并且已经加载过数据的时候，如果需要在切换到其他页面时停止加载数据，通过覆写此方法实现
     */
    protected void onInvisible() {


    }

}
