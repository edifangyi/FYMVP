package com.fangyi.sample.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fangyi.sample.R;
import com.fangyi.sample.mvp.base.BaseActivity;
import com.fangyi.sample.mvp.bean.LoginBean;
import com.fangyi.sample.mvp.mvp.contract.MVPContract;
import com.fangyi.sample.mvp.mvp.model.MVPModel;
import com.fangyi.sample.mvp.mvp.presenter.MVPPresenter;

import butterknife.BindView;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class MVPActivity extends BaseActivity<MVPPresenter, MVPModel> implements MVPContract.View {

    @BindView(R.id.tv_white_board)
    TextView mTvWhiteBoard;

    @BindView(R.id.btn_success)
    Button mBtnSuccess;
    @BindView(R.id.btn_error)
    Button mBtnError;
    @BindView(R.id.btn_error_1)
    Button mBtnError1;
    @BindView(R.id.btn_rx2_success)
    Button mBtnRx2Success;
    @BindView(R.id.btn_rx2_error)
    Button mBtnRx2Error;

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, MVPActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MVPActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void init(Bundle savedInstanceState) {


        mBtnSuccess.setOnClickListener(v -> mPresenter.doSuccess("fangyi", "123456"));

        mBtnError.setOnClickListener(v -> mPresenter.doError("fangyi", "123456"));
        mBtnError1.setOnClickListener(v -> mPresenter.doError("123456", "123456"));


        mBtnRx2Success.setOnClickListener(v -> mPresenter.doRx2Success("123456", "123456"));
    }


    @Override
    public void onSuccessView(LoginBean data) {
        mTvWhiteBoard.setText(data.toString());
    }


    @Override
    public void onErrorView(String message) {
        mTvWhiteBoard.setText(message);
    }
}
