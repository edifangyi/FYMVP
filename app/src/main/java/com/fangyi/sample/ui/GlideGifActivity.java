package com.fangyi.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fangyi.fymvp.base.SimpleBaseActivity;
import com.fangyi.sample.R;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/28
 * 权    限：
 * 说    明：
 * ================================================
 */
public class GlideGifActivity extends SimpleBaseActivity {
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.imageView1)
    ImageView mImageView1;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, GlideGifActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ui_glide_gif;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.mipmap.ic_launcher_round)
                .error(android.R.drawable.stat_notify_error)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(mContext)
                .asGif()
                .load(ContextCompat.getDrawable(mContext, R.drawable.building))
                .apply(options)
                .into(mImageView);


        Glide.with(mContext)
                .load(ContextCompat.getDrawable(mContext, R.mipmap.home_mvp_ic_architecture))
                .into(mImageView1);
    }
}
