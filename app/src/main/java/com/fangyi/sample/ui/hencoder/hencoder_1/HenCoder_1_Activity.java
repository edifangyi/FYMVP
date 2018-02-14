package com.fangyi.sample.ui.hencoder.hencoder_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fangyi.fymvp.base.SimpleBaseActivity;
import com.fangyi.sample.R;
import com.fangyi.sample.ui.hencoder.PageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/14
 * 权    限：
 * 说    明：http://hencoder.com/ui-1-1/
 *          http://www.jb51.net/article/106245.htm
 * ================================================
 */
public class HenCoder_1_Activity extends SimpleBaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;

    List<PageModel> pageModels = new ArrayList<>();


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, HenCoder_1_Activity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ui_hencoder_1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_color, "drawColor()", R.layout.activity_ui_hencoder_1_practice_color));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_circle, "drawCircle()", R.layout.activity_ui_hencoder_1_practice_circle));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_rect, "drawRect()", R.layout.activity_ui_hencoder_1_practice_rect));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_point, "drawPoint()", R.layout.activity_ui_hencoder_1_practice_point));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_oval, "drawOval()", R.layout.activity_ui_hencoder_1_practice_oval));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_line, "drawLine()", R.layout.activity_ui_hencoder_1_practice_line));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_round_rect, "drawRoundRect()", R.layout.activity_ui_hencoder_1_practice_round_rect));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_arc, "drawArc()", R.layout.activity_ui_hencoder_1_practice_arc));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_path, "drawPath()", R.layout.activity_ui_hencoder_1_practice_path));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_histogram, "直方图", R.layout.activity_ui_hencoder_1_practice_histogram));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_pie_chart, "饼图1", R.layout.activity_ui_hencoder_1_practice_pie_chart_1));
        pageModels.add(new PageModel(R.layout.activity_ui_hencoder_1_sample_pie_chart, "饼图2", R.layout.activity_ui_hencoder_1_practice_pie_chart_2));


        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageModels.get(position).titleRes;
            }
        });


        mTabLayout.setupWithViewPager(mPager);
    }

    private class PageModel {
        @LayoutRes
        int sampleLayoutRes;
        String titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, String titleRes, @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
