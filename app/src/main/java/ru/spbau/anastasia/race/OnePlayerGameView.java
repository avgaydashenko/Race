package ru.spbau.anastasia.race;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TODO: document your custom view class.
 */
public class OnePlayerGameView extends View {
    protected mScene scene;
    protected Paint mainPaint;
    protected Bitmap fon;

    private static final String TAG = "OnePlayerGameView";
    boolean initialized = false;

    public OnePlayerGameView(Context context) {
        super(context);
        init();
        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public OnePlayerGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void init() {
        mSettings.GenerateSettings(getWidth(), getHeight());
        fon = BitmapFactory.decodeResource(getResources(), R.drawable.game_road);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(scene == null)
            return;

        scene.setWH(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        mSettings.GenerateSettings(scene.width, scene.height);
        fon = Bitmap.createScaledBitmap(fon, mSettings.CurrentXRes, mSettings.CurrentYRes, false);

        scene.initScene();
        scene.start();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (scene == null) {
            return;
        }
        Log.d(TAG, "onDraw started");
        synchronized (scene) {
            canvas.drawBitmap(fon, 0, 0, mainPaint);
            for (mLayer l : scene.layers) {
                if (l != null) {
                    for (mBasic tmp : l.data) {
                        tmp.draw(canvas, mainPaint);
                        Log.d(TAG, String.valueOf(tmp.getX()));
                    }
                }
            }
            scene.player.draw(canvas, mainPaint);
            scene.live.draw(canvas, mainPaint);
            canvas.drawText(String.valueOf((int) scene.count), mSettings.CurrentXRes / 2, 50, mainPaint);
        }
        Log.d(TAG, "onDraw finished");
        invalidate();
    }
}
