package com.example.supermariobrosclone;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Consumable implements GameObject {
    private Bitmap goombaImg;
    boolean state = false;  //0 means don't draw, 1 means draw

    @Override
    public void draw(Canvas canvas) {
        if(state){

        }
    }

    public Consumable(){

    }

    public void setState(boolean state){
        this.state = state;
    }

    public void resetPlayer(){
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
