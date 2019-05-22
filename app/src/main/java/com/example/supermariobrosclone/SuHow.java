package com.example.supermariobrosclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class SuHow extends View {

    Bitmap hsuHao;

    public SuHow(Context context){
        super(context);
        hsuHao = BitmapFactory.decodeFile("drawable-v24/hsuhao.png");
    }

    @Override
    protected void  onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(hsuHao, canvas.getWidth()/2, canvas.getHeight()/2,null);
    }
}
