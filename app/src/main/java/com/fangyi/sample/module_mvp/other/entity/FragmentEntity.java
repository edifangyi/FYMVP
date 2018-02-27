package com.fangyi.sample.module_mvp.other.entity;

import android.support.v4.app.Fragment;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/6
 * 权    限：
 * 说    明：
 * ================================================
 */
public class FragmentEntity {

    private String title;
    private Fragment mFragment;

    public FragmentEntity(String title, Fragment fragment) {
        this.title = title;
        mFragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
