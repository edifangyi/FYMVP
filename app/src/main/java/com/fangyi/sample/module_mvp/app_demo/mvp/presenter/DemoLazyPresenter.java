package com.fangyi.sample.module_mvp.app_demo.mvp.presenter;

import com.fangyi.sample.module_mvp.app_demo.mvp.contract.DemoLazyContract;
import com.socks.library.KLog;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class DemoLazyPresenter extends DemoLazyContract.Presenter {


    @Override
    public void haha() {
        KLog.e("========hahaha");
    }
}