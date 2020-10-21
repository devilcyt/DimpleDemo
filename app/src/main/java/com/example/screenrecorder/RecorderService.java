package com.example.screenrecorder;

import android.app.Service;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RecorderService extends Service {

    private int mResult_code;
    private int mWidth;
    private int mHeight;
    private int mDensity;
    private String mQuality;
    private Intent mRequestIntent;
    private boolean isAudio;

    private MediaProjection mMediaProjection;
    private MediaRecorder mMediaRecorder;
    private VirtualDisplay mVirtualDisplay;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mResult_code = intent.getIntExtra("code", 0);
        mRequestIntent = intent.getParcelableExtra("intent");
        isAudio = intent.getBooleanExtra("audio", false);
        mWidth = intent.getIntExtra("width", 720);
        mHeight = intent.getIntExtra("height", 1080);
        mDensity = intent.getIntExtra("density", 0);
        mQuality = intent.getStringExtra("quality");
        return super.onStartCommand(intent, flags, startId);
    }
}
