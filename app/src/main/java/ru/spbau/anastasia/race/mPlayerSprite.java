package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;


public class mPlayerSprite extends mSimpleSprite {

    public static final String TAG = "mPlayerSprite";

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
            if (a != null && this.isSelected(a)){
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
        float sx = x, sy = y;
        truAddDX(dx);
        truAddDY(dy);
        Log.d(TAG,
                "x changes from " + Float.toString(sx) + " to " + Float.toString(x) + ", " +
                "y changes from " + Float.toString(sy) + " to " + Float.toString(y)
        );
        step = (step + 1) % 2;
        bmp = bmps[step];
    }

    public void restart(){
        live = 3;
    }

    private void truAddDX(float dx){
      //  if ((x + dx < 80 * mSettings.ScaleFactorX) && (x + dx > mSettings.CurrentXRes - 100))
            x += dx;
        Log.d(TAG, "truAddDX params " +
                Float.toString(x) + " " +
                Float.toString(dx) + " " +
                Float.toString(mSettings.ScaleFactorX) + " " +
                Integer.toString(mSettings.CurrentXRes)
        );
    }

    private void truAddDY(float dy){
        if ((y + dy > 80 * mSettings.ScaleFactorY) && (y + dy < mSettings.CurrentYRes - 100))
            y += dy;
        Log.d(TAG, "truAddDY params " +
                Float.toString(y) + " " +
                Float.toString(dy) + " " +
                Float.toString(mSettings.ScaleFactorY) + " " +
                Integer.toString(mSettings.CurrentYRes)
        );
    }

    @Override
    void update() {
    }

}
