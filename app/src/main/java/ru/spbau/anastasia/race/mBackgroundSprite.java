package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by alex on 22.10.2015.
 */
public class mBackgroundSprite extends mSimpleSprite {
    private boolean isLeft;
    private int numOfTheme;
    private static Bitmap[][] backgrundSrite  = new Bitmap[2][2];
    public static final Random RND = new Random();
    private static int row;
    private static float[] rowX = new float[2];
    private static float[] rowY = new float[2];
    private static float[] rowDX = new float[2];
    private static float[] rowDY = new float[2];

    public mBackgroundSprite(Resources res, float speed_, boolean isLeft_, int numOfTheme_, float height_){
        super(rowX[row], rowY[row], rowDX[row] * speed_, rowDY[row] * speed_, whithBarrier(res, numOfTheme_, isLeft_), height_);
        numOfTheme = numOfTheme_;
        isLeft = isLeft_;
        this.type = TYPE_BACKGROUNDSPRITE;
    }

    public static void initBarrier(Resources res){
        backgrundSrite[0][0] = BitmapFactory.decodeResource(res, R.drawable.background1);
        backgrundSrite[0][1] = BitmapFactory.decodeResource(res, R.drawable.background2);
        backgrundSrite[1][0] = BitmapFactory.decodeResource(res, R.drawable.background3);
        backgrundSrite[1][1] = BitmapFactory.decodeResource(res, R.drawable.background4);
        for(int i = 0; i < 2; i++){
            rowX[i] = mSettings.ScaleFactorX * (330 + i * 140 );
            rowY[i] = 80 * mSettings.ScaleFactorY;
            rowDY[i] = 4 * mSettings.ScaleFactorY;
            rowDX[i] = (-37 + i * 68) * mSettings.ScaleFactorX;
        }
    }
    private static Bitmap whithBarrier(Resources res, int numOfTheme, boolean isLeft_){
        row = isLeft_ ? 1 : 0;
        return backgrundSrite[numOfTheme][row];
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
