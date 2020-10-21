package com.example.screenrecorder;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private boolean isStarted = false;
    private FloatingActionButton mRecorderFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecorderFab = findViewById(R.id.fab);
        mRecorderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if(!isStarted) {
                    startScreenRecorder();
                }else{
                    stopScreenRecorder();
                }
            }
        });
    }

    private void stopScreenRecorder() {
        Intent stopIntent = new Intent(this, RecorderService.class);
        stopService(stopIntent);
        mRecorderFab.setImageResource(R.drawable.start);
        isStarted = false;
    }

    private void startScreenRecorder(){
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent intent = mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode == 1) {
            if(resultCode == RESULT_OK){
                *//**
                 * 获取屏幕相关数据
                 *//*
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int screenWidth = metrics.widthPixels;
                int screenHeight = metrics.heightPixels;
                int screenDensity = metrics.densityDpi;
                // 开启Service录制
                Intent serviceIntent = new Intent(this, RecorderService.class);
                serviceIntent.putExtra("code", resultCode);
                serviceIntent.putExtra("intent", data);
                serviceIntent.putExtra("audio", true);
                serviceIntent.putExtra("width", screenWidth);
                serviceIntent.putExtra("height", screenHeight);
                serviceIntent.putExtra("density", screenDensity);
                serviceIntent.putExtra("quality", "HD");
                startService(serviceIntent);
                isStarted = true;
                mRecorderFab.setImageResource(R.drawable.pause);
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}