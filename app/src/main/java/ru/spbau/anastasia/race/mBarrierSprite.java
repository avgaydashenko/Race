package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by alex on 22.10.2015.
 */
public class mBarrierSprite extends mSimpleSprite {

    private static Bitmap [][] barriersSprite = new Bitmap[2][6];
    public static final Random RND = new Random();
    private static int row;
    private static float[] rowX = new float[5];
    private static float[] rowY = new float[5];
    private static float[] rowDX = new float[5];
    private static float[] rowDY = new float[5];
    private static int numOfImage;

    public mBarrierSprite(Resources res, float speed_, int numOfTheme_, float height_) {
        super(rowX[row], rowY[row], rowDX[row] * speed_, rowDY[row] * speed_,  whithBarrier(res, numOfTheme_), height_);
        this.type = TYPE_BARRIERSPRITE;
    }

    public mBarrierSprite(FileForSent file, Resources res, float speed_, int numOfTheme_, float height_) {
        super(rowX[file.getRow()], rowY[file.getRow()], rowDX[file.getRow()] * speed_, rowDY[file.getRow()] * speed_,  whithBarrierSent(file, res, numOfTheme_), height_);
        this.type = TYPE_BARRIERSPRITE;
    }

    public static void initBarrier(Resources res){
        barriersSprite[0][0] = BitmapFactory.decodeResource(res, R.drawable.barrier6);
        barriersSprite[0][1] = BitmapFactory.decodeResource(res, R.drawable.barrier1);
        barriersSprite[0][2] = BitmapFactory.decodeResource(res, R.drawable.barrier2);
        barriersSprite[0][3] = BitmapFactory.decodeResource(res, R.drawable.barrier3);
        barriersSprite[0][4] = BitmapFactory.decodeResource(res, R.drawable.barrier4);
        barriersSprite[0][5] = BitmapFactory.decodeResource(res, R.drawable.barrier5);

        barriersSprite[1][0] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        barriersSprite[1][1] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        barriersSprite[1][2] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        barriersSprite[1][3] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        barriersSprite[1][4] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        barriersSprite[1][5] = BitmapFactory.decodeResource(res, R.drawable.barrier0);
        for(int i = 0; i < 5; i++){
            rowX[i] = mSettings.ScaleFactorX * (360 + i * 20 );
            rowY[i] = 90 * mSettings.ScaleFactorY;
            rowDY[i] = 14 * mSettings.ScaleFactorY;
            rowDX[i] = (-12 + i * 6) * mSettings.ScaleFactorX;
        }

    }

    private static Bitmap whithBarrier(Resources res, int numOfTheme){
        row = RND.nextInt(5);
        numOfImage = RND.nextInt(6);
        return barriersSprite[numOfTheme][numOfImage];
    }

    private static Bitmap whithBarrierSent(FileForSent file, Resources res, int numOfTheme){
        row = file.getRow();
        return barriersSprite[numOfTheme][file.getNumOfImage()];
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

    @Override
    public FileForSent toFileForServer(float dx, float dy, boolean isJumping) {
        FileForSent file = new FileForSent(dx, dy, row, numOfImage, isJumping);
        return file;
    }
}
