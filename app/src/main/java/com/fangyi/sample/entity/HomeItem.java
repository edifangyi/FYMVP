package com.fangyi.sample.entity;

import android.content.Intent;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/28
 * 权    限：
 * 说    明：
 * ================================================
 */
public class HomeItem {
    private String title;
    private int imageResource;
    private Intent intent;

    public HomeItem(String title, int imageResource, Intent intent) {
        this.title = title;
        this.imageResource = imageResource;
        this.intent = intent;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
