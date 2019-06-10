package com.example.supermariobrosclone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Consumable implements GameObject {
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Context context;
    private Rect cRect;
    private Bitmap bitmap;
    private Paint paint;
    private int sizeRect = sHeight/14;
    /*Goomba moves left when movePattern is false, Goomba moves right when movePattern is true;*/
    private boolean movePattern;
    private int lastMove;
    private boolean isActive;

    public Consumable(Context context) {
        this.cRect = new Rect();
        this.paint = new Paint();
        this.context = context;
        this.movePattern = false;
        this.isActive = false;
    }
    public Consumable(Bitmap bitmap, Context context) {
        this.cRect = new Rect();
        this.paint = new Paint();
        this.bitmap = bitmap;
        this.context = context;
        this.movePattern = false;
        this.isActive = false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(cRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,cRect,null);
        }

    }
    @Override
    public void update(Canvas canvas){}

    public Rect returnRect(){
        return this.cRect;
    }

    public void resetGoomba(){
        setPosition(0,0);
    }

    public void setPosition(int x, int y){
        this.cRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPosition(float x, float y){
        this.cRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
    }

    public int returnLastMove(){
        return this.lastMove;
    }
    public void setLastMove(int l){
        this.lastMove = l;
    }

    public void moveRight(){
        this.cRect.offset(1,0);

    }

    public void moveLeft(){
        this.cRect.offset(-1,0);

    }

    public void moveUp(){
        this.cRect.offset(0,-10);

    }

    public void moveDown(){
        this.cRect.offset(0,10);

    }

    public void borderCollision(){
        if(this.cRect.left < 0){
            setMovePattern(true);
        }
        else if(this.cRect.right > sWidth){
            setMovePattern(false);
        }
    }

    public void movement(){
        if(movePattern){
            moveRight();
        }
        else{
            moveLeft();
        }
    }

    public void setMovePattern(boolean b){
        this.movePattern = b;
    }
}
