package com.example.supermariobrosclone;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Goomba implements GameObject {
    Bitmap goombaImg;

    @Override
    public void draw(Canvas canvas) {

    }
    @Override
    public void update(Canvas canvas){}

    public Goomba(){

    }



    public void resetGoomba(){
        setPosition(0,0);
    }

    public void setPosition(int x, int y){

    }

    public void setPosition(float x, float y){

    }

    public void moveRight(){

    }

    public void moveLeft(){

    }

    public void moveUp(){

    }

    public void moveDown(){

    }
}
