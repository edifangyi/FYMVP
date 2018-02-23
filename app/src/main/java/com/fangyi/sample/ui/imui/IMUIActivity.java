package com.fangyi.sample.ui.imui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fangyi.sample.R;
import com.fangyi.sample.ui.imui.models.DefaultUser;
import com.fangyi.sample.ui.imui.models.MyMessage;
import com.fangyi.sample.ui.imui.views.ChatView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.model.VideoItem;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.ViewHolderController;
import cn.jiguang.imui.messages.ptr.PtrHandler;
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/2/22
 * 权    限：
 * 说    明：
 * ================================================
 */
public class IMUIActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnTouchListener {
    private final static String TAG = "IMUIActivity";
    private final int RC_RECORD_VOICE = 0x0001;
    private final int RC_CAMERA = 0x0002;
    private final int RC_PHOTO = 0x0003;

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.title_container)
    LinearLayout mTitleContainer;
    @BindView(R.id.msg_list)
    MessageList mMsgList;
    @BindView(R.id.pull_to_refresh_layout)
    PullToRefreshLayout mPullToRefreshLayout;
    @BindView(R.id.chat_input)
    ChatInputView mChatInput;
    @BindView(R.id.chat_view)
    ChatView mChatView;

    private List<MyMessage> mData;
    private MsgListAdapter<MyMessage> mAdapter;

    private InputMethodManager mImm;
    private Window mWindow;
    private HeadsetDetectReceiver mReceiver;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, IMUIActivity.class);
        return intent;
    }


    public Context mContext;
    public Handler mHandler;
    private Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ui_imui);
        mUnbinder = ButterKnife.bind(this);

        mContext = this;
        mHandler = new Handler();

        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
        mHandler = null;
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        mUnbinder = null;
    }

    private void init() {

        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = getWindow();
        registerProximitySensorListener();

        mChatView.initModule();
        mChatView.setTitle("Deadpool");

        mData = getMessages();


        initMsgAdapter();

        mReceiver = new HeadsetDetectReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);

        mChatView.setOnTouchListener(this);
        mChatView.setMenuClickListener(new OnMenuClickListener() {
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) {
                    return false;
                }
                MyMessage message = new MyMessage(input.toString(), IMessage.MessageType.SEND_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("1", "Ironman", "R.mipmap.ic_launcher"));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                mAdapter.addToStart(message, true);
                return true;
            }

            @Override
            public void onSendFiles(List<FileItem> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }

                MyMessage message;
                for (FileItem item : list) {
                    if (item.getType() == FileItem.Type.Image) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());

                    } else if (item.getType() == FileItem.Type.Video) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO.ordinal());
                        message.setDuration(((VideoItem) item).getDuration());

                    } else {
                        throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video");
                    }

                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    message.setMediaFilePath(item.getFilePath());
                    message.setUserInfo(new DefaultUser("1", "Ironman", "R.mipmap.ic_launcher"));

                    final MyMessage fMsg = message;
                    runOnUiThread(() -> mAdapter.addToStart(fMsg, true));
                }
            }

            @Override
            public boolean switchToMicrophoneMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(IMUIActivity.this, perms)) {
                    EasyPermissions.requestPermissions(IMUIActivity.this,
                            getResources().getString(R.string.rationale_record_voice),
                            RC_RECORD_VOICE, perms);
                }
                return true;
            }

            @Override
            public boolean switchToGalleryMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(IMUIActivity.this, perms)) {
                    EasyPermissions.requestPermissions(IMUIActivity.this,
                            getResources().getString(R.string.rationale_photo),
                            RC_PHOTO, perms);
                }
                return true;
            }

            @Override
            public boolean switchToCameraMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                };

                if (!EasyPermissions.hasPermissions(IMUIActivity.this, perms)) {
                    EasyPermissions.requestPermissions(IMUIActivity.this,
                            getResources().getString(R.string.rationale_camera),
                            RC_CAMERA, perms);
                } else {
                    File rootDir = getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/photo";
                    mChatView.setCameraCaptureFile(fileDir, new SimpleDateFormat("yyyy-MM-dd-hhmmss",
                            Locale.getDefault()).format(new Date()));
                }
                return true;
            }

            @Override
            public boolean switchToEmojiMode() {
                scrollToBottom();
                return true;
            }
        });

        mChatView.setRecordVoiceListener(new RecordVoiceListener() {
            @Override
            public void onStartRecord() {
                // set voice file path, after recording, audio file will save here
                String path = Environment.getExternalStorageDirectory().getPath() + "/voice";
                File destDir = new File(path);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                mChatView.setRecordVoiceFile(destDir.getPath(), DateFormat.format("yyyy-MM-dd-hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + "");
            }

            @Override
            public void onFinishRecord(File voiceFile, int duration) {
                MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE.ordinal());
                message.setUserInfo(new DefaultUser("1", "Ironman", "R.mipmap.ic_launcher"));
                message.setMediaFilePath(voiceFile.getPath());
                message.setDuration(duration);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                mAdapter.addToStart(message, true);
            }

            @Override
            public void onCancelRecord() {

            }
        });

        mChatView.setOnCameraCallbackListener(new OnCameraCallbackListener() {
            @Override
            public void onTakePictureCompleted(String photoPath) {
                final MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMediaFilePath(photoPath);
                message.setUserInfo(new DefaultUser("1", "Ironman", "R.mipmap.ic_launcher"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addToStart(message, true);
                    }
                });
            }

            @Override
            public void onStartVideoRecord() {

            }

            @Override
            public void onFinishVideoRecord(String videoPath) {

            }

            @Override
            public void onCancelVideoRecord() {

            }
        });

        mChatView.setOnTouchEditTextListener(() -> new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mChatView.getMessageListView().smoothScrollToPosition(0);
            }
        }, 100));

        mChatView.getSelectAlbumBtn().setOnClickListener(v -> Toast.makeText(IMUIActivity.this, "OnClick select album button",
                Toast.LENGTH_SHORT).show());


    }

    private void registerProximitySensorListener() {
        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    try {
                        if (audioManager.isBluetoothA2dpOn() || audioManager.isWiredHeadsetOn()) {
                            return;
                        }
                        if (mAdapter.getMediaPlayer().isPlaying()) {
                            float distance = event.values[0];
                            if (distance >= mSensor.getMaximumRange()) {
                                mAdapter.setAudioPlayByEarPhone(0);
                                setScreenOn();
                            } else {
                                mAdapter.setAudioPlayByEarPhone(2);
                                ViewHolderController.getInstance().replayVoice();
                                setScreenOff();
                            }
                        } else {
                            if (mWakeLock != null && mWakeLock.isHeld()) {
                                mWakeLock.release();
                                mWakeLock = null;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void setScreenOff() {
        if (mWakeLock == null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        }
        mWakeLock.acquire();
    }

    private List<MyMessage> getMessages() {
        List<MyMessage> list = new ArrayList<>();
        Resources res = getResources();
        String[] messages = res.getStringArray(R.array.messages_array);
        for (int i = 0; i < messages.length; i++) {
            MyMessage message;
            if (i % 2 == 0) {
                message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("0", "DeadPool", "R.mipmap.ic_launcher"));
            } else {
                message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("1", "IronMan", "R.mipmap.ic_launcher"));
            }
            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            list.add(message);
        }
        Collections.reverse(list);
        return list;
    }


    private void initMsgAdapter() {

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                // You can use other image load libraries.
                if (string.contains("R.mipmap")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.mipmap.", ""),
                            "mipmap", getPackageName());

                    avatarImageView.setImageResource(resId);
                } else {
                    Glide.with(getApplicationContext())
                            .load(string)
                            .placeholder(R.drawable.aurora_headicon_default)
                            .into(avatarImageView);
                }
            }

            @Override
            public void loadImage(ImageView imageView, String string) {
                // You can use other image load libraries.
                Glide.with(getApplicationContext())
                        .load(string)
                        .fitCenter()
                        .placeholder(R.drawable.aurora_picture_not_found)
                        .override(400, Target.SIZE_ORIGINAL)
                        .into(imageView);
            }
        };
        // Use default layout
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        mAdapter = new MsgListAdapter<>("0", holdersConfig, imageLoader);
        // If you want to customise your layout, try to create custom ViewHolder:
        // holdersConfig.setSenderTxtMsg(CustomViewHolder.class, layoutRes);
        // holdersConfig.setReceiverTxtMsg(CustomViewHolder.class, layoutRes);
        // CustomViewHolder must extends ViewHolders defined in MsgListAdapter.
        // Current ViewHolders are TxtViewHolder, VoiceViewHolder.

        mAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener<MyMessage>() {
            @Override
            public void onMessageClick(MyMessage message) {
                // do something
                if (message.getType() == IMessage.MessageType.RECEIVE_VIDEO.ordinal()
                        || message.getType() == IMessage.MessageType.SEND_VIDEO.ordinal()) {
                    if (!TextUtils.isEmpty(message.getMediaFilePath())) {
                        Intent intent = new Intent(IMUIActivity.this, IMUIVideoActivity.class);
                        intent.putExtra(IMUIVideoActivity.VIDEO_PATH, message.getMediaFilePath());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.message_click_hint),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter.setMsgLongClickListener(new MsgListAdapter.OnMsgLongClickListener<MyMessage>() {
            @Override
            public void onMessageLongClick(MyMessage message) {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.message_long_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setOnAvatarClickListener(new MsgListAdapter.OnAvatarClickListener<MyMessage>() {
            @Override
            public void onAvatarClick(MyMessage message) {
                DefaultUser userInfo = (DefaultUser) message.getFromUser();
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.avatar_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setMsgStatusViewClickListener(new MsgListAdapter.OnMsgStatusViewClickListener<MyMessage>() {
            @Override
            public void onStatusViewClick(MyMessage message) {
                // message status view click, resend or download here
            }
        });

        MyMessage message = new MyMessage("Hello World", IMessage.MessageType.RECEIVE_TEXT.ordinal());
        message.setUserInfo(new DefaultUser("0", "Deadpool", "R.mipmap.ic_launcher"));
        mAdapter.addToStart(message, true);
        MyMessage eventMsg = new MyMessage("123456", IMessage.MessageType.EVENT.ordinal());
        mAdapter.addToStart(eventMsg, true);
        mAdapter.addToEnd(mData);
        PullToRefreshLayout layout = mChatView.getPtrLayout();
        layout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PullToRefreshLayout layout) {
                Log.i("MessageListActivity", "Loading next page");
                loadNextPage();
            }
        });
        // Deprecated, should use onRefreshBegin to load next page
        mAdapter.setOnLoadMoreListener(new MsgListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalCount) {
//                Log.i("MessageListActivity", "Loading next page");
//                loadNextPage();
            }
        });

        mChatView.setAdapter(mAdapter);
        mAdapter.getLayoutManager().scrollToPosition(0);
    }

    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<MyMessage> list = new ArrayList<>();
                Resources res = getResources();
                String[] messages = res.getStringArray(R.array.conversation);
                for (int i = 0; i < messages.length; i++) {
                    MyMessage message;
                    if (i % 2 == 0) {
                        message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT.ordinal());
                        message.setUserInfo(new DefaultUser("0", "DeadPool", "R.mipmap.ic_launcher"));
                    } else {
                        message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT.ordinal());
                        message.setUserInfo(new DefaultUser("1", "IronMan", "R.mipmap.ic_launcher"));
                    }
                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    list.add(message);
                }
                Collections.reverse(list);
                mAdapter.addToEnd(list);
                mChatView.getPtrLayout().refreshComplete();
            }
        }, 1500);
    }

    private void scrollToBottom() {
        mAdapter.getLayoutManager().scrollToPosition(0);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param view        The view the touch event has been dispatched to.
     * @param motionEvent The MotionEvent object containing full information about
     *                    the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ChatInputView chatInputView = mChatView.getChatInputView();

                if (view.getId() == chatInputView.getInputView().getId()) {
                    scrollToBottom();
                    if (chatInputView.getMenuState() == View.VISIBLE
                            && !chatInputView.getSoftInputState()) {
                        chatInputView.dismissMenuAndResetSoftMode();
                        return false;
                    } else {
                        return false;
                    }
                }
                if (chatInputView.getMenuState() == View.VISIBLE) {
                    chatInputView.dismissMenuLayout();
                }
                try {
                    View v = getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        view.clearFocus();
                        chatInputView.setSoftInputState(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
        }
        return false;
    }


    private class HeadsetDetectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    int state = intent.getIntExtra("state", 0);
                    mAdapter.setAudioPlayByEarPhone(state);
                }
            }
        }
    }


}
