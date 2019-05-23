package com.example.supermariobrosclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class Tileset extends View {

    Bitmap bMap;
    Bitmap[] tSet;

    public Tileset(Context context){
        super(context);
        bMap = BitmapFactory.decodeFile("drawable-v24/hsuhao.png");
        tSet = new Bitmap[10];
        for(int i = 0; i< tSet.length;i++){
            tSet[i] = bMap;
        }
    }

    @Override
    protected void  onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bMap, canvas.getWidth()/2, canvas.getHeight()/2,null);
        for(int i = 0; i < tSet.length; i++){
            canvas.drawBitmap(tSet[i], i*(50) + 1,canvas.getHeight()/2,null);
        }
    }

    public void  draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bMap, canvas.getWidth()/2, canvas.getHeight()/2,null);
        for(int i = 0; i < tSet.length; i++){
            canvas.drawBitmap(tSet[i], i*(50) + 1,canvas.getHeight()/2,null);
        }
    }
}
