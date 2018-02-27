package com.fangyi.sample.module_mvp.app_demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.fangyi.sample.R;
import com.fangyi.sample.base.BaseToolbarPagerActivity;
import com.fangyi.sample.module_mvp.app_demo.mvp.contract.DemoViewPagerContract;
import com.fangyi.sample.module_mvp.app_demo.mvp.model.DemoViewPagerModel;
import com.fangyi.sample.module_mvp.app_demo.mvp.presenter.DemoViewPagerPresenter;
import com.fangyi.sample.module_mvp.app_demo.ui.fragment.DemoLazyFragment;
import com.fangyi.sample.module_mvp.other.entity.FragmentEntity;

import java.util.ArrayList;


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class DemoLazyPagerActivity extends BaseToolbarPagerActivity<DemoViewPagerPresenter, DemoViewPagerModel> implements DemoViewPagerContract.View {

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, DemoLazyPagerActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DemoLazyPagerActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mvp_activity_demo_lazy_pager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {


    }

    /**
     * 设置需要展示的Fragment
     *
     * @param list
     * @return
     */
    @Override
    protected ArrayList<FragmentEntity> setShowFragments(ArrayList<FragmentEntity> list) {
        list.add(new FragmentEntity("Android", DemoLazyFragment.newInstance("第一个")));
        list.add(new FragmentEntity("IOS", DemoLazyFragment.newInstance("第二个")));
        list.add(new FragmentEntity("前端", DemoLazyFragment.newInstance("第三个")));
        list.add(new FragmentEntity("后端", DemoLazyFragment.newInstance("第四个")));
        return list;
    }

    /**
     * 设置标题
     *
     * @return
     */
    @Override
    protected String setToolbarTitle() {
        return "LazyPager";
    }

    /**
     * 返回键操作
     */
    @Override
    protected void onBack() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mvp_demo_lazy_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tab_default:

                break;
            case R.id.menu_tab_indicator_color:
                mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.green));
                break;
            case R.id.menu_tab_indicator_height:
                mTabLayout.setSelectedTabIndicatorHeight(5);
                break;
            case R.id.menu_tab_hide_indicator_height:
                mTabLayout.setSelectedTabIndicatorHeight(0);
                break;
            case R.id.menu_tab_hide_indicator_color:
                mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.transparent));
                break;
            case R.id.menu_tab_selected_text_color:

                break;
            case R.id.menu_tab_text_color:
                break;
            case R.id.menu_tab_mode:
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
