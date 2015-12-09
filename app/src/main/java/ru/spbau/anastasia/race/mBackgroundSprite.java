package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by alex on 22.10.2015.
 */
public class mBackgroundSprite extends mSimpleSprite {
    private static Bitmap[] backgrundSrite  = new Bitmap[7];;
    public static final Random RND = new Random();
    public static final float SPEED = 1;
    private static int row;
    private static float[] rowX = new float[2];
    private static float[] rowY = new float[2];
    private static float[] rowDX = new float[2];
    private static float[] rowDY = new float[2];

    public mBackgroundSprite(Resources res) {
        super(rowX[row], rowY[row], rowDX[row] * SPEED, rowDY[row] * SPEED, whithBarrier(res));
        this.type = TYPE_BACKGROUNDSPRITE;
    }

    public static void initBarrier(Resources res){
        backgrundSrite[0] = BitmapFactory.decodeResource(res, R.drawable.background1);
        backgrundSrite[1] = BitmapFactory.decodeResource(res, R.drawable.background2);
        backgrundSrite[2] = BitmapFactory.decodeResource(res, R.drawable.background3);
        backgrundSrite[3] = BitmapFactory.decodeResource(res, R.drawable.background4);
        backgrundSrite[4] = BitmapFactory.decodeResource(res, R.drawable.background5);
        backgrundSrite[5] = BitmapFactory.decodeResource(res, R.drawable.background6);
        backgrundSrite[6] = BitmapFactory.decodeResource(res, R.drawable.background7);
        for(int i = 0; i < 2; i++){
            rowX[i] = mSettings.ScaleFactorX * (330 + i * 140 );
            rowY[i] = 80 * mSettings.ScaleFactorY;
            rowDY[i] = 4 * mSettings.ScaleFactorY;
            rowDX[i] = (-37 + i * 68) * mSettings.ScaleFactorX;
        }
    }
    private static Bitmap whithBarrier(Resources res){
        row = RND.nextInt(2);
        return backgrundSrite[RND.nextInt(7)];
    }

    private void updateExist(){
        this.exist = (this.y < mSettings.CurrentYRes) && (this.x < mSettings.CurrentXRes) && (this.x > 0);
    }

    @Override
    void update() {
        x = x + dx;
        y = y + dy;
        updateExist();
    }
}
