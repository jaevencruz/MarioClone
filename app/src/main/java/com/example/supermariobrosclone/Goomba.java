package com.example.supermariobrosclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Goomba implements GameObject {
    private Context context;
    private Rect gRect;
    private Bitmap bitmap;
    private Paint paint;
    private int sizeRect = 80;
    private boolean movePattern;

    public Goomba(Context context) {
        this.gRect = new Rect();
        this.paint.setColor(Color.RED);
        this.context = context;
        this.movePattern = false;
    }
    public Goomba(Bitmap bitmap, Context context) {
        this.gRect = new Rect();
        this.paint.setColor(Color.RED);
        this.bitmap = bitmap;
        this.context = context;
        this.movePattern = false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(gRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,gRect,paint);
        }

    }
    @Override
    public void update(Canvas canvas){}


    public void resetGoomba(){
        setPosition(0,0);
    }

    public void setPosition(int x, int y){
        this.gRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPosition(float x, float y){
        this.gRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
    }

    public void moveRight(){
        this.gRect.offset(10,0);

    }

    public void moveLeft(){

        this.gRect.offset(-10,0);

    }

    public void moveUp(){
        this.gRect.offset(0,-10);

    }

    public void moveDown(){
        this.gRect.offset(0,10);

    }

    public void MovePattern(){
        if(movePattern){
            moveLeft();
        }
        else{
            moveRight();
        }
    }
}
