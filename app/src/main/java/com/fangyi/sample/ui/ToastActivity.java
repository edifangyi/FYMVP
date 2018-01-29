package com.fangyi.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fangyi.fymvp.base.SimpleBaseActivity;
import com.fangyi.fyui.toast.UniversalToast;
import com.fangyi.sample.MainActivity;
import com.fangyi.sample.R;

import butterknife.BindView;

public class ToastActivity extends SimpleBaseActivity {

    public static final String[] ITEMS = {"通用toast", "强调toast", "可点击toast"
            , "通用 + 成功toast", "通用 + 警告toast", "通用 + 错误toast"
            , "强调 + 成功toast", "强调 + 警告toast", "强调 + 错误toast"
            , "可点击 + 成功toast", "可点击 + 警告toast", "可点击 + 错误toast"};
    private static final int REQUEST_PERMISSION_CODE = 123;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ToastActivity.class);
        return intent;
    }


    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                UniversalToast.makeText(this, "请允许悬浮窗权限", UniversalToast.LENGTH_SHORT).showWarning();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == REQUEST_PERMISSION_CODE) {
            String text = Settings.canDrawOverlays(this) ? "已获取悬浮窗权限" : "请打开悬浮窗权限";
            UniversalToast.makeText(this, text, UniversalToast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ui_toast;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ListView listView = (ListView) findViewById(R.id.listView);
        final View.OnClickListener onClickListener = v -> Log.d("MainActivity", "toast clicked!!!");
        listView.setAdapter(new ArrayAdapter(mContext, R.layout.ui_item_toast, ITEMS));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!requestPermission()) {
                return;
            }
            switch (position) {
                case 0:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.UNIVERSAL)
                            .setGravity(Gravity.CENTER, 0, 0)
                            .setIcon(R.drawable.toast_ic_done)
                            .show();
                    break;
                case 1:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE)
                            .setIcon(R.drawable.toast_ic_check)
                            .show();
                    break;
                case 2:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                            .setIcon(R.drawable.toast_ic_done)
                            .setGravity(Gravity.TOP, 0, 0)
                            .setClickCallBack("查看", onClickListener)
                            .show();
                    break;
                case 3:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT).showSuccess();
                    break;
                case 4:
                    UniversalToast.makeText(mContext, "请先登录", UniversalToast.LENGTH_SHORT).showWarning();
                    break;
                case 5:
                    UniversalToast.makeText(mContext, "关注失败", UniversalToast.LENGTH_SHORT).showError();
                    break;
                case 6:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showSuccess();
                    break;
                case 7:
                    UniversalToast.makeText(mContext, "请先登录", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showWarning();
                    break;
                case 8:
                    UniversalToast.makeText(mContext, "关注失败", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showError();
                    break;
                case 9:
                    UniversalToast.makeText(mContext, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                            .setClickCallBack("查看", onClickListener)
                            .showSuccess();
                    break;
                case 10:
                    UniversalToast.makeText(mContext, "请先登录", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                            .setClickCallBack("查看", onClickListener)
                            .showWarning();
                    break;
                case 11:
                    UniversalToast.makeText(mContext, "关注失败", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                            .setClickCallBack("查看", onClickListener)
                            .showError();
                    break;
                default:
            }
        });
    }
}
