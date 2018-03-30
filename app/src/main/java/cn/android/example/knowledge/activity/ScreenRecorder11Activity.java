package cn.android.example.knowledge.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import cn.android.example.knowledge.service.FloatingViewService;
import cn.cn.retrofit.demo.com.utils.LogUtil;
import cn.cn.retrofit.demo.com.utils.SharePreUtil;
import cn.project.demo.com.R;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;

/**
 * Created by chawei on 2018/2/24.
 */

public class ScreenRecorder11Activity extends ActivityManagePermission {

    public static final String TAG = "ez";
    private static final int REQUEST_CODE = 1000;
    private int mScreenDensity;
    private Button mBtnRecorder;
    private MediaProjectionManager mProjectionManager;
    private static final int DISPLAY_WIDTH = 720;
    private static final int DISPLAY_HEIGHT = 1280;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private MediaRecorder mMediaRecorder;
    private static final SparseIntArray ORIENTTIONS = new SparseIntArray();
    boolean isRecording = false;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    static {
        ORIENTTIONS.append(Surface.ROTATION_0, 90);
        ORIENTTIONS.append(Surface.ROTATION_90, 0);
        ORIENTTIONS.append(Surface.ROTATION_180, 270);
        ORIENTTIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_recorder_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        SharePreUtil.saveInt("densityDpi",mScreenDensity);

        mMediaRecorder = new MediaRecorder();
        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

//        requestPermission();

//        startService(new Intent(ScreenRecorderActivity.this, FloatingViewService.class));
//        finish();
        mBtnRecorder = (Button) findViewById(R.id.id_btn_screen_recorder);
        mBtnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleScreenShare();


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(ScreenRecorderActivity.this)) {
//
//                    //If the draw over permission is not available open the settings screen
//                    //to grant the permission.
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                            Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
//                } else {
//                    LogUtil.debug("tttttttttttttttttttt");
//                    startService(new Intent(ScreenRecorderActivity.this, FloatingViewService.class));
//                }


            }
        });




    }

    private void onToggleScreenShare() {
        if (!isRecording) {
            initRecorder();
            recordScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            stopRecordScreen();
        }
    }

    private void initRecorder() {
        try {
            if (mMediaRecorder == null) {
                Log.d(TAG, "initRecorder: MediaRecorder为空啊---");
                return;
            }
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);// 音频源
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);// 视频源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//视频输出格式
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() +"/"+System.currentTimeMillis()+".mp4");
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);// 设置分辨率：
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);// 视频录制格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 音频格式
//            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(16);//30
            mMediaRecorder.setVideoEncodingBitRate(5242880);//视频清晰度
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientataion = ORIENTTIONS.get(rotation + 90);
            mMediaRecorder.setOrientationHint(orientataion);
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recordScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
            return;
        }

        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
        isRecording = true;
        actionBtnReload();
    }

    private void stopRecordScreen() {
        if (mVirtualDisplay == null) {
            return;
        }

        mVirtualDisplay.release();
        destroyMediaProjection();
        isRecording = false;
        actionBtnReload();
    }

    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        Log.d(TAG, "destroyMediaProjection: 停止录屏了。。。");
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("ScreenRecorder", DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(), null, null);
    }

    private void actionBtnReload() {
        if (isRecording) {
            mBtnRecorder.setText("停止录屏");
        } else {
            mBtnRecorder.setText("开始录屏");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==REQUEST_CODE) {

            if (resultCode != RESULT_OK) {
                 Toast.makeText(ScreenRecorder11Activity.this, "录屏权限被禁止了啊", Toast.LENGTH_SHORT).show();
                isRecording = false;
                actionBtnReload();
                return;
            }

            mMediaProjectionCallback = new MediaProjectionCallback();
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
            mMediaProjection.registerCallback(mMediaProjectionCallback, null);
            mVirtualDisplay = createVirtualDisplay();
            mMediaRecorder.start();
            isRecording = true;
            actionBtnReload();
            LogUtil.debug("1111111111111111111");
        }else if(requestCode==CODE_DRAW_OVER_OTHER_APP_PERMISSION){
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "需要去设置中允许权限！", Toast.LENGTH_SHORT).show();
                return;
            }

            LogUtil.debug("22222222222222");
            startService(new Intent(ScreenRecorder11Activity.this, FloatingViewService.class));
        }


    }



    private void deniedSnackbar() {
        isRecording = false;
        actionBtnReload();
        Snackbar.make(findViewById(android.R.id.content), "请去设置中允许存储和录音权限",
                Snackbar.LENGTH_INDEFINITE).setAction("GO",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }).show();
    }

    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (isRecording) {
                isRecording = false;
                actionBtnReload();
                mMediaRecorder.stop();
                mMediaRecorder.reset();
            }
            mMediaProjection = null;
            stopRecordScreen();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyMediaProjection();
    }

    @Override
    public void onBackPressed() {
        if (isRecording) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确定停止录屏吗？")
                    .setPositiveButton("停止", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMediaRecorder.stop();
                            mMediaRecorder.reset();
                            Log.d(TAG, "onBackPressed: 停止录屏了。。。");
                            stopRecordScreen();
                            finish();
                        }
                    }).setNegativeButton("继续录吧", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();

        } else {
            finish();

        }
    }
}
