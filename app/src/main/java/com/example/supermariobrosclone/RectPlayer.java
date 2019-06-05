package com.example.supermariobrosclone;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectPlayer implements GameObject {
    private Rect playerRect;
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int sizeRect = 100;
    private int sizeRectWidth = sizeRect;
    private int sizeRectHeight = sizeRect;
    private Paint paint = new Paint();
    private int x,y;
    private Bitmap bitmap;

    public RectPlayer() {
        this.playerRect = new Rect();
        this.paint.setColor(Color.RED);
    }
    public RectPlayer(Bitmap bitmap) {
        this.playerRect = new Rect();
        this.paint.setColor(Color.RED);
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(playerRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,playerRect,paint);
        }

    }

    public Rect returnRect(){
        return this.playerRect;
    }

    public int returnX(){
        return this.x;
    }

    public int returnY(){
        return this.y;
    }

    public int returnPlayerHeight(){
        return this.sizeRectHeight;
    }

    public int returnPlayerWidth(){
        return this.sizeRectWidth;
    }

    public void setPlayerWidth(int width){
        this.sizeRectWidth = width;
    }

    public void setPlayerHeight(int height){
        this.sizeRectWidth = height;
    }

    public void resetPlayer(){
        setPosition(0,0);
    }

    public void setPosition(int x, int y){
        this.playerRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));
        this.x = x;
        this.y = y;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPosition(float x, float y){
        this.playerRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
        this.x = (int)x;
        this.y = (int)y;
    }

    public void moveRight(){
        this.playerRect.offset(10,0);
        this.x++;
    }

    public void moveLeft(){
        this.playerRect.offset(-10,0);
        this.x--;
    }

    public void moveUp(){
        this.playerRect.offset(0,1);
        this.y++;
    }

    public void moveDown(){
        this.playerRect.offset(0,-1);
        this.y--;
    }

}
