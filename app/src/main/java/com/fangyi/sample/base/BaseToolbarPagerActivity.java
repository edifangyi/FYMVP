package com.fangyi.sample.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.base.BasePresenter;
import com.fangyi.fymvp.mvp.IView;
import com.fangyi.sample.R;
import com.fangyi.sample.module_mvp.other.entity.FragmentEntity;

import java.util.ArrayList;

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
public abstract class BaseToolbarPagerActivity<P extends BasePresenter, M extends BaseModel> extends BaseToolbarActivity<P, M> implements IView {

    @BindView(R.id.tablayout)
    public TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    public ViewPager mViewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<FragmentEntity> list = new ArrayList<>();
        initTabLayout(setShowFragments(list));
    }

    /**
     * 设置需要展示的Fragment
     *
     * @param list
     * @return
     */
    protected abstract ArrayList<FragmentEntity> setShowFragments(ArrayList<FragmentEntity> list);


    private void initTabLayout(ArrayList<FragmentEntity> list) {
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(), list);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewpager, true);
    }


}
