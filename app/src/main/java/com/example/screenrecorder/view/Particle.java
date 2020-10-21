package com.example.screenrecorder.view;

/*
*   粒子
* */

public class Particle {
    float x; // X坐标
    float y; // Y坐标
    float radius; //半径
    float offSetX; // 粒子移动的X值
    float offSet; // 粒子移动路径
    float direction;
    float speed; // 速度
    double angle; // 粒子角度
    float maxOffset; // 粒子最大移动距离

    Particle(float x, float y, float radius, float offSetX, float offSet, float direction, float speed, double angle, float maxOffset){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.offSetX = offSetX;
        this.offSet = offSet;
        this.direction = direction;
        this.speed = speed;
        this.angle = angle;
        this.maxOffset = maxOffset;
    }
}
