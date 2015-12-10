package ru.spbau.anastasia.race;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SceneManager implements SensorEventListener {

    public static final int FPS = 10;

    final mScene scene;
    float dx, dy;

    class SceneTask extends TimerTask {
        @Override
        public void run() {
            synchronized (scene){
                if(scene.type == mScene.SINGLE_PLAY) {
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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
