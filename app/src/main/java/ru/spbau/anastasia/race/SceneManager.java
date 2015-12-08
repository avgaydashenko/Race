package ru.spbau.anastasia.race;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SceneManager implements SensorEventListener {

    private static final String TAG = "SceneManager";
    public static final int FPS = 10;

    mScene scene;
    float dx, dy;

    class SceneTask extends TimerTask {
        @Override
        public void run() {
            synchronized (scene){
                if(scene.type == scene.SINGLE_PLAY) {
                    scene.oneStep(dx, dy);
                } else {
                    scene.oneStep(dx, dy, 0, 0);
                }
            }
        }
    }

    SceneTask task;
    Timer timer;

    public SceneManager(mScene scene) {
        this.scene = scene;
        this.task = new SceneTask();
        this.timer = new Timer();
    }

    public void start() {
        timer.schedule(task, 0 , 1000 / FPS);
    }

    public void stop() {
        timer.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        dx = (float)(-Math.sin(Math.toRadians(event.values[1])) * 50);
        dy = (float)(+Math.sin(Math.toRadians(event.values[2])) * 50);

        Log.d(TAG,
                "dx = " + Float.toString(dx) + ", " +
                "dy = " + Float.toString(dy)
        );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
