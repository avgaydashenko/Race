package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by alex on 22.10.2015.
 */
public class mBarrierSprite extends mSimpleSprite {

    private static Bitmap [] bariersSrite = new Bitmap[7];
    public static final Random RND = new Random();
    public static final float SPEED = 1;
    private static int row;
    private static float[] rowX = new float[5];
    private static float[] rowY = new float[5];
    private static float[] rowDX = new float[5];
    private static float[] rowDY = new float[5];

    public mBarrierSprite(Resources res) {
        super(rowX[row], rowY[row], rowDX[row] * SPEED, rowDY[row] * SPEED, whithBarrier(res));
        this.type = TYPE_BARRIERSPRITE;
    }

    public static void initBarrier(Resources res){
        bariersSrite[0] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        bariersSrite[1] = BitmapFactory.decodeResource(res, R.drawable.barrier1);
        bariersSrite[2] = BitmapFactory.decodeResource(res, R.drawable.barrier2);
        bariersSrite[3] = BitmapFactory.decodeResource(res, R.drawable.barrier3);
        bariersSrite[4] = BitmapFactory.decodeResource(res, R.drawable.barrier4);
        bariersSrite[5] = BitmapFactory.decodeResource(res, R.drawable.barrier5);
        bariersSrite[6] = BitmapFactory.decodeResource(res, R.drawable.barrier6);
        for(int i = 0; i < 5; i++){
            rowX[i] = mSettings.ScaleFactorX * (360 + i * 20 );
            rowY[i] = 90 * mSettings.ScaleFactorY;
            rowDY[i] = 14 * mSettings.ScaleFactorY;
            rowDX[i] = (-12 + i * 6) * mSettings.ScaleFactorX;
        }

    }
    private static Bitmap whithBarrier(Resources res){
        row = RND.nextInt(5);
        return bariersSrite[RND.nextInt(7)];
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
