package com.fangyi.sample.module_mvp.app_demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fangyi.sample.R;
import com.fangyi.sample.base.BaseToolbarActivity;
import com.fangyi.sample.module_mvp.app_demo.mvp.contract.DemoToolbarContract;
import com.fangyi.sample.module_mvp.app_demo.mvp.model.DemoToolbarModel;
import com.fangyi.sample.module_mvp.app_demo.mvp.presenter.DemoToolbarPresenter;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class DemoToolbarActivity extends BaseToolbarActivity<DemoToolbarPresenter, DemoToolbarModel> implements DemoToolbarContract.View {

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, DemoToolbarActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DemoToolbarActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mvp_activity_demo_toolbar;
    }


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * 设置标题
     *
     * @return
     */
    @Override
    protected String setToolbarTitle() {
        return "标题";
    }

    /**
     * 返回键操作
     */
    @Override
    protected void onBack() {
        finish();
    }
}
