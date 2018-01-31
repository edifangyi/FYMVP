package com.fangyi.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.fangyi.fymvp.base.SimpleBaseActivity;
import com.fangyi.fymvp.base.SimpleBaseFragment;
import com.fangyi.fyui.utils.FragmentUtils;
import com.fangyi.sample.app.MyApplication;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/27
 * 权    限：
 * 说    明：
 * ================================================
 */
public class MainActivity extends SimpleBaseActivity {
    @BindView(R.id.tab)
    PageNavigationView mTab;


    private NavigationController mNavigationController;

    private SimpleBaseFragment[] mFragments = new SimpleBaseFragment[4];
    private int curIndex;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mFragments[0] = HomeFragment.newInstance("MVP");
        mFragments[1] = HomeFragment.newInstance("UI");
        mFragments[2] = HomeFragment.newInstance("Utils");
        mFragments[3] = HomeFragment.newInstance("GitHub");


        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.activity_frag_container, curIndex);

        mNavigationController = mTab.custom()
                .addItem(newItem(R.drawable.main_ic_mvp_uncheck, R.drawable.main_ic_mvp_check, "MVP"))
                .addItem(newItem(R.drawable.main_ic_ui_uncheck, R.drawable.main_ic_ui_check, "UI"))
                .addItem(newItem(R.drawable.main_ic_utils_uncheck, R.drawable.main_ic_utils_check, "Utils"))
                .addItem(newItem(R.drawable.main_ic_github_uncheck, R.drawable.main_ic_github_check, "GitHub"))
                .build();


        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentUtils.showHide(index, mFragments);
            }

            @Override
            public void onRepeat(int index) {

            }
        });

    }


    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        return normalItemView;
    }


}
