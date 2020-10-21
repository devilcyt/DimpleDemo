package com.example.screenrecorder.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.screenrecorder.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DimpleActivity extends AppCompatActivity {

    private static final String STATE_PLAY = "PLAY";
    private static final String STATE_PAUSE = "PAUSE";
    private static final String STATE_STOP = "STOP";
    private String mState = STATE_STOP;
    private ImageView mMusicImageView;
    private ConstraintLayout mParentRoot;
    private ObjectAnimator mObjectAnimator;
    private boolean isAnimationStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dimple_main);
        mMusicImageView = findViewById(R.id.music_avatar);
        mParentRoot = findViewById(R.id.view_root);
        // 加载图片
        Glide.with(this)
                .load(R.drawable.ic_music2)
                .apply(RequestOptions.circleCropTransform())
                .into(mMusicImageView);

        // 背景变化动画
        Glide.with(this)
                .load(R.drawable.ic_music2)
                .transform(new BlurTransformation(10, 25))
                .into(new SimpleTarget< Drawable >(){
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mParentRoot.setBackground(resource);
                    }
                });
        spinObjectAnimation(mMusicImageView);
        mMusicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(!isAnimationStart) {
                    // 开始播放粒子动画
                    spinAnimation(view);
                }else{
                    view.clearAnimation();
                    isAnimationStart = false;
                }*/
                // 开始播放粒子动画
                playObjectAnimation(mState);
            }
        });
    }

    /*
     *   定义一个旋转动画  使用属性动画
     * */
    private void spinObjectAnimation(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);
        mObjectAnimator.setDuration(6000);
        mObjectAnimator.setRepeatCount(-1);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    private void playObjectAnimation(String state) {
        if (mObjectAnimator != null) {
            switch (state) {
                case STATE_STOP:
                    mObjectAnimator.start();
                    mState = STATE_PLAY;
                    break;
                case STATE_PLAY:
                    mObjectAnimator.pause();
                    mState = STATE_PAUSE;
                    break;
                case STATE_PAUSE:
                    mObjectAnimator.resume();
                    mState = STATE_PLAY;
                    break;
                default:
                    break;
            }
        }
    }

    /*
     *   定义一个旋转动画 使用补间动画
     * */
    private void spinAnimation(View imageView) {
        Animation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 围绕图片中心
        rotate.setDuration(6000); // 间隔
        rotate.setRepeatCount(2); // 重复次数
        LinearInterpolator line = new LinearInterpolator(); // 匀速变化
        rotate.setFillAfter(true); // 动画结束后停在最后一帧
        rotate.setInterpolator(line);
        imageView.startAnimation(rotate);
        isAnimationStart = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMusicImageView != null) {
            mMusicImageView.clearAnimation();
        }
    }
}
