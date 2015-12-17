package ru.spbau.anastasia.race;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SceneManager implements SensorEventListener {

    public static final int FPS = 10;

    protected final mScene scene;

    private float dx, dy;

    private SceneTask task;
    private Timer timer;

    private class SceneTask extends TimerTask {
        @Override
        public void run() {
            synchronized (scene){
                if (scene.type == mScene.SINGLE_PLAY) {
                    scene.oneStep(dx, dy);
                }
            }
        }
    }

    public byte [] forTwoPlayer(FileForSent msg){
        synchronized (scene) {

            byte [] bytes;
            if (scene.isServer) {
                bytes = FileForSent.genClient().toMsg();
                try {
                    bytes = scene.oneStepServer(dx, dy, msg).toMsg();
                } catch (NullPointerException ignored) { }
            } else {
                bytes = new byte[5];
                try {
                    bytes = scene.oneStepClient(dx, dy, msg).toMsg();;
                } catch (NullPointerException ignored) { }
            }
            return bytes;
        }
    }

    public SceneManager(mScene scene_) {
        scene = scene_;
        task = new SceneTask();
        timer = new Timer();
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
