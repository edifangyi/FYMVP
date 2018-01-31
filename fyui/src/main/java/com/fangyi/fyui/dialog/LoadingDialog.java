package com.fangyi.fyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/26
 * 说    明：
 * ================================================
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        this(context, android.R.style.Theme_Dialog);
    }


    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
