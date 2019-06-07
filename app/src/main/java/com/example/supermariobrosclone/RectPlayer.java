package com.example.supermariobrosclone;

import android.content.ContentQueryMap;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectPlayer implements GameObject {
    private Context context;
    private Rect playerRect;
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int sizeRect = 80;
    private int sizeRectWidth = sizeRect;
    private int sizeRectHeight = sizeRect;
    private Paint paint = new Paint();
    private Bitmap bitmap;
    private int lastMove;
    private boolean isOnBlock = false;

    public RectPlayer(Context context) {
        this.playerRect = new Rect();
        this.paint.setColor(Color.RED);
        this.context = context;
    }
    public RectPlayer(Bitmap bitmap, Context context) {
        this.playerRect = new Rect();
        this.paint.setColor(Color.RED);
        this.bitmap = bitmap;
        this.context = context;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(playerRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,playerRect,paint);
        }

    }

    @Override
    public void update(Canvas canvas){

    }

    public Rect returnRect(){
        return this.playerRect;
    }

    public int returnX(){
        return (int) this.playerRect.exactCenterX();
    }

    public int returnY(){
        return (int) this.playerRect.exactCenterY();
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

    public void setOnTopBlock(boolean b){
        this.isOnBlock = b;
    }

    public boolean returnIsOnBlock(){
        return this.isOnBlock;
    }


    public int returnLastMove(){
        return this.lastMove;
    }

    public void setPosition(int x, int y){
        this.playerRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));

    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPosition(float x, float y){
        this.playerRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
    }

    public void moveRight(){
        this.playerRect.offset(10,0);
        this.lastMove = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.smallmario);
    }

    public void moveLeft(){
        this.playerRect.offset(-10,0);
        this.lastMove = 3;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.smallmariobackwards);
    }

    public void moveUp(){
        this.playerRect.offset(0,-10);
        this.lastMove = 0;
    }

    public void moveDown(){
        this.playerRect.offset(0,10);
        this.lastMove = 2;

    }

}
