package cn.android.example.knowledge.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
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
import cn.project.demo.com.R;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;

/**
 * Created by chawei on 2018/2/24.
 */

public class ScreenRecorderActivity extends ActivityManagePermission {

    public static final String TAG = "ez";
    private static  int DISPLAY_WIDTH = 720;
    private static  int DISPLAY_HEIGHT = 1280;
    private static final int RECORD_REQUEST_CODE = 100;
    private int mScreenDensity;
    private Button mBtnRecorder;
    boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mProjectionManager;
    private MediaProjectionCallback mMediaProjectionCallback;
    private static final SparseIntArray ORIENTTIONS = new SparseIntArray();

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

        mMediaRecorder = new MediaRecorder();
        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        mBtnRecorder = (Button) findViewById(R.id.id_btn_screen_recorder);
        mBtnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onToggleRecordScreen();
                startService(new Intent(ScreenRecorderActivity.this, FloatingViewService.class));
            }
        });


    }

    //是否开启录制
    private void onToggleRecordScreen() {
        if (!isRecording) {
            initRecorder();
            recordScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            stopRecordScreen();
        }
    }

    //初始化录制参数
    private void initRecorder() {
        try {
            if (mMediaRecorder == null) {
                Log.d(TAG, "initRecorder: MediaRecorder为空啊---");
                return;
            }
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);// 视频源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//视频输出格式
//            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".3gp");
            mMediaRecorder.setOutputFile("/storage/emulated/0/ScreenRecorder/" + System.currentTimeMillis() + ".mp4");
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);// 设置分辨率
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频录制格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式
            mMediaRecorder.setVideoFrameRate(16);//帧率
//            mMediaRecorder.setVideoEncodingBitRate(5242880);//视频清晰度
            mMediaRecorder.setVideoEncodingBitRate(52428800);//视频清晰度
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientataion = ORIENTTIONS.get(rotation + 90);
            mMediaRecorder.setOrientationHint(orientataion);//设置旋转方向
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //开始录制
    private void recordScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), RECORD_REQUEST_CODE);
            return;
        }

        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
        isRecording = true;
        changeText();
    }

    //停止录制
    private void stopRecordScreen() {
        if (mVirtualDisplay == null) {
            return;
        }

        mVirtualDisplay.release();
        destroyMediaProjection();
        isRecording = false;
        changeText();
    }

    //释放录制的资源
    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("ScreenRecorder", DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(), null, null);
    }

    private void changeText() {
        if (isRecording) {
            mBtnRecorder.setText("停止录屏");
        } else {
            mBtnRecorder.setText("开始录屏");
        }
    }

    //录制回调
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (isRecording) {
                isRecording = false;
                changeText();
                mMediaRecorder.stop();
                mMediaRecorder.reset();
            }
            mMediaProjection = null;
            stopRecordScreen();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE) {

            if (resultCode != RESULT_OK) {
                Toast.makeText(ScreenRecorderActivity.this, "录屏权限被禁止了啊", Toast.LENGTH_SHORT).show();
                isRecording = false;
                changeText();
                return;
            }

            mMediaProjectionCallback = new MediaProjectionCallback();
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
            mMediaProjection.registerCallback(mMediaProjectionCallback, null);
            mVirtualDisplay = createVirtualDisplay();
            mMediaRecorder.start();
            isRecording = true;
            changeText();
        }

    }

    //停止录制时，释放资源
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
