package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;


public class RoadForOne extends Activity {

    public static final String TAG =  RoadForOne.class.getSimpleName();

    OnePlayerGameView gameView;
    SceneManager sceneManager;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        int player_id = getIntent().getExtras().getInt("player");
        setContentView(R.layout.activity_road_for_one);
        gameView = (OnePlayerGameView)  findViewById(R.id.game_view);

        mScene scene = new mScene(getResources(), mScene.SINGLE_PLAY);
        scene.width = gameView.getWidth();
        scene.height = gameView.getHeight();
        scene.player_id = player_id;
        sceneManager = new SceneManager(scene);

        gameView.scene = scene;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sceneManager, sensor, SensorManager.SENSOR_DELAY_GAME);
        sceneManager.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sceneManager);
        sceneManager.stop();
    }
}
