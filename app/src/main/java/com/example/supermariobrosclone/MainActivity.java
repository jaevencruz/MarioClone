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

        //v = new GameView(this.getBaseContext());
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
                    if(v.playerControl) {
                        v.mario.moveRight();
                        v.mario.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.smallmarioright));
                        v.mario.setLastMove(1);
                    }
            }
        });


        findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v.playerControl) {
                    v.mario.moveLeft();
                    v.mario.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.smallmarioleft));
                    v.mario.setLastMove(3);
                }
            }
        });

        findViewById(R.id.btn_move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(v.playerControl) {
                    v.jmpUp(v.mario);
                    v.mario.setLastMove(0);
                }
            }
        });
        findViewById(R.id.btn_rt_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(v.playerControl) {
                    v.jmpUp(v.mario);
                    for (int i = 0; i < 20; i++) {
                        v.mario.moveRight();
                    }
                    v.mario.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.smallmarioright));
                    v.mario.setLastMove(1);
                }
            }
        });
        findViewById(R.id.btn_lft_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(v.playerControl) {
                    v.jmpUp(v.mario);
                    for (int i = 0; i < 20; i++) {
                        v.mario.moveLeft();
                    }
                    v.mario.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.smallmarioleft));
                    v.mario.setLastMove(3);
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
        this.v.mario.setPosition(me.getX(),me.getY());
        if(v.gameOverState){
            v.gameOverState = false;
        }
        v.invalidate();
        return false;
    }


}
