package com.example.supermariobrosclone;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    static int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    Thread t = null;
    SurfaceHolder holder;
    boolean running = false;
    Bitmap bMap;
    float x,y,bMapWidth,bMapHeight;
    Paint paint = new Paint();
    int numHolder = 0;

    public GameView(Context context){
        super(context);
        holder = getHolder();
        paint.setColor(Color.RED);
    }

    public GameView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
    }

    public GameView(Context context, Bitmap b, float x, float y){
        super(context);
        this.bMap = b;
        this.x = x;
        this.y = y;
        if(bMap == null){
            bMapHeight = bMapWidth = 0;
        }
        else{
            bMapWidth = (bMap.getWidth()/2);
            bMapHeight = (bMap.getHeight()/2);
        }
        paint.setColor(Color.RED);
        holder = getHolder();
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void surfaceCreated(SurfaceHolder holder){
        Canvas c = getHolder().lockCanvas();
        draw(c);
        getHolder().unlockCanvasAndPost(c);
    }
    public void surfaceDestroyed(SurfaceHolder holder){

    }
    public void surfaceChanged(SurfaceHolder holder, int x, int y, int z){}

    public void run(){
        while (running){
            if(!holder.getSurface().isValid()){
                continue;
            }
            Canvas c = holder.lockCanvas();
            //c.drawARGB(0,150,150,10);
            //c.drawBitmap(bMap,x,y,null);
            c.drawRect(x - 50, y - 50, x+50, y+50,paint);
            squareBounder();
            squareMover();
            holder.unlockCanvasAndPost(c);
        }
    }

    public void pause(){
        running = false;
        while(true){
            try{
                t.join();
                t = null;
                break;
            }catch(Exception e){e.printStackTrace();}
        }
    }
    public void resume(){
        running = true;
        t = new Thread(this);
        t.start();
    }

    private void squareBounder(){
        if(x - 50 < 0 || y - 50 < 0){
            this.numHolder = 0;
        }
        else if(x+50 > sWidth){
            this.numHolder = 1;
        }
        else if(y + 50 > sHeight){
            this.numHolder = 2;
        }
        else if(y + 50 > sHeight || x + 50 > sWidth){
            this.numHolder = 3;
        }
        else{
            return;
        }
    }

    private void squareMover(){
        if(numHolder == 0){
            for(int i = 0; i < 5; i++) {
                x++;
            }
            for(int i = 0; i < 5; i++) {
                y++;
            }
        }
        else if(numHolder == 1){
            for(int i = 0; i < 5; i++) {
                x--;
            }
            for(int i = 0; i < 5; i++) {
                y++;
            }
        }
        else if(numHolder == 2) {
            for (int i = 0; i < 5; i++) {
                x++;
            }
            for(int i = 0; i < 5; i++) {
                y--;
            }
        }
        else if(numHolder == 3) {
            for(int i = 0; i < 5; i++) {
                x--;
            }
            for(int i = 0; i < 5; i++) {
                y--;
            }
        }
    }
}
