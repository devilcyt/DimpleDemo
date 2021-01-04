package com.example.screenrecorder.view;

/*
*  粒子动画
* */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DimpleView extends View {

    private List<Particle> particleList = new ArrayList<>(); // 粒子集合
    private Paint mPaint;
    private float mCenterX;
    private float mCenterY;
    private ValueAnimator mAnimator; // 粒子动画
    private Random mRandom;
    private Path mCirclePath; // 路径，可以添加圆、线、曲线等
    private PathMeasure mPathMeasure; // 路径，用于测量扩散圆某一处的X,Y值，（粒子初始的X,Y）
    private float[] pos = new float[2]; // 扩散圆上某一点的x,y
    private float[] tan = new float[2]; // 扩散圆上某一点切线
    private int particleNumber = 2000; //粒子数量
    private float particleRadius = 2.2f; //粒子半径
    private float diffusionRadius = 268f; //扩散圆半径

    public DimpleView(Context context) {
        this(context, null);
    }

    public DimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true); // 抗锯齿

        mCirclePath = new Path();
        mPathMeasure = new PathMeasure();

        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(-1);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 更新粒子状态
                updateParticle();
                // 重绘界面
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int index = 0;
        while(index < particleList.size() && particleList.size() != 0) {
            if(particleList.get(index).offSet > 5f){
                double alpha = (1 - particleList.get(index).offSet / particleList.get(index).maxOffset) * 0.8 * 225f;
                mPaint.setAlpha((int)alpha);
                canvas.drawCircle(particleList.get(index).x, particleList.get(index).y,
                        particleList.get(index).radius, mPaint);
            }else{
                mPaint.setAlpha(255);
            }
            canvas.drawCircle(particleList.get(index).x, particleList.get(index).y,
                    particleList.get(index).radius, mPaint);
            index++;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = (float)(w / 2);
        mCenterY = (float)(h / 2);
        mRandom = new Random();
        mCirclePath.addCircle(mCenterX, mCenterY, diffusionRadius, Path.Direction.CCW);
        mPathMeasure.setPath(mCirclePath, false);
        // 添加多个粒子
        int count = 0;
        while(count <= particleNumber){
            mPathMeasure.getPosTan((count / (float)particleNumber) * mPathMeasure.getLength(), pos, tan);
            int offset = mRandom.nextInt(200);
            float nextX = 0f;
            float nextY = 0f;
            float speed = mRandom.nextInt(2) + 0.5f; // 粒子变化速度随机5-15不等
            float randomX = mRandom.nextInt(6) - 3f;
            float randomY = mRandom.nextInt(6) - 3f;
            int offSetX = mRandom.nextInt(3);
            float direction = mRandom.nextInt(3) - 1.5f;
            double angle = Math.acos((pos[0] - mCenterX) / diffusionRadius);
            float maxOffset = mRandom.nextInt(250) + 0f;
            // 按比例测量路径上每一点的值
            nextX = pos[0] + randomX; // X值随机偏移
            nextY = pos[1] + randomY; // Y值随机偏移
            particleList.add(new Particle(
                    nextX,
                    nextY,
                    particleRadius,
                    (float)offSetX,
                    (float)offset,
                    direction,
                    speed,
                    angle,
                    maxOffset));
            count++;
        }
        // 启动粒子动画
        mAnimator.start();
    }

    /*
    *   更新粒子动画状态
    * */
    private void updateParticle(){
        int i = 0;
        while(i < particleList.size()){
            if(particleList.get(i).offSet > particleList.get(i).maxOffset){ // 大于最大距离就重置
                particleList.get(i).offSet = 0.0f;
                float speed = mRandom.nextInt(3) + 1.5f;
                float maxOffset = (float)(mRandom.nextInt(250));
                particleList.get(i).speed = speed;
                particleList.get(i).maxOffset =maxOffset;
            }

            float temp1 = (float) Math.cos(particleList.get(i).angle) * (diffusionRadius + particleList.get(i).offSet);
            float temp2 = particleList.get(i).offSetX * particleList.get(i).direction;
            particleList.get(i).x = mCenterX + temp1 + temp2;

            float temp3 = (float) Math.sin(particleList.get(i).angle) * (diffusionRadius + particleList.get(i).offSet);
            if(particleList.get(i).y > mCenterY){
                particleList.get(i).y = temp3 + mCenterY;
            }else{
                particleList.get(i).y = mCenterY - temp3;
            }
            particleList.get(i).offSet += particleList.get(i).speed;
            i++;
        }
    }
}
