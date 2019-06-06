package com.example.supermariobrosclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class maploader{

    public void bmap(){
        int color, red, green, blue;
        Bitmap b = BitmapFactory.decodeFile("/drawable/pixelmap1.png");
        for(int i = 0; i < 100; i++){
            for(int j = 0; i < 12; j++){
                color = b.getPixel(i,j);
                red = Color.red(color);
                blue = Color.blue(color);
                green = Color.green(color);

            }
        }
    }

}
