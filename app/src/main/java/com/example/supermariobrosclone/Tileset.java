package com.example.supermariobrosclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Tileset extends View {

    Bitmap bMap;
    Bitmap[] tSet;

    public Tileset(Context context){
        super(context);

        init(null);
    }


    public Tileset(Context context, AttributeSet attrs){
        super(context,attrs);
        init(attrs);
    }

    public Tileset(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    public Tileset(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context,attrs,defStyleAttr,defStyleRes);
    }

    public Bitmap[] returnTileSet(){
        return this.tSet;
    }


    private void init(@Nullable AttributeSet set){
        bMap = BitmapFactory.decodeFile("drawable-v24/hsuhao.png");
        tSet = new Bitmap[10];
        for(int i = 0; i< tSet.length;i++){
            //tSet[i] = Bitmap.createBitmap();
            tSet[i] = bMap;
        }
    }

    @Override
    protected void  onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bMap, getWidth()/2, getHeight()/2,null);
        for(int i = 0; i < tSet.length; i++){
            canvas.drawBitmap(tSet[i], i*(50) + 1,getHeight()/2,null);
        }
    }

    public void  draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(bMap, canvas.getWidth()/2, canvas.getHeight()/2,null);
        for(int i = 0; i < tSet.length; i++){
            canvas.drawBitmap(tSet[i], i*(50) + 1,canvas.getHeight()/2,null);
        }
    }
}
