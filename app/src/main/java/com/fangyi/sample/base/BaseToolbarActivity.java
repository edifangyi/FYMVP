package com.fangyi.sample.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.mvp.IView;
import com.fangyi.sample.R;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/1
 * 权    限：
 * 说    明：
 * ================================================
 */
public abstract class BaseToolbarActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity<P, M> implements IView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(setToolbarTitle());
    }


    private void initToolbar(String title) {
        mToolbar.setNavigationIcon(R.drawable.toolbar_ic_back);
        mToolbarTitle.setText(title);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> onBack());
    }

    /**
     * 设置标题
     *
     * @return
     */
    protected abstract String setToolbarTitle();


    /**
     * 按下返回箭头操作
     */
    protected abstract void onBack();
}
