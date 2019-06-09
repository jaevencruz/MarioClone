package com.example.supermariobrosclone;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.MediaPlayer;
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
    boolean button = false;

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


        /*findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        v.mario.moveRight();

            }
        });*/

        findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    v.mario.moveRight();
                    v.mario.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.smallmario));
                }catch(Exception e){}
            }
        });


        findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    v.mario.moveLeft();
                    v.mario.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.smallmariobackwards));
            }
        });

        findViewById(R.id.btn_move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0 ; i < 25; i++) {
                    /*if(Rect.intersects(v.mario.returnRect(),v.r)){
                        break;
                    }*/
                    v.mario.moveUp();
                }
            }
        });




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

    public boolean onTouch(View s, MotionEvent me){
        this.v.setX(me.getX());
        this.v.setY(me.getY());
        this.v.mario.setPosition(me.getX(),me.getY());
        switch(me.getAction()){
            case MotionEvent.ACTION_BUTTON_PRESS:
                findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        v.mario.moveRight();
                    }
                });


                findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        v.mario.moveLeft();
                    }
                });

                findViewById(R.id.btn_move_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0 ; i < 20; i++) {
                            for(int j = 0; j < 100; j++){
                                for(int k = 0; k <12; k++){
                                    if(Rect.intersects(v.mario.returnRect(),v.tilesets[j][k].returnRect()) && v.levelarray[j][k] != null){
                                        break;
                                    }
                                }
                            }

                            v.mario.moveUp();
                        }
                    }
                });
                break;
             case MotionEvent.ACTION_BUTTON_RELEASE:
                 break;
        }
            /*if (me.getX() > Resources.getSystem().getDisplayMetrics().widthPixels / 2) {
                for (int i = 0; i < 10; i++) {
                    this.v.mario.moveRight();
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    this.v.mario.moveLeft();
                }
            }*/
        v.invalidate();
        return false;
    }


}
