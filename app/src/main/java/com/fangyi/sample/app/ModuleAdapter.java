package com.fangyi.sample.app;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fangyi.sample.R;

import java.util.List;


/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/28
 * 权    限：
 * 说    明：
 * ================================================
 */
public class ModuleAdapter extends BaseQuickAdapter<ModuleFragment.HomeItem, BaseViewHolder> {

    public ModuleAdapter(@Nullable List<ModuleFragment.HomeItem> data) {
        super(R.layout.app_item_module, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ModuleFragment.HomeItem item) {
        helper.setText(R.id.text, item.getTitle());

        helper.setBackgroundRes(R.id.icon, item.getImageResource());
    }
}
