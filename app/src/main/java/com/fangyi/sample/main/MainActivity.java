package com.fangyi.sample.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fangyi.sample.base.BaseActivity;
import com.fangyi.sample.R;
import com.fangyi.sample.bean.LoginBean;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {


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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
