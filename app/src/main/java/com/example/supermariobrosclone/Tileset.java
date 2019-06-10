package com.example.supermariobrosclone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class Tileset implements GameObject {
    private Rect rect;
    private Paint paint;
    boolean collideable;
    Bitmap bitmap;
    boolean draw = false;
    int blockType;

    public Tileset(){
        this.rect = new Rect();
        this.paint = new Paint();
        this.draw = false;
        this.blockType =0;
    }
    public Tileset(Bitmap b){
        this.rect = new Rect();
        this.paint = new Paint();
        this.draw = false;
        this.bitmap = b;
        this.blockType =0;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect,paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,rect,null);
        }
    }

    @Override
    public void update(Canvas canvas){

    }

    public Paint returnPaint(){
        return this.paint;
    }

    public Rect returnRect(){
        return this.rect;
    }

    public int returnBlockType(){
        return this.blockType;
    }

    public Bitmap returnBitmap(){
        return this.bitmap;
    }

    public void setBitmap(Bitmap b){
        this.bitmap = b;
    }

    public void setCollideable(boolean b){
        this.collideable = b;
    }

    public void setDraw(boolean b){this.draw = b;}

    public boolean isCollideable(){
        return this.collideable;
    }

    public boolean isDraw(){
        return this.draw;
    }

    public void setType(int t){this.blockType = t;}

    public int returnType(){
        return this.blockType;
    }
}
