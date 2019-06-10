package com.example.supermariobrosclone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Goomba implements GameObject {
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Context context;
    private Rect gRect;
    private Bitmap bitmap;
    private Paint paint;
    private int sizeRect = sHeight/14;
    /*Goomba moves left when movePattern is false, Goomba moves right when movePattern is true;*/
    private boolean movePattern;
    private int lastMove;
    private boolean isAlive;

    public Goomba(Context context) {
        this.gRect = new Rect();
        this.paint = new Paint();
        this.context = context;
        this.movePattern = false;
        this.isAlive = true;
    }
    public Goomba(Bitmap bitmap, Context context) {
        this.gRect = new Rect();
        this.paint = new Paint();
        this.bitmap = bitmap;
        this.context = context;
        this.movePattern = false;
        this.isAlive = true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(gRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,gRect,null);
        }

    }
    @Override
    public void update(Canvas canvas){}

    public Rect returnRect(){
        return this.gRect;
    }

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

    public int returnLastMove(){
        return this.lastMove;
    }
    public void setLastMove(int l){
        this.lastMove = l;
    }

    public void moveRight(){
        this.gRect.offset(1,0);
        this.lastMove = 1;
    }

    public void moveLeft(){
        this.gRect.offset(-1,0);
        this.lastMove = 3;
    }

    public void moveUp(){
        this.gRect.offset(0,-10);

    }

    public void moveDown(){
        this.gRect.offset(0,10);

    }

    public void borderCollision(){
        if(this.gRect.left < 0){
            setMovePattern(true);
        }
        else if(this.gRect.right > sWidth){
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
