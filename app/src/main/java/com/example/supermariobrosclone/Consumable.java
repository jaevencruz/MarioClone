package com.example.supermariobrosclone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Consumable implements GameObject {
    private Context context;
    private Rect objectRect;
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int sizeRect = 100;
    private Paint paint = new Paint();
    private int x,y;
    private Bitmap bitmap;
    private int lastMove;
    private boolean isOnBlock = false;
    boolean state = false;  //0 means don't draw, 1 means draw

    public Consumable(Context context) {
        this.objectRect = new Rect();
        this.paint.setColor(Color.WHITE);
        this.context = context;
    }
    public Consumable(Bitmap bitmap, Context context) {
        this.objectRect = new Rect();
        this.paint.setColor(Color.WHITE);
        this.bitmap = bitmap;
        this.context = context;
    }

    @Override
    public void draw(Canvas canvas) {
        if(state){

        }
    }

    @Override
    public void update(Canvas canvas){}

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
