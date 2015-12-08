package ru.spbau.anastasia.race;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by alex on 08.12.2015.
 */
public class TwoPlayerGameView extends OnePlayerGameView {

    private static final String TAG = "TwoPlayerGameView";

    public TwoPlayerGameView(Context context) {
        super(context);
    }

    public TwoPlayerGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw started");

        synchronized (scene){
            scene.player2.draw(canvas, mainPaint);
            scene.live2.draw(canvas, mainPaint);
        }
        Log.d(TAG, "onDraw finished");
        invalidate();
    }
}
