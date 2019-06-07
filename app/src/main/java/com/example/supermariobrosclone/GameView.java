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
    static boolean start = true;
    int blockside = sHeight/14;
    static int cameraleft = 0;
    Bitmap bMap = decodeSampledBitmapFromResource(getResources(),R.drawable.brickblock1, 100,100);
    float x,y,bMapWidth,bMapHeight;
    Paint paint = new Paint();
    int numHolder = 0;
    RectPlayer mario = new RectPlayer(decodeSampledBitmapFromResource(getResources(),R.drawable.smallmario,100,100), this.getContext());
    Rect mBlock = mario.returnRect();
    Rect r = new Rect(700,400,800,500);
    Bitmap levelarray[][] = new Bitmap[100][12];



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
        mario.setPosition(sWidth/2 -200,100);
        //bmap();
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
            Canvas c = null;
            synchronized (getHolder()){
                if(!getHolder().getSurface().isValid()){
                    continue;
                }
                c = getHolder().lockCanvas();
                c.drawColor(Color.WHITE);
                frameShift(mario, r);
                //c.drawRect(x - 50, y - 50, x+50, y+50,paint);
                //squareBounder();

                c.drawRect(mBlock,paint);
                c.drawRect(r, paint);
                c.drawBitmap(bMap,null,r,paint);
                marioCollideRect(mario,r,paint);

                //this loop takes the level array and prints accordingly to mario's current position
                /*for(int x = cameraleft; x<(24+cameraleft); x++){
                    for(int y = 0; y<12;y++){
                        if(levelarray[x][y]!=null) {
                            c.drawBitmap(levelarray[x][y], x * blockside, y * blockside, null);
                        }
                    }
                }*/
                //



                marioGravity(mario,r);
                mario.draw(c);
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

    private void marioCollideRect(RectPlayer m, Rect r, Paint p){
        //Mario runs into a block from the right
        if( m.returnLastMove() == 1 && Rect.intersects(m.returnRect(),r)){
                m.moveLeft();
        }
        else if( m.returnLastMove() == 3 && Rect.intersects(m.returnRect(),r)){
            m.moveRight();
        }
        else if(m.returnLastMove() == 0 && Rect.intersects(m.returnRect(),r)){
            r.offset(-5000,-10);
            p.setColor(Color.TRANSPARENT);
        }
    }

    public void marioGravity(RectPlayer m,Rect r){
        if(m.returnRect().bottom + 1 > r.top  && m.returnRect().left < r.right && m.returnRect().right > r.left  && m.returnRect().top < r.top){

            //m.setOnTopBlock(true);
        }
        else if((m.returnRect().centerY() < sHeight - 300) /**&& mario.returnIsOnBlock()**/){
            m.moveDown();
            m.setOnTopBlock(false);
        }
    }

    public void frameShift(RectPlayer m, Rect r){
        if(m.returnRect().centerX() > sWidth/2){
            m.moveLeft();
            m.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.smallmario));
            r.offset(-10,0);
            if(cameraleft +24 < 100) {
                cameraleft++;
            }
        }
    }

    //public void canvas
    public void bmap(){
        int color, red, green, blue, blockside;
        blockside = sHeight/14;
        Bitmap blk;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pixelmap7);
        b = Bitmap.createScaledBitmap(b, 100, 100, false);

        System.out.println("The width is: " + b.getWidth() + "\n");
        System.out.println("The height is: " + b.getHeight() + "\n");
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 12; j++){
                color = b.getPixel(i,j);
                red = Color.red(color);
                blue = Color.blue(color);
                green = Color.green(color);
                blk = block(red,blue,green,i,j);
                levelarray[i][j]= blk;
            }
        }

    }
    public Bitmap block(int r, int b, int g, int x, int y){
        int blockside = sHeight/14;
        Bitmap blk, ground, sky, brick, question;
        blk = null;
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
        brick = BitmapFactory.decodeResource(getResources(), R.drawable.brickblock1);
        sky = BitmapFactory.decodeResource(getResources(), R.drawable.skyblu);
        question = BitmapFactory.decodeResource(getResources(), R.drawable.question);

        if(r == 0 && g ==0 && b==0)
        {
            //spawn a ground block if pixel is black
            System.out.println("Groundblock printed at " + x + "and " + y);
            blk = ground;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if (r==255 && g == 0 && b ==0){
            //spawn a breakable brick if pixel is red
            blk = brick;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
        }
        else if(r == 0 && g == 0 && b==255){
            //spawn mario here if pixel is blue
        }
        else if(r ==255 && g==255 && b==255){
            blk = null;
        }
        else if(r ==255 && g==255 && b==0){
            blk = question;
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
