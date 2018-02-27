package com.fangyi.sample.module_mvp.app_demo.ui.fragment;

import android.os.Bundle;

import com.fangyi.sample.R;
import com.fangyi.sample.base.BaseLazyFragment;
import com.fangyi.sample.module_mvp.app_demo.mvp.contract.DemoLazyContract;
import com.fangyi.sample.module_mvp.app_demo.mvp.model.DemoLazyModel;
import com.fangyi.sample.module_mvp.app_demo.mvp.presenter.DemoLazyPresenter;
import com.socks.library.KLog;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class DemoLazyFragment extends BaseLazyFragment<DemoLazyPresenter, DemoLazyModel> implements DemoLazyContract.View {
    private static String KEY_DATA = "key_data";


    private String title;

    public static DemoLazyFragment newInstance(String title) {
        DemoLazyFragment fragment = new DemoLazyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mvp_fragment_demo_lazy;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        title = getArguments().getString(KEY_DATA);



        KLog.e("=====初始化==========" + title);
    }


    /**
     * 当视图初始化并且对可见时加载数据
     */
    @Override
    protected void lazyLoad() {

        KLog.e("=====可见时加载数据==========" + title);
    }
}
