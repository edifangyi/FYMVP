package com.fangyi.sample;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fangyi.fymvp.base.SimpleBaseFragment;
import com.fangyi.sample.adapter.HomeAdapter;
import com.fangyi.sample.entity.HomeItem;
import com.fangyi.sample.mvp.ui.activity.MVPActivity;
import com.fangyi.sample.ui.GlideGifActivity;
import com.fangyi.sample.ui.ToastActivity;

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
public class HomeFragment extends SimpleBaseFragment {

    private final static String KEY_TITLE = "data_title";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String mTitle;

    private ArrayList<HomeItem> mDataList;
    private HomeAdapter mAdapter;

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mTitle = getArguments().getString(KEY_TITLE);

        initData();

        initToobar();

        initRecyclerView();

    }

    private void initToobar() {
        mToolbar.setTitle(mTitle);
        mActivity.setSupportActionBar(mToolbar);


    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new HomeAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(mDataList.get(position).getIntent());
        });
    }


    private void initData() {
        mDataList = new ArrayList<>();

        switch (mTitle) {
            case "MVP":
                mDataList.add(new HomeItem("MVP框架演示", R.mipmap.home_mvp_ic_architecture, MVPActivity.createIntent(mContext)));
                break;
            case "UI":
                mDataList.add(new HomeItem("增强Toast", R.mipmap.home_ui_ic_toast_0, ToastActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("普通Toast", R.mipmap.home_ui_ic_toast_0, ToastActivity.createIntent(mContext)));
                mDataList.add(new HomeItem("Glide", R.mipmap.home_ui_ic_toast_0, GlideGifActivity.createIntent(mContext)));

                break;
            case "Utils":
                break;
            case "GitHub":
                break;
        }
    }

}
