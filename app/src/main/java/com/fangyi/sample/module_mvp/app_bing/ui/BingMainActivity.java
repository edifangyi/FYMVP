package com.fangyi.sample.module_mvp.app_bing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fangyi.sample.R;
import com.fangyi.sample.base.BaseActivity;
import com.fangyi.sample.module_mvp.app_bing.mvp.contract.BingMainContract;
import com.fangyi.sample.module_mvp.app_bing.mvp.model.BingMainModel;
import com.fangyi.sample.module_mvp.app_bing.mvp.presenter.BingMainPresenter;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class BingMainActivity extends BaseActivity<BingMainPresenter, BingMainModel> implements BingMainContract.View {

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, BingMainActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.mvp_activity_bing_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


}
