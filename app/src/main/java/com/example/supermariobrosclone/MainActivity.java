package com.example.supermariobrosclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    SurfaceHolder transparentV;
    GameView v;
    float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        v = new GameView(this.getBaseContext());
        v = findViewById(R.id.gameView);
        v.setOnTouchListener(this);
        //v.setZOrderOnTop(true);
        //transparentV = v.getHolder();
        //transparentV.setFormat(PixelFormat.TRANSPARENT);



    }

    @Override
    protected void onPause(){
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        v.resume();
    }

    public boolean onTouch(View v, MotionEvent me){
        this.v.setX(me.getX());
        this.v.setY(me.getY());
        this.v.mario.setPosition(me.getX(),me.getY());
        System.out.println("Nani?!");

        return false;
    }


}
