package com.example.supermariobrosclone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class Tileset {

    Bitmap bMap;
    private int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int size = sWidth/16;
    Paint paint;
    Rect rectangle = new Rect();
    int blockType = 0;


    public void setBlockType(){
        if(blockType == 0){
            paint.setColor(Color.BLUE);
        }
        else if (blockType == 1){
            paint.setColor(Color.GREEN);
        }
    }

}
