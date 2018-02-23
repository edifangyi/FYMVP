package com.fangyi.sample.ui.hencoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fangyi.fymvp.base.SimpleBaseActivity;
import com.fangyi.sample.R;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/14
 * 权    限：
 * 说    明：
 * ================================================
 */
public class HenCoderActivity extends SimpleBaseActivity {
    public static final String[] ITEMS = {"HenCoder 绘制 1 练习项目", "HenCoder 绘制 2 练习项目"};
    @BindView(R.id.listView)
    ListView mListView;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, HenCoderActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ui_hencoder;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mListView.setAdapter(new ArrayAdapter(mContext, R.layout.ui_item_toast, ITEMS));
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
