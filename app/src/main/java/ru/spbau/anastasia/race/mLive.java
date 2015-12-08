package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by alex on 08.12.2015.
 */
public class mLive extends mSimpleSprite {
    private static int type;
    private int numOfLive;
    public static final int SINGLE_PLAY = 1;
    public static final int PLAY_TOGETHER = 2;
    private static int x;


    public mLive(Resources res, int play) {
        super(x, 10, 0, 0, init(res, play));
        numOfLive = 3;
        type = TYPE_LIVE;
    }

    private static Bitmap init (Resources res, int play){
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.live);
        if (play == 1){
            type = SINGLE_PLAY;
            x = 10;
        } else {
            type = PLAY_TOGETHER;
            x = mSettings.CurrentXRes - 120;
        }
        return Bitmap.createScaledBitmap(bmp, 40, 40, false);
    }

    @Override
    void update() {    }

    void update(mPlayerSprite player) {
        numOfLive = player.getLive();
    }

    @Override
    public void draw(Canvas c, Paint p){
        for(int i = 0; i < numOfLive; i++){
            c.drawBitmap(bmp, x + i * 50, y, p);
        }
    }
}
