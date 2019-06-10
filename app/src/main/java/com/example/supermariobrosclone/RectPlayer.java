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
    private int sizeRect = sHeight/14;
    private int sizeRectWidth = sizeRect;
    private int sizeRectHeight = sizeRect;
    private Paint paint = new Paint();
    private Bitmap bitmap;
    private int lastMove;
    private boolean isOnBlock = false;
    private int marioState; //0 = small mario, 1 = big mario, 2 = small invincible mario, 3 = big invincible mario, 4 = dead mario

    public RectPlayer(Context context) {
        this.playerRect = new Rect();
        this.context = context;
        marioState = 0;
    }
    public RectPlayer(Bitmap bitmap, Context context) {
        this.playerRect = new Rect();
        this.bitmap = bitmap;
        this.context = context;
        marioState = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(playerRect, paint);
        if (bitmap != null) {
            canvas.drawBitmap(this.bitmap,null,playerRect,null);
        }

    }

    @Override
    public void update(Canvas canvas){

    }

    public Rect returnRect(){
        return this.playerRect;
    }

    public int returnMarioState(){
        return this.marioState;
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

    public void setMarioState(int i){
        this.marioState = i;
    }


    public int returnLastMove(){
        return this.lastMove;
    }
    public void setLastMove(int l){
        this.lastMove = l;
    }

    public void setPosition(int x, int y){
        this.playerRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));
    }

    public void makeBig(Bitmap b){
        this.playerRect.set(this.playerRect.left, (this.playerRect.top + this.playerRect.height()), this.playerRect.right,this.playerRect.bottom );
        this.bitmap = b;
    }

    public void makeSmall(Bitmap b){
        this.playerRect.set(this.playerRect.left, (this.playerRect.top - this.playerRect.height()), this.playerRect.right,this.playerRect.bottom );
        this.bitmap = b;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPosition(float x, float y){
        this.playerRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
    }

    public void moveRight(){
        if(this.playerRect.right+1 > sWidth){
            return;
        }
        this.playerRect.offset(10,0);
        this.lastMove = 1;

    }

    public void moveLeft(){
        if(this.playerRect.left-1 < 0){
            return;
        }
        this.playerRect.offset(-10,0);
        this.lastMove = 3;
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
