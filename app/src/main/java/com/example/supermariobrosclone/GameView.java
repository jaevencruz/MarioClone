package com.example.supermariobrosclone;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    static int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    Thread t = null;
    boolean running = false;
    Bitmap bMap = decodeSampledBitmapFromResource(getResources(),R.drawable.hsuhao, 100,100);
    float x,y,bMapWidth,bMapHeight;
    Paint paint = new Paint();
    int numHolder = 0;
    RectPlayer mario = new RectPlayer();
    Rect mBlock = mario.returnRect();



    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
    }

    public GameView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
    }


    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        System.out.println("sWidth is : "+sWidth+" and sHeight is : "+sHeight);
        mario.setPosition(sWidth/2,sHeight/2);
        running = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int x, int y, int z){
    }

    public void run(){
        while (running){
            synchronized (getHolder()){
                if(!getHolder().getSurface().isValid()){
                    continue;
                }
                Rect r = new Rect(100,100,400,400);
                Canvas c = getHolder().lockCanvas();
                //c.drawRect(x - 50, y - 50, x+50, y+50,paint);
                //squareBounder();

                c.drawRect(mBlock,paint);
                c.drawRect(r, paint);
                c.drawBitmap(bMap,r,r,paint);
                bmap(c);
                invalidate();
                getHolder().unlockCanvasAndPost(c);
            }
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
        blockside = sHeight/16;
        Bitmap blk;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pixelmap3);
        for(int i = 0; i < 24; i++){
            for(int j = 0; j < 12; j++){
                color = b.getPixel(i,j);
                red = Color.red(color);
                blue = Color.blue(color);
                green = Color.green(color);
                blk = block(red,blue,green,i,j);
                canvas.drawBitmap(blk, i*blockside, j * blockside,null);
                System.out.println("Printed a box \n");
            }
        }

    }
    public Bitmap block(int r, int b, int g, int x, int y){
        int blockside = sHeight/16;
        Bitmap blk = null;
        blk = BitmapFactory.decodeResource(getResources(), R.drawable.skyblu);
        blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        if(r == 0 && g ==0 && b==0)
        {
            //spawn a ground block if pixel is black
            System.out.println("Groundblock printed at" + x + "and" + y);
            blk = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if (r==255 && g == 0 && b ==0){
            //spawn a breakable brick if pixel is red
            blk = BitmapFactory.decodeResource(getResources(), R.drawable.brickblock1);
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if(r == 0 && g == 0 && b==255){
            //spawn mario here if pixel is blue
        }
        else if(r ==255 && g==255 && b==255){
            blk = BitmapFactory.decodeResource(getResources(), R.drawable.skyblu);
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        return blk;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
