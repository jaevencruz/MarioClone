package com.example.supermariobrosclone;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    RectPlayer mario = new RectPlayer();

    public GameView(Context context){
        super(context);
        holder = getHolder();
        paint.setColor(Color.RED);
    }

    public GameView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        holder = getHolder();
        paint.setColor(Color.RED);
    }


    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void surfaceCreated(SurfaceHolder holder){
        System.out.println("sWidth is : "+sWidth+" and sHeight is : "+sHeight);
        this.holder = holder;
        mario.setPosition(sWidth/2,sHeight/2);
        running = true;
    }
    public void surfaceDestroyed(SurfaceHolder holder){
        this.holder = holder;
    }
    public void surfaceChanged(SurfaceHolder holder, int x, int y, int z){
        this.holder = holder;
    }

    public void run(){
        while (running){
            synchronized (holder){
                if(!holder.getSurface().isValid()){
                    continue;
                }
                Canvas c = holder.lockCanvas();
                //c.drawRect(x - 50, y - 50, x+50, y+50,paint);
                //squareBounder();
                mario.draw(c);
                c.drawRect(100, 100, 200, 200, paint);
                holder.unlockCanvasAndPost(c);
            }
            if(!holder.getSurface().isValid()){
                continue;
            }
            Canvas c = holder.lockCanvas();
            //c.drawARGB(0,150,150,10);
            //c.drawBitmap(bMap,x,y,null);
            c.drawRect(x - 50, y - 50, x+50, y+50,paint);
            //draws map here?
            bmap(c);

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
    public void bmap(Canvas canvas){
        int color, red, green, blue, blockside;

        Bitmap blk;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pixelmap1);
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                color = b.getPixel(i,j);
                red = Color.red(color);
                blue = Color.blue(color);
                green = Color.green(color);
                blk = block(red,blue,green,i,j);
                canvas.drawBitmap(blk, i*12, j * 12,null);
                System.out.println("Printed a box \n");
            }
        }

    }
    public Bitmap block(int r, int b, int g, int x, int y){
        int blockside = sWidth/12;
        Bitmap blk = null;
        if(r == 0 && g ==0 && b==0)
        {
            //spawn a ground block if pixel is black
            System.out.println("Groundblock printed");
            blk = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
            Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if (r==255 && g == 0 && b ==0){
            //spawn a breakable brick if pixel is red
            blk = BitmapFactory.decodeResource(getResources(), R.drawable.brickblock1);
            Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if(r == 0 && g == 0 && b==255){
            //spawn mario here if pixel is blue
        }
        return blk;
    }
}
