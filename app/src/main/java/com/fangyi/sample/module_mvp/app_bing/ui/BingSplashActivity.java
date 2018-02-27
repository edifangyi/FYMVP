package com.fangyi.sample.module_mvp.app_bing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fangyi.sample.R;
import com.fangyi.sample.base.BaseActivity;
import com.fangyi.sample.module_mvp.app_bing.mvp.contract.BingSplashContract;
import com.fangyi.sample.module_mvp.app_bing.mvp.model.BingSplashModel;
import com.fangyi.sample.module_mvp.app_bing.mvp.presenter.BingSplashPresenter;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class BingSplashActivity extends BaseActivity<BingSplashPresenter, BingSplashModel> implements BingSplashContract.View {

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, BingSplashActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, BingSplashActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.mvp_activity_bing_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mPresenter.haha();
    }
}
