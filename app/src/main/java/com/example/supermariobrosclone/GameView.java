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
    int score = 0;
    int lives = 3;
    static int cameraleft = 0;
    Bitmap bMap = decodeSampledBitmapFromResource(getResources(),R.drawable.brickblock1, 100,100);
    float x,y;
    Paint paint = new Paint();
    Goomba goombaone = new Goomba(decodeSampledBitmapFromResource(getResources(),R.drawable.goombaleft,100,100),this.getContext());
    RectPlayer mario = new RectPlayer(decodeSampledBitmapFromResource(getResources(),R.drawable.smallmario,100,100), this.getContext());
    Bitmap levelarray[][] = new Bitmap[100][12];
    Tileset tilesets[][] = new Tileset[100][12];



    public GameView(Context context){
        super(context);
        init(context);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
    }

    public GameView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
    }


    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    private void init(Context context){
        mario.setPosition(sWidth/14,100);
        goombaone.setPosition(3*(sWidth/4),100);
        bmap();
        running = true;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){

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
            if(lives == 0){
                System.out.println("Game Over");
            }
            synchronized (getHolder()){
                if(!getHolder().getSurface().isValid()){
                    continue;
                }
                c = getHolder().lockCanvas();
                c.drawColor(Color.CYAN);
                frameShift(mario, tilesets);
                //c.drawRect(x - 50, y - 50, x+50, y+50,paint);
                //squareBounder();

                //this loop takes the level array and prints accordingly to mario's current position
                for(int x = cameraleft; x<(24+cameraleft); x++){
                    for(int y = 0; y<12;y++){
                        if(levelarray[x][y]!=null) {
                            //c.drawBitmap(levelarray[x][y], (x-cameraleft) * blockside, y * blockside, null);
                            c.drawBitmap(levelarray[x][y],null, tilesets[x][y].returnRect(),null);

                        }
                        if(Rect.intersects(mario.returnRect(),tilesets[x][y].returnRect())) {
                            marioGravity(mario, tilesets[x][y]);
                            marioCollideRect(mario,tilesets[x][y].returnRect(),paint);
                        }
                    }
                }

                goombaone.movement();
                goombaone.collision();
                goombaone.draw(c);
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
        if( m.returnLastMove() == 1 && Rect.intersects(m.returnRect(),r) && m.returnRect().bottom + 1 < r.top){
                m.moveLeft();
        }
        else if( m.returnLastMove() == 3 && Rect.intersects(m.returnRect(),r) && m.returnRect().bottom + 1 < r.top){
            m.moveRight();
        }
        else if(m.returnLastMove() == 0 && Rect.intersects(m.returnRect(),r)){
            r.offset(-5000,-10);
            p.setColor(Color.TRANSPARENT);
        }
    }

    public void marioGravity(RectPlayer m,Tileset r){
        if (m.returnRect().bottom + 1 > r.returnRect().top && m.returnRect().left < r.returnRect().right && m.returnRect().right > r.returnRect().left && m.returnRect().centerY() < r.returnRect().centerY() && r.isCollideable()) {
            while (Rect.intersects(m.returnRect(), r.returnRect())) {
                m.returnRect().offset(0, -1);
            }

            return;
        }
        else {
            m.moveDown();

        }
    }

    public void frameShift(RectPlayer m, Tileset[][] tilesets){
        if(m.returnRect().centerX() + 80 > sWidth/2){
            if(cameraleft +24 < 100) {
                m.moveLeft();
                m.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.smallmario));
                for(int i = 0; i < 100; i++ ) {
                    for(int j = 0; j < 12; j++ ) {
                        tilesets[i][j].returnRect().offset(-(sHeight/14), 0);

                    }
                }
                cameraleft++;
            }
        }
    }

    //public void canvas
    public void bmap(){
        int color, red, green, blue, blockside;
        blockside = sHeight/14;
        Bitmap blk;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pixelart8);
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
                tilesets[i][j]=new Tileset();
                tilesets[i][j].returnRect().set(i*(sHeight/14),j*(sHeight/14), (i*sHeight/14) + sHeight/14,(j*sHeight/14) + sHeight/14);
                tilesets[i][j].returnPaint().setARGB(0,red,blue,green);
                if(levelarray[i][j] == null) {
                    tilesets[i][j].setCollideable(false);
                }
                else{
                    tilesets[i][j].setCollideable(true);
                }

            }
        }

    }
    public Bitmap block(int r, int b, int g, int x, int y){
        int blockside = sHeight/14;
        Bitmap blk, ground, sky, brick, question, door;
        blk = null;
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
        brick = BitmapFactory.decodeResource(getResources(), R.drawable.brickblock1);
        sky = BitmapFactory.decodeResource(getResources(), R.drawable.skyblu);
        question = BitmapFactory.decodeResource(getResources(), R.drawable.question);
        door = BitmapFactory.decodeResource(getResources(), R.drawable.mariodoor);

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
        else if(r == 0 && g == 255 && b==255){
            blk = door;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside * 2, false);
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
