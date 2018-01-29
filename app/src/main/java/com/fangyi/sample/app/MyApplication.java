package com.fangyi.sample.app;

import com.fangyi.fymvp.base.BaseApplication;
import com.fangyi.fyui.utils.Utils;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
