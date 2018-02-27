package com.fangyi.sample.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.fangyi.fymvp.base.BaseFragment;
import com.fangyi.sample.R;
import com.fangyi.sample.module_mvp.app_bing.ui.BingSplashActivity;
import com.fangyi.sample.module_mvp.app_demo.ui.activity.DemoToolbarActivity;
import com.fangyi.sample.module_mvp.app_demo.ui.activity.DemoLazyPagerActivity;
import com.fangyi.sample.module_ui.glide.GlideActivity;
import com.fangyi.sample.module_ui.imui.IMUIActivity;
import com.fangyi.sample.module_ui.toast.ToastActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/27
 * 权    限：
 * 说    明：
 * ================================================
 */
public class ModuleFragment extends BaseFragment {

    private final static String KEY_TITLE = "data_title";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private String mTitle;

    private ArrayList<HomeItem> mDataList;
    private ModuleAdapter mAdapter;

    public static ModuleFragment newInstance(String title) {
        ModuleFragment fragment = new ModuleFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_module;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mTitle = getArguments().getString(KEY_TITLE);

        initData();

        initToobar();

        initRecyclerView();

    }

    private void initToobar() {

        mToolbarTitle.setText(mTitle);
        mActivity.setSupportActionBar(mToolbar);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new ModuleAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(mDataList.get(position).getIntent()));
    }


    private void initData() {
        mDataList = new ArrayList<>();

        switch (mTitle) {
            case "MVP":
                mDataList.add(new HomeItem("Bing", R.mipmap.home_mvp_ic_architecture, BingSplashActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("MVP演示 - Toolbar", R.mipmap.home_mvp_ic_architecture, DemoToolbarActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("MVP演示 - LazyPager", R.mipmap.home_mvp_ic_architecture, DemoLazyPagerActivity.createIntent(mContext)));
                break;
            case "UI":
                mDataList.add(new HomeItem("增强Toast", R.mipmap.home_ui_ic_toast, ToastActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("Glide", R.mipmap.home_ui_ic_toast, GlideActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("IM UI", R.mipmap.home_ui_ic_custom, IMUIActivity.createIntent(mContext)));

                break;
            case "Utils":
                break;
            case "GitHub":
                break;
        }
    }

    public class HomeItem {
        private String title;
        private int imageResource;
        private Intent intent;

        public HomeItem(String title, int imageResource, Intent intent) {
            this.title = title;
            this.imageResource = imageResource;
            this.intent = intent;
        }

        public int getImageResource() {
            return imageResource;
        }

        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }
    }

}
