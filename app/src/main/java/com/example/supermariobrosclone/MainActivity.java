package com.example.supermariobrosclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    String fname;
    GameView v;
    Bitmap ball;
    float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        try{
            fname = new File(getFilesDir(),"drawable/ballosteel.png").getAbsolutePath();
        }catch(Exception e){
            ball = BitmapFactory.decodeFile("C:\\Users\\jaeve\\AndroidStudioProjects\\SuperMarioBrosClone\\app\\src\\main\\res\\drawable\\ballosteel.png");
        }

        ball = BitmapFactory.decodeFile(fname);
        x = y = 0;
        v = new GameView(this);
        v.setOnTouchListener(this);
        setContentView(R.layout.activity_main);


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
        System.out.println("Nani?!");
        return false;
    }

}
