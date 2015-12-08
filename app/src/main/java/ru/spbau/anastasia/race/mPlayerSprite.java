package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;


public class mPlayerSprite extends mSimpleSprite {

    public static final float DX = 0;
    public static final float DY = 0;

    private int step = 0;
    private int live;

    private Bitmap[] bmps;

    public mPlayerSprite(float x, float y, Resources res, int id1, int id2) {
        super(x, y, DX, DY, res, id1);
        this.type = TYPE_PLAYERSPRITE;
        live = 3;
        bmps = new Bitmap[2];
        bmps[0] = bmp;
        bmps[1] = BitmapFactory.decodeResource(res, id2);
    }

    public mBasic updateExist(ArrayList<mBasic> data){
        for (mBasic a : data) {
            if (a != null && this.isSelected(a.x, a.y)){
                live--;
                return a;

            }
            if (notInFild()){
                live--;
            }
            if (live == 0){
                exist = false;
            }
        }
        return null;
    }

    public boolean notInFild(){
        if ((x < 80 * mSettings.ScaleFactorX) || (x > 60 * mSettings.ScaleFactorX)
                || (y < 28 / 36 * x - 190) || (y > - 28 / 36 * x + 190)){
            //return true;
        }
        return false;
    }

    public int getLive (){
        return live;
    }

    void update(float dx, float dy) {
        x = x + dx;
        y = y + dy;
        step = (step + 1) % 2;
        bmp = bmps[step];
    }

    public void restart(){
        live = 3;
    }


    @Override
    void update() {
        x = x + dx;
        y = y + dy;
        step = (step + 1) % 2;
        bmp = bmps[step];
    }



}
