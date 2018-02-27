package com.fangyi.sample.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fangyi.sample.module_mvp.other.entity.FragmentEntity;

import java.util.ArrayList;


/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/6
 * 权    限：
 * 说    明：
 * ================================================
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FragmentEntity> mArrayList;

    public BasePagerAdapter(FragmentManager fm, ArrayList<FragmentEntity> list) {
        super(fm);
        mArrayList = list;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mArrayList.get(position).getFragment();
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mArrayList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayList.get(position).getTitle();
    }

}