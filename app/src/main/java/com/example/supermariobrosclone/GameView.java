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
    boolean reset = false;
    boolean loadLvl = false;
    static boolean start = true;
    boolean marioDeath = false;
    boolean gameOverState = false;
    boolean winState = false;
    int blockside = sHeight/14;
    int score = 0;
    int lives = 0;
    int deathJumpCounter = 100;
    static int cameraleft = 0;
    Bitmap bMap = decodeSampledBitmapFromResource(getResources(),R.drawable.brickblock1, 100,100);
    Bitmap load = decodeSampledBitmapFromResource(getResources(),R.drawable.loadingscreen, 200, 200);
    Bitmap deadMario = decodeSampledBitmapFromResource(getResources(),R.drawable.deadmario, 100, 100);
    Bitmap smallMario = decodeSampledBitmapFromResource(getResources(),R.drawable.smallmarioright,100,100);
    Bitmap gameOverScreen = decodeSampledBitmapFromResource(getResources(),R.drawable.gameoverscreen,200,200);
    Bitmap winnerSceen = decodeSampledBitmapFromResource(getResources(),R.drawable.yourewinner, 400, 200);
    float x,y;
    Paint paint = new Paint();
    Goomba goombaone = new Goomba(decodeSampledBitmapFromResource(getResources(),R.drawable.goombaleft,100,100),this.getContext());
    RectPlayer mario = new RectPlayer(smallMario, this.getContext());
    Bitmap levelarray[][] = new Bitmap[100][12];
    Tileset tilesets[][] = new Tileset[100][12];
    Tileset resetLevelArray[][] = new Tileset[100][12];
    static int level = 1;
    int temptype[][] = new int[100][12];
    private String scorestr;
    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        paint.setColor(Color.RED);
        init(context);
    }

    public GameView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        getHolder().addCallback(this);
        paint.setColor(Color.RED);
        init(context);
    }


    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    private void init(Context context){
        scorestr = "Score: " + score;
        textPaint.setColor(Color.BLACK);
        setTextSizeForWidth(textPaint,200,scorestr);
        mario.setPosition(sWidth/7,400);
        goombaone.setPosition(3*(sWidth/4),100);
        System.out.println("Loading level 1");
        bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
        System.out.println("Level 1 printed");
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
            if(winState){
                winState = false;
                System.out.println("You Win \n");
                lives = 3;
                System.out.println("Starting over \n");
                level = 1;
                cameraleft = 0;
                score = 0;
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(winnerSceen, 0, sHeight/2 - 600,null);
                getHolder().unlockCanvasAndPost(c);
                bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
                continue;
            }
            if(lives < 0){
                gameOverState = true;
                System.out.println("Game Over");
                lives = 3;
                while (gameOverState){
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.BLACK);
                    c.drawBitmap(gameOverScreen, sWidth/2 - 400, sHeight/2 - 400,null);
                    getHolder().unlockCanvasAndPost(c);
                }
                bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
                continue;
            }
            if(loadLvl){
                loadLvl = false;
                mario.setPosition(sWidth/7,300);
                cameraleft = 0;
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(load, sWidth/2 - 400, sHeight/2 - 400,null);
                getHolder().unlockCanvasAndPost(c);
                if(level == 2) {
                    bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level2));
                }
                else if(level == 3){
                    System.out.println("Loading lvl3 \n");
                    bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level3));
                }
                continue;
            }
            if(marioDeath){
                mario.setBitmap(deadMario);
                for(int i = deathJumpCounter; i > 0; i--)
                {
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.CYAN);
                    for(int x = cameraleft; x<(24+cameraleft); x++) {
                        for (int y = 0; y < 12; y++) {
                            if (tilesets[x][y].isDraw()) {
                                tilesets[x][y].draw(c);
                            }
                        }
                    }
                    mario.moveUp();
                    mario.draw(c);
                    invalidate();
                    getHolder().unlockCanvasAndPost(c);
                }
                while (mario.returnRect().top < sHeight){
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.CYAN);
                    for(int x = cameraleft; x<(24+cameraleft); x++) {
                        for (int y = 0; y < 12; y++) {
                            if (tilesets[x][y].isDraw()) {
                                tilesets[x][y].draw(c);
                            }
                        }
                    }
                    mario.moveDown();
                    mario.draw(c);
                    invalidate();
                    getHolder().unlockCanvasAndPost(c);
                }
                deathJumpCounter = 100;
                lives-=1;
                cameraleft = 0;
                marioDeath = false;
                mario.setBitmap(smallMario);
                mario.setPosition(sWidth/7,400);
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(winnerSceen, 0, sHeight/2 - 600,null);
                invalidate();
                getHolder().unlockCanvasAndPost(c);
                bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
                continue;
            }
            synchronized (getHolder()){
                if(!getHolder().getSurface().isValid()){
                    continue;
                }
                c = getHolder().lockCanvas();
                c.drawColor(Color.CYAN);
                frameShift(mario, tilesets);
                scorestr = "Score: " + score;
                c.drawText(scorestr, 11*blockside, 150, textPaint);
                //c.drawRect(x - 50, y - 50, x+50, y+50,paint);
                //squareBounder();

                //this loop takes the level array and prints accordingly to mario's current position
                for(int x = cameraleft; x<(24+cameraleft); x++){
                    for(int y = 0; y<12;y++){
                        if(tilesets[x][y].isDraw()) {

                            tilesets[x][y].draw(c);
                        }
                        if(Rect.intersects(mario.returnRect(),tilesets[x][y].returnRect())) {
                            for(int g = x; g<(x+3); g++){

                                    if(tilesets[g][y].returnType() == 4)
                                    {
                                        tilesets[g][y-1].setType(5);
                                        tilesets[g][y-1].bitmap = piranha();
                                        levelarray[g][y-1] = piranha();
                                        tilesets[g][y-1].draw(c);
                                    }

                            }
                            if(tilesets[x][y].blockType==6)
                            {
                                score+=100;
                                tilesets[x][y].blockType = -1;
                                tilesets[x][y].bitmap = null;
                                levelarray[x][y] = null;
                                tilesets[x][y].setCollideable(false);
                                tilesets[x][y].setDraw(false);

                            }
                            if(x>=87 && level==1)
                            {
                                System.out.println("Loading lvl2 \n");
                                //bmap(BitmapFactory.decodeResource(getResources(), R.drawable.pixelmap3));
                                mario.setPosition(sWidth/7,300);
                                cameraleft = 0;
                                level = 2;
                                loadLvl = true;
                            }
                            else if(x>=87 && level==2)
                            {
                                //System.out.println("Loading lvl3 \n");
                                //bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level2));
                                //mario.setPosition(sWidth/7,300);
                                //cameraleft = 0;
                                level = 3;
                                loadLvl = true;
                                break;
                            }
                            else if(x>=87 && level==3)
                            {
                                System.out.println("You Win \n");
                                winState = true;
                                break;
                            }
                            else if(tilesets[x][y].returnType()==5)
                            {
                                System.out.println("You Died \n");
                                marioDeath = true;
                                break;
                            }

                            marioCollideRect(mario,tilesets[x][y]);
                            goombaCollision(goombaone,tilesets[x][y]);
                            marioGravity(mario, tilesets[x][y]);
                            goombaGravity(goombaone,tilesets[x][y]);
                            goombaone.borderCollision();
                        }

                    }
                }

                if(reset || marioDeath){
                    reset = false;
                    invalidate();

                }
                else{
                    goombaone.movement();
                    goombaone.draw(c);
                    mario.draw(c);
                    invalidate();
                }
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

    /**Mario behaviours**/
    private void marioCollideRect(RectPlayer m, Tileset r){
        //Mario runs into a block from the right
        if(Rect.intersects(m.returnRect(),r.returnRect()) && r.isCollideable()){
            if( m.returnLastMove() == 1 && m.returnRect().bottom - 2 > r.returnRect().top && m.returnRect().centerX() < r.returnRect().left ){
                while(Rect.intersects(m.returnRect(),r.returnRect()) && m.returnRect().right > r.returnRect().left) {
                    m.returnRect().offset(-1,0);
                }

            }
            //Mario runs into a block from the left
            else if( m.returnLastMove() == 3 && m.returnRect().bottom - 2 > r.returnRect().top && m.returnRect().centerX() > r.returnRect().right ){
                while(Rect.intersects(m.returnRect(),r.returnRect()) && m.returnRect().left < r.returnRect().right) {
                    m.returnRect().offset(1,0);
                }
            }
            /**Collide with breakable block**/
            else if(m.returnLastMove() == 0 && Rect.intersects(m.returnRect(),r.returnRect()) && r.isCollideable() && m.returnRect().top > r.returnRect().top){
                r.setDraw(false);
                r.setCollideable(false);
            }
        }
    }

    public void marioGravity(RectPlayer m,Tileset r){
        if (Rect.intersects(m.returnRect(), r.returnRect()) && m.returnRect().bottom > r.returnRect().top /**&& m.returnRect().centerX() > r.returnRect().left  && m.returnRect().centerX() < r.returnRect().right**/  && r.isCollideable()) {
            while (Rect.intersects(m.returnRect(), r.returnRect())) {
                m.returnRect().offset(0, -1);
            }
            return;
        }
        else if(m.returnRect().bottom + 1 == r.returnRect().top){
            return;
        }
        else {
            m.returnRect().offset(0,1);

        }
    }
    /**End Mario Behaviours**/

    /**Enemy Behaviours**/
    private void goombaCollision(Goomba g, Tileset r){
        //Mario runs into a block from the right
        if(Rect.intersects(g.returnRect(),r.returnRect()) && r.isCollideable()){
            if( g.returnRect().bottom - 2 > r.returnRect().top && g.returnRect().centerX() < r.returnRect().left ){
                while(Rect.intersects(g.returnRect(),r.returnRect()) && g.returnRect().right > r.returnRect().left) {
                    g.returnRect().offset(-1,0);
                }
                g.setMovePattern(false);
            }
            //Mario runs into a block from the left
            else if( g.returnRect().bottom - 2 > r.returnRect().top && g.returnRect().centerX() > r.returnRect().right ){
                while(Rect.intersects(g.returnRect(),r.returnRect()) && g.returnRect().left < r.returnRect().right) {
                    g.returnRect().offset(1,0);
                }
                g.setMovePattern(true);
            }
        }
    }
    public void goombaGravity(Goomba g,Tileset r){
        if(Rect.intersects(g.returnRect(),r.returnRect())){
            System.out.println("true");
        }
        if (Rect.intersects(g.returnRect(), r.returnRect()) && g.returnRect().bottom > r.returnRect().top && r.isCollideable()) {
            System.out.println("Goomba colided");
            while (Rect.intersects(g.returnRect(), r.returnRect())) {
                g.returnRect().offset(0, -2);

            }
            return;
        }
        else if(g.returnRect().bottom + 1  == r.returnRect().top){
            return;
        }
        else {
            g.returnRect().offset(0,1);

        }
    }
    /**End enemy behaviours**/

    public void frameShift(RectPlayer m, Tileset[][] tilesets){
        if(m.returnRect().centerX() > 2*sWidth/3){
            if(cameraleft +24 < 100) {
                m.returnRect().offset(-(sHeight/14),0);
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

    public Bitmap piranha(){
        Bitmap pir = BitmapFactory.decodeResource(getResources(), R.drawable.pplant);
        pir = Bitmap.createScaledBitmap(pir, blockside, blockside, false);
        return pir;
    }

    public void bmap(Bitmap b){
        int color, red, green, blue, blockside;
        blockside = sHeight/14;
        Bitmap blk;
        //b = BitmapFactory.decodeResource(getResources(), R.drawable.level1);
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
                //levelarray[i][j]= blk;
                /*When level is initialized, it gets cloned into resetLevelArray and when Mario Dies, the level gets reset to its initial state */
                if(tilesets[i][j] == null) {
                    tilesets[i][j] = new Tileset(blk);
                }
                tilesets[i][j].returnRect().set(i*(sHeight/14),j*(sHeight/14), (i*sHeight/14) + sHeight/14,(j*sHeight/14) + sHeight/14);
                tilesets[i][j].returnPaint().setARGB(255,red,blue,green);

                if(blk == null) {
                    tilesets[i][j].setCollideable(false);
                    tilesets[i][j].setDraw(false);

                }
                else{
                    tilesets[i][j].setCollideable(true);
                    tilesets[i][j].setDraw(true);
                    tilesets[i][j].setType(temptype[i][j]);
                }

            }
        }

    }
    public Bitmap block(int r, int b, int g, int x, int y){
        int blockside = sHeight/14;
        Bitmap blk, ground, sky, brick, question, door, pipe, coin;
        blk = null;
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
        brick = BitmapFactory.decodeResource(getResources(), R.drawable.brickblock1);
        sky = BitmapFactory.decodeResource(getResources(), R.drawable.skyblu);
        question = BitmapFactory.decodeResource(getResources(), R.drawable.question);
        door = BitmapFactory.decodeResource(getResources(), R.drawable.allblack);
        pipe = BitmapFactory.decodeResource(getResources(), R.drawable.allgreen);
        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);


        if(r == 0 && g ==0 && b==0) {
            //spawn a ground block if pixel is black
            blk = ground;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 0;
        }
        else if (r==255 && g == 0 && b ==0){
            //spawn a breakable brick if pixel is red
            blk = brick;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 1;
        }
        else if(r == 0 && g == 255 && b==255){
            blk = door;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 2;
        }
        else if(r ==255 && g==255 && b==255){
            blk = null;
            temptype[x][y] = -1;
        }
        else if(r ==255 && g==255 && b==0){
            blk = question;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 3;
        }
        else if(r ==0 && g==255 && b==0){
            blk = pipe;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 4;
        }
        else if(r ==255 && g==0 && b==255){
            blk = coin;
            blk = Bitmap.createScaledBitmap(blk, blockside, blockside, false);
            temptype[x][y] = 6;
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
    public void jmpUp(RectPlayer m){
        for(int k = 0 ; k < 25; k++) {
            m.moveUp();
            for(int i = 0; i<100;i++){
                for(int j = 0; j <12;j++){
                    if(Rect.intersects(m.returnRect(),tilesets[i][j].returnRect()) && tilesets[i][j].isCollideable()){
                        while(Rect.intersects(m.returnRect(),tilesets[i][j].returnRect())){
                            m.returnRect().offset(0,1);
                        }
                        tilesets[i][j].setDraw(false);
                        tilesets[i][j].setCollideable(false);
                        return;
                    }
                }
            }

        }
    }
    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {
        final float testTextSize = 48f;

        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        paint.setTextSize(desiredTextSize);
    }

}
