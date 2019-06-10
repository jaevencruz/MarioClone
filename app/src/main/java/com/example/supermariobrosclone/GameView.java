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

import java.util.Random;

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
    boolean playerControl = true;
    boolean mapCloned = false;
    boolean cantkill = false;
    boolean oscillator = false;
    boolean degradeInvicibility = false;
    int blockside = sHeight/14;
    int score = 0;
    int lives = 3;
    int deathJumpCounter =60;
    int starCounter = 0;
    static int cameraleft = 0;
    Bitmap mushroom = decodeSampledBitmapFromResource(getResources(),R.drawable.supermushroom, 100,100);
    Bitmap bg3 = decodeSampledBitmapFromResource(getResources(),R.drawable.forestpng, 1000,1000);
    Bitmap bg1 = decodeSampledBitmapFromResource(getResources(),R.drawable.bg1, 700,700);
    Bitmap usedQuestion = decodeSampledBitmapFromResource(getResources(),R.drawable.usedquestion,100,100);
    Bitmap load = decodeSampledBitmapFromResource(getResources(),R.drawable.loadingscreen, 200, 200);
    Bitmap deadMario = decodeSampledBitmapFromResource(getResources(),R.drawable.deadmario, 100, 100);
    Bitmap smallMarioLeftStar = decodeSampledBitmapFromResource(getResources(),R.drawable.smallmarioleftstar,100,100);
    Bitmap smallMarioRightStar = decodeSampledBitmapFromResource(getResources(),R.drawable.smallmariorightstar,100,100);
    Bitmap smallMarioLeft = decodeSampledBitmapFromResource(getResources(),R.drawable.smallmarioleft,100,100);
    Bitmap smallMario = decodeSampledBitmapFromResource(getResources(),R.drawable.smallmarioright,100,100);
    Bitmap bigMarioLeftStar = decodeSampledBitmapFromResource(getResources(),R.drawable.bigmarioleftstar,100,200);
    Bitmap bigMarioRightStar = decodeSampledBitmapFromResource(getResources(),R.drawable.bigmariorightstar,100,200);
    Bitmap bigMarioLeft = decodeSampledBitmapFromResource(getResources(),R.drawable.bigmarioleft,100,200);
    Bitmap bigMarioRight = decodeSampledBitmapFromResource(getResources(),R.drawable.bigmarioright,100,200);
    Bitmap deathScreen = decodeSampledBitmapFromResource(getResources(),R.drawable.tomb, 200, 200);
    Bitmap gameOverScreen = decodeSampledBitmapFromResource(getResources(),R.drawable.gameoverscreen,200,200);
    Bitmap winnerSceen = decodeSampledBitmapFromResource(getResources(),R.drawable.yourewinner, 400, 200);
    float x,y;
    Paint paint = new Paint();
    Goomba goombaone = new Goomba(decodeSampledBitmapFromResource(getResources(),R.drawable.goombaleft,100,100),this.getContext());
    Consumable shroomOne = new Consumable(mushroom,this.getContext());
    //Goomba goombaone = new Goomba(this.getContext());
    RectPlayer mario = new RectPlayer(smallMario, this.getContext());
    Bitmap levelarray[][] = new Bitmap[100][12];
    Tileset tilesets[][] = new Tileset[100][12];
    Tileset resetLevelArray[][] = new Tileset[100][12];
    static int level = 1;
    int temptype[][] = new int[100][12];
    private String scorestr;
    private String livesStr;
    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textPaintLight = new Paint(Paint.ANTI_ALIAS_FLAG);


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
        textPaintLight.setColor(Color.WHITE);
        scorestr = "Score: " + score;
        livesStr = "Lives: " + lives;
        textPaint.setColor(Color.BLACK);
        setTextSizeForWidth(textPaint,200,scorestr);
        setTextSizeForWidth(textPaint,200,livesStr);
        setTextSizeForWidth(textPaintLight,200,scorestr);
        setTextSizeForWidth(textPaintLight,200,livesStr);
        mario.setPosition(sWidth/7,400);
        goombaone.setPosition(1000,400);
        shroomOne.setPosition(500,400);

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
            /*Player finishes all 3 levels*/

            if(winState){
                cantkill = false;
                gameOverState = true;
                mapCloned = false;
                winState = false;
                System.out.println("You Win \n");
                lives = 3;
                System.out.println("Starting over \n");
                level = 1;
                cameraleft = 0;
                score = 0;
                for(int i = 0; i < 200; i ++) {
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.BLACK);
                    c.drawBitmap(winnerSceen, 0, sHeight / 2 - 600, null);
                    getHolder().unlockCanvasAndPost(c);
                }
                while (gameOverState){
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.BLACK);
                    c.drawBitmap(gameOverScreen, sWidth/2 - 400, sHeight/2 - 400,null);
                    getHolder().unlockCanvasAndPost(c);
                }
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(load, sWidth/2 - 400, sHeight/2 - 400,null);
                getHolder().unlockCanvasAndPost(c);
                bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
                mario.setPosition(sWidth/7,300);
                playerControl = true;
                continue;
            }
            /*Game over case*/
            else if(lives < 0){
                cameraleft = 0;
                score = 0;
                mapCloned = false;
                gameOverState = true;
                System.out.println("Game Over");
                lives = 3;
                level = 1;
                while (gameOverState){
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.BLACK);
                    c.drawBitmap(gameOverScreen, sWidth/2 - 400, sHeight/2 - 400,null);
                    getHolder().unlockCanvasAndPost(c);
                }
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(load, sWidth/2 - 400, sHeight/2 - 400,null);
                getHolder().unlockCanvasAndPost(c);
                mario.setPosition(sWidth/7,300);
                gameOverState = false;
                bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
                playerControl = true;
                continue;
            }
            /*Level transition*/
            else if(loadLvl){
                mapCloned = false;
                loadLvl = false;

                cameraleft = 0;
                c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawBitmap(load, sWidth/2 - 400, sHeight/2 - 400,null);
                getHolder().unlockCanvasAndPost(c);
                if(level == 1){
                    bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level1));

                }
                else if(level == 2) {
                    bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level2));
                }
                else if(level == 3){
                    System.out.println("Loading lvl3 \n");
                    mario.setPosition(200,300);
                    bmap(BitmapFactory.decodeResource(getResources(), R.drawable.level3));
                }
                playerControl = true;
                continue;
            }
            /*When mario dies, falls through and stage loads again*/
            else if(marioDeath){
                mario.setBitmap(deadMario);
                for(int i = deathJumpCounter; i > 0; i--)
                {
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.RED);
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
                    c.drawColor(Color.RED);
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
                deathJumpCounter = 60;
                lives-=1;
                cameraleft = 0;
                marioDeath = false;
                mario.setBitmap(smallMario);
                mario.setPosition(sWidth/7,400);
                resetGoomba(goombaone);
                consumableReset(shroomOne);
                for(int i = 0; i < 100 ; i++) {
                    c = getHolder().lockCanvas();
                    c.drawColor(Color.BLACK);
                    c.drawBitmap(deathScreen, sWidth / 2 - 400, sHeight / 2 - 400, null);
                    getHolder().unlockCanvasAndPost(c);
                }
                levelReset();
                playerControl = true;
                continue;
            }

            synchronized (getHolder()){
                if(!getHolder().getSurface().isValid()){
                    continue;
                }

                c = getHolder().lockCanvas();
                if(level == 1){
                    c.drawBitmap(bg1,0,0,null);
                }
                else if(level == 2){
                    c.drawColor(Color.BLACK);
                }
                else if(level == 3){
                    c.drawBitmap(bg3, 0, 0,null);
                }
                else{
                    c.drawColor(Color.CYAN);
                }

                frameShift(mario, goombaone,shroomOne,tilesets);
                scorestr = "Score: " + score;
                livesStr = "Lives: " + lives;
                if(level == 2 || level == 3){
                    c.drawText(scorestr, 9*blockside, 150, textPaintLight);
                    c.drawText(livesStr, 14*blockside, 150, textPaintLight);
                }
                else {
                    c.drawText(scorestr, 9 * blockside, 150, textPaint);
                    c.drawText(livesStr, 14 * blockside, 150, textPaint);
                }
                //this loop takes the level array and prints accordingly to mario's current position
                for(int x = cameraleft; x<(24+cameraleft); x++){
                    for(int y = 0; y<12;y++){
                        //If tileset can be drawn, then draw
                        if(tilesets[x][y].isDraw()) {

                            tilesets[x][y].draw(c);
                        }
                        if(tilesets[x][y].blockType==7)
                        {
                            tilesets[x][y].draw(c);
                        }
                        goombaMarioCollision(mario,goombaone);
                        consumableMarioCollision(mario,shroomOne);
                        if(marioDeath){
                            break;
                        }
                        if(Rect.intersects(shroomOne.returnRect(),tilesets[x][y].returnRect())) {
                            consumableCollision(shroomOne, tilesets[x][y]);
                            consumableGravity(shroomOne, tilesets[x][y]);
                        }
                        if(Rect.intersects(goombaone.returnRect(),tilesets[x][y].returnRect())) {
                            goombaCollision(goombaone, tilesets[x][y]);
                            goombaGravity(goombaone, tilesets[x][y]);
                        }
                        //Check mario collision with some enemies and consumables
                        if(Rect.intersects(mario.returnRect(),tilesets[x][y].returnRect())) {
                            //Invincibility check
                            if(cantkill)
                            {
                                //Touch piranha plant when invincible
                                if(tilesets[x][y].blockType==5)
                                {
                                    tilesets[x][y+1].blockType = 0;
                                    tilesets[x][y].blockType = 0;
                                }
                            }
                            for(int g = x; g<(x+3); g++){

                                    if(tilesets[g][y].returnType() == 4)
                                    {
                                        tilesets[g][y-1].setType(5);
                                        tilesets[g][y-1].bitmap = piranha();
                                        levelarray[g][y-1] = piranha();
                                        tilesets[g][y-1].draw(c);
                                    }

                            }
                            //Check if consumed star
                            if(tilesets[x][y].blockType==7)
                            {
                                score+=1000;
                                cantkill = true;
                                starCounter = 0;
                                tilesets[x][y].blockType = -1;
                                tilesets[x][y].setCollideable(false);
                                tilesets[x][y].setDraw(false);
                            }
                            //check if consumed coin
                            if(tilesets[x][y].blockType==6)
                            {
                                score+=100;
                                tilesets[x][y].blockType = -1;
                                //tilesets[x][y].bitmap = null;
                                //levelarray[x][y] = null;
                                tilesets[x][y].setCollideable(false);
                                tilesets[x][y].setDraw(false);

                            }
                            //lvl 2 transition
                            if(x>=87 && level==1)
                            {
                                System.out.println("Loading lvl2 \n");
                                cantkill = false;
                                //bmap(BitmapFactory.decodeResource(getResources(), R.drawable.pixelmap3));
                                mario.setPosition(sWidth/7,300);
                                cameraleft = 0;
                                level = 2;
                                loadLvl = true;
                            }
                            //lvl 3 transition
                            else if(x>=87 && level==2)
                            {

                                cantkill = false;
                                level = 3;
                                loadLvl = true;
                                break;
                            }
                            //end game condition
                            else if(x>=87 && level==3)
                            {
                                System.out.println("You Win \n");
                                winState = true;
                                break;
                            }
                            //Piranha plant touch
                            else if(tilesets[x][y].returnType()==5  && (mario.returnRect().centerX() > tilesets[x][y].returnRect().left || mario.returnRect().centerX() < tilesets[x][y].returnRect().right))
                            {
                                if(mario.returnMarioState() == 0) {
                                    System.out.println("You Died \n");
                                    marioDeath = true;
                                    playerControl = false;
                                    break;
                                }
                                else if (mario.returnMarioState() == 1){
                                    mario.setMarioState(5);
                                    degradeInvicibility = true;
                                    cantkill = true;
                                    mario.setBitmap(smallMario);

                                }                            }

                            marioCollideRect(mario,tilesets[x][y]);
                            marioGravity(mario, tilesets[x][y]);

                        }


                    }
                }

                if(reset || marioDeath){
                    reset = false;
                    invalidate();

                }
                else{
                    if(cantkill){
                        starCounter ++;
                        if(starCounter > 2000  || (mario.returnMarioState() == 5 && starCounter > 500)){
                            if(mario.returnMarioState() == 5){
                                degradeInvicibility = false;
                                mario.setMarioState(0);
                                mario.setBitmap(smallMario);
                            }
                            cantkill = false;
                            starCounter = 0;
                        }
                        //small mario invincibility
                        else if(oscillator && mario.returnLastMove() == 1 && mario.returnMarioState() == 2) {
                            mario.setBitmap(smallMarioRightStar);
                            oscillator = false;
                        }
                        else if (!oscillator && mario.returnLastMove() == 1 && mario.returnMarioState() == 2){
                            mario.setBitmap(smallMario);
                            oscillator = true;
                        }
                        else if(oscillator && mario.returnLastMove() == 3 && mario.returnMarioState() == 2) {
                            mario.setBitmap(smallMarioLeftStar);
                            oscillator = false;
                        }
                        else if (!oscillator && mario.returnLastMove() == 3 && mario.returnMarioState() == 2){
                            mario.setBitmap(smallMarioLeft);
                            oscillator = true;
                        }
                        else if(oscillator && mario.returnLastMove() == 1 && mario.returnMarioState() == 3) {
                            mario.setBitmap(bigMarioRightStar);
                            oscillator = false;
                        }
                        //Big mario invincibility
                        else if (!oscillator && mario.returnLastMove() == 1 && mario.returnMarioState() == 3){
                            mario.setBitmap(bigMarioRight);
                            oscillator = true;
                        }
                        else if(oscillator && mario.returnLastMove() == 3 && mario.returnMarioState() == 3) {
                            mario.setBitmap(bigMarioLeftStar);
                            oscillator = false;
                        }
                        else if (!oscillator && mario.returnLastMove() == 3 && mario.returnMarioState() == 3){
                            mario.setBitmap(bigMarioLeft);
                            oscillator = true;
                        }
                    }
                    shroomOne.movement();
                    goombaone.movement();
                    goombaone.draw(c);
                    mario.draw(c);
                    shroomOne.draw(c);
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
            if( m.returnLastMove() == 1 && m.returnRect().bottom - 2 > r.returnRect().top && m.returnRect().centerX() < r.returnRect().left && (r.returnBlockType() == 1 || r.returnBlockType() == 0)){
                while(Rect.intersects(m.returnRect(),r.returnRect()) && m.returnRect().right > r.returnRect().left) {
                    m.returnRect().offset(-1,0);
                }


            }
            //Mario runs into a block from the left
            else if( m.returnLastMove() == 3 && m.returnRect().bottom - 2 > r.returnRect().top && m.returnRect().centerX() > r.returnRect().right && (r.returnBlockType() == 1 || r.returnBlockType() == 0)){
                while(Rect.intersects(m.returnRect(),r.returnRect()) && m.returnRect().left < r.returnRect().right) {
                    m.returnRect().offset(1,0);
                }

            }
            /**Collide with breakable block**/
            else if(m.returnLastMove() == 0  && m.returnRect().top > r.returnRect().top){
                if(r.returnBlockType() == 1){
                    if(mario.returnMarioState() == 1 || mario.returnMarioState() == 3){
                        r.setDraw(false);
                        r.setCollideable(false);
                        score = score + 20;
                    }
                    else{
                        return;
                    }
                }
                else if (r.returnBlockType() == 3){
                    r.setBitmap(usedQuestion);
                    r.setType(8);
                    m.returnRect().offset(0,1);
                }

            }
            else{
                return;
            }
        }
    }

    public void marioGravity(RectPlayer m,Tileset r){
        if (Rect.intersects(m.returnRect(), r.returnRect()) && m.returnRect().bottom > r.returnRect().top   && r.isCollideable() /**m.returnRect().centerY() > r.returnRect().centerY()**/) {
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
        if(Rect.intersects(g.returnRect(),r.returnRect()) && r.isCollideable()) {
            if (g.returnLastMove() == 1 && g.returnRect().bottom - 2 > r.returnRect().top && g.returnRect().centerX() < r.returnRect().left && (r.returnBlockType() == 1 || r.returnBlockType() == 0)) {
                g.returnRect().offset(-1,0 );
                g.setMovePattern(false);
            }

            //Mario runs into a block from the left
            else if (g.returnLastMove() == 3 && g.returnRect().bottom - 2 > r.returnRect().top && g.returnRect().centerX() > r.returnRect().right && (r.returnBlockType() == 1 || r.returnBlockType() == 0)) {
                g.returnRect().offset(1, 0);
                g.setMovePattern(true);
            }
        }
    }
    public void goombaGravity(Goomba g,Tileset r){
        if (Rect.intersects(g.returnRect(), r.returnRect()) && g.returnRect().bottom > r.returnRect().top   && r.isCollideable()) {
            while (Rect.intersects(g.returnRect(), r.returnRect())) {
                g.returnRect().offset(0, -1);
            }
            return;
        }

        else {
            g.returnRect().offset(0,1);

        }
    }
    public void goombaMarioCollision(RectPlayer m, Goomba g){
        if (Rect.intersects(g.returnRect(), m.returnRect()) && g.isAlive()){
            if(m.returnRect().exactCenterY()< g.returnRect().top || m.returnMarioState() == 2 || m.returnMarioState() == 3){
                g.setAlive(false);
                score = score + 200;
            }
            else if(m.returnMarioState() == 1){
                m.setMarioState(5);
                degradeInvicibility = true;
                cantkill = true;
                m.setBitmap(smallMario);
            }
            else if(m.returnMarioState() == 5){
                return;
            }
            else{
                marioDeath = true;
            }
        }
    }

    public void resetGoomba(Goomba g){
        g.setAlive(true);
        g.setPosition(500,400);
    }

    /**End enemy behaviours**/

    /**Consumables Behaviour**/
    private void consumableCollision(Consumable c, Tileset r){
        //Mario runs into a block from the right
        if(Rect.intersects(c.returnRect(),r.returnRect()) && r.isCollideable()) {
            if (c.returnLastMove() == 1 && c.returnRect().bottom - 2 > r.returnRect().top && c.returnRect().centerX() < r.returnRect().left && (r.returnBlockType() == 1 || r.returnBlockType() == 0)) {
                c.returnRect().offset(-1,0 );
                c.setMovePattern(false);
            }

            //Mario runs into a block from the left
            else if (c.returnLastMove() == 3 && c.returnRect().bottom - 2 > r.returnRect().top && c.returnRect().centerX() > r.returnRect().right && (r.returnBlockType() == 1 || r.returnBlockType() == 0)) {
                c.returnRect().offset(1, 0);
                c.setMovePattern(true);
            }
        }
    }
    public void consumableGravity(Consumable c,Tileset r){
        if (Rect.intersects(c.returnRect(), r.returnRect()) && c.returnRect().bottom > r.returnRect().top   && r.isCollideable()) {
            while (Rect.intersects(c.returnRect(), r.returnRect())) {
                c.returnRect().offset(0, -1);
            }
            return;
        }

        else {
            c.returnRect().offset(0,1);

        }
    }
    public void consumableMarioCollision(RectPlayer m, Consumable c){
        if (Rect.intersects(c.returnRect(), m.returnRect()) && c.isAlive()){
            c.setAlive(false);
            score = score + 1000;
            m.setMarioState(1);
            if(m.returnLastMove() == 1) {
                m.setBitmap(bigMarioRight);
            }
            else if(m.returnMarioState() == 3){
                m.setBitmap(bigMarioLeft);
            }
            else{
                m.setBitmap(bigMarioRight);
            }
        }
    }

    public void consumableReset(Consumable c){
        c.setAlive(true);
        c.setPosition(800,400);
    }
    /**End Consumables Behaviour**/

    public void frameShift(RectPlayer m,Goomba g,Consumable c,Tileset[][] tilesets){
        if(m.returnRect().centerX() > 2*sWidth/3){
            if(cameraleft +24 < 100) {
                m.returnRect().offset(-(sHeight/14),0);
                g.returnRect().offset(-(sHeight/14),0);
                c.returnRect().offset(-(sHeight/14),0);
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
    public Bitmap star(){
        Bitmap pir = BitmapFactory.decodeResource(getResources(), R.drawable.staryu);
        pir = Bitmap.createScaledBitmap(pir, blockside, blockside, false);
        return pir;
    }

    public void levelReset() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 12; j++) {
                try{
                    tilesets[i][j].setType(resetLevelArray[i][j].returnType());
                }catch (Exception e){}
                try{
                    tilesets[i][j].setBitmap(resetLevelArray[i][j].returnBitmap());
                }catch (Exception e){}
                tilesets[i][j].returnRect().set(i*(sHeight/14),j*(sHeight/14), (i*sHeight/14) + sHeight/14,(j*sHeight/14) + sHeight/14);
                try {
                    tilesets[i][j].setDraw(resetLevelArray[i][j].isDraw());
                }catch (Exception e){}
                try {
                    tilesets[i][j].setCollideable(resetLevelArray[i][j].isCollideable());
                }catch (Exception e){}
            }
        }
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
                if(mapCloned == false){
                    if(resetLevelArray[i][j] == null) {
                        resetLevelArray[i][j] = new Tileset(blk);
                    }else {
                        resetLevelArray[i][j].setBitmap(blk);
                    }
                    resetLevelArray[i][j].returnRect().set(i*(sHeight/14),j*(sHeight/14), (i*sHeight/14) + sHeight/14,(j*sHeight/14) + sHeight/14);
                    resetLevelArray[i][j].returnPaint().setARGB(255,red,blue,green);

                    if(blk == null) {
                        resetLevelArray[i][j].setCollideable(false);
                        resetLevelArray[i][j].setDraw(false);
                        try{
                            resetLevelArray[i][j].setType(temptype[i][j]);
                        }catch (Exception e){}
                    }
                    else{
                        resetLevelArray[i][j].setCollideable(true);
                        resetLevelArray[i][j].setDraw(true);
                        resetLevelArray[i][j].setType(temptype[i][j]);
                    }
                }
                /*When level is initialized, it gets cloned into resetLevelArray and when Mario Dies, the level gets reset to its initial state */
                if(tilesets[i][j] == null) {
                    tilesets[i][j] = new Tileset(blk);
                }
                tilesets[i][j].setBitmap(blk);
                tilesets[i][j].returnRect().set(i*(sHeight/14),j*(sHeight/14), (i*sHeight/14) + sHeight/14,(j*sHeight/14) + sHeight/14);
                tilesets[i][j].returnPaint().setARGB(255,red,blue,green);

                if(blk == null) {
                    tilesets[i][j].setCollideable(false);
                    tilesets[i][j].setDraw(false);
                    try {
                        resetLevelArray[i][j].setType(temptype[i][j]);
                    }catch (Exception e){}
                }
                else{
                    tilesets[i][j].setCollideable(true);
                    tilesets[i][j].setDraw(true);
                    tilesets[i][j].setType(temptype[i][j]);
                }

            }
        }
        mapCloned = true;
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
        pipe = BitmapFactory.decodeResource(getResources(), R.drawable.pipep);
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
            tilesets[x][y-1].bitmap = star();
            tilesets[x][y-1].blockType = 7;
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
        m.setLastMove(0);
        for(int k = 0 ; k < 25; k++) {
            m.moveUp();
            for(int i = 0; i<100;i++){
                for(int j = 0; j <12;j++){
                    if(Rect.intersects(m.returnRect(),tilesets[i][j].returnRect()) && tilesets[i][j].isCollideable()){
                        while(Rect.intersects(m.returnRect(),tilesets[i][j].returnRect())){
                            m.returnRect().offset(0,1);
                        }
                        if((tilesets[i][j].returnBlockType() == 1 )  && (mario.returnMarioState() == 1 || mario.returnMarioState() == 3) ) {
                            m.returnRect().offset(0, -1);
                        }
                        else if(tilesets[i][j].returnBlockType() == 3 ) {
                            m.returnRect().offset(0, -1);
                        }
                        else if(tilesets[i][j].returnBlockType() == 6) {
                            score += 100;
                            tilesets[i][j].blockType = -1;
                            tilesets[i][j].setCollideable(false);
                            tilesets[i][j].setDraw(false);
                        }
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
