package com.fangyi.sample.ui.hencoder;

import android.support.annotation.LayoutRes;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/15
 * 权    限：
 * 说    明：
 * ================================================
 */
public class PageModel {
    @LayoutRes
    public int sampleLayoutRes;
    public String titleRes;
    @LayoutRes
    public int practiceLayoutRes;

    public PageModel(@LayoutRes int sampleLayoutRes, String titleRes, @LayoutRes int practiceLayoutRes) {
        this.sampleLayoutRes = sampleLayoutRes;
        this.titleRes = titleRes;
        this.practiceLayoutRes = practiceLayoutRes;
    }
}
