package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;


public class mPlayerSprite extends mSimpleSprite {

    public static final int DAMAGED_TIME = 20;
    public static final int JUMP_TIME = 10;
    public static final int DEAD_TIME = 12;

    private int timerDamaged = 0;
    private int timerJump = 0;
    private int timerLastDead = 0;
    public boolean isJumping = false;
    public boolean isDamaged = false;

    public static final float DX = 0;
    public static final float DY = 0;

    private int step = 0;
    private int live;

    private Bitmap[] bmps;
    private Bitmap damagedBmp;
    private Bitmap jumpBmp;

    public mPlayerSprite(float x, float y, Resources res, int id1, int id2, int id3, int id4, float height_) {
        super(x, y, DX, DY, res, id1, height_);
        this.type = TYPE_PLAYERSPRITE;
        live = 3;
        bmps = new Bitmap[2];
        bmps[0] = bmp;
        bmps[1] = BitmapFactory.decodeResource(res, id2);
        jumpBmp = BitmapFactory.decodeResource(res, id3);
        damagedBmp = BitmapFactory.decodeResource(res, id4);
    }

    public mBasic updateExist(mLayer[] l){
        if (timerDamaged == DAMAGED_TIME){
            for (mLayer line : l) {
                line.isDamaged = false;
            }
            isDamaged = false;
            if (live == 0){
                died(l);
            }
        }
        if (isJumping){
            return null;
        }
        for (mBasic a : l[0].data) {
            if (a != null && this.isSelected(a)){
                live--;
                isDamaged = true;
                timerDamaged = 0;
                for (mLayer line : l) {
                    line.isDamaged = true;
                }
                return a;
            }
        }
        return null;
    }

    public int getLive (){
        return live;
    }

    void updateStatus() {
        if (timerJump <= JUMP_TIME){
            timerJump++;
        } else {
            isJumping = false;
        }
        if (timerLastDead <= DEAD_TIME){
            timerLastDead++;
        }
        if (timerDamaged <= DAMAGED_TIME){
            timerDamaged++;
        } else {
            isDamaged = false;
        }
    }

    public void startJump(){
        if (timerLastDead < DEAD_TIME){
            return;
        }
        timerLastDead = 0;
        timerJump = 0;
        isJumping = true;
    }

    void update(float dx, float dy) {
        if (isJumping) {
            bmp = jumpBmp;
        } else if (isDamaged) {
            bmp = damagedBmp;
        } else {
            truAddDX(dx);
            truAddDY(dy);
            Log.d(TAG,
                "x changes from " + Float.toString(dx) + " to " + Float.toString(x) + ", " +
                        "y changes from " + Float.toString(dy) + " to " + Float.toString(y)
            );
            step = (step + 1) % 2;
            bmp = bmps[step];
        }
        src.set(0, 0, bmp.getWidth(), bmp.getHeight());
        updateStatus();
    }

    public void restart(){
        live = 3;
        isDamaged = false;
        isJumping = false;
        timerDamaged = 0;
        timerJump = 0;
        exist = true;
    }

    private void died(mLayer[] l){
        for(mLayer line : l) {
            line.clear();
            line.isDamaged = true;
        }
        exist = false;
    }
    private void truAddDX(float dx){
        x += dx;
        if ( y < 1.62 * mSettings.CurrentYRes * x / mSettings.CurrentXRes - 0.82 * mSettings.CurrentYRes)
            x -= dx;
    }

    private void truAddDY(float dy){
        if ((y + dy > mSettings.CurrentXRes / 6) && (y + dy < mSettings.CurrentYRes * 7 / 8))
            y += dy;
    }

    @Override
    void update() {
    }

}
