package com.example.supermariobrosclone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectPlayer implements GameObject {
    private Rect playerRect;
    private final int sizeRect = 100;
    private Paint paint = new Paint();
    private int x,y;

    public RectPlayer() {
        this.playerRect = new Rect();
        this.paint.setColor(Color.BLUE);
    }

    public void draw(Canvas canvas){
        if(playerRect == null){
            System.out.println("Help");
        }
        canvas.drawRect(playerRect,paint);
    }

    public int returnX(){
        return this.x;
    }

    public int returnY(){
        return this.x;
    }

    public void resetPlayer(){
        setPosition(0,0);
    }

    public void setPosition(int x, int y){
        this.playerRect.set(x-(sizeRect/2),y-(sizeRect/2),x+(sizeRect/2),y+(sizeRect/2));
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y){
        this.playerRect.set((int)x-(sizeRect/2),(int)y-(sizeRect/2),(int)x+(sizeRect/2),(int)y+(sizeRect/2));
        this.x = (int)x;
        this.y = (int)y;
    }

    public void moveRight(){
        this.playerRect.offset(1,0);
        this.x++;
    }

    public void moveLeft(){
        this.playerRect.offset(-1,0);
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
