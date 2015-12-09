package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class RoadForOne extends Activity {

    public static final String TAG =  RoadForOne.class.getSimpleName();
    OnePlayerGameView gameView;
    SceneManager sceneManager;
    SensorManager sensorManager;
    Sensor sensor;
    ImageButton pause;
    View.OnClickListener onPauseListener, onResumeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        int player_id = getIntent().getExtras().getInt("player");
        setContentView(R.layout.activity_road_for_one);

        gameView = (OnePlayerGameView)  findViewById(R.id.game_view);

        final mScene scene = new mScene(getResources(), mScene.SINGLE_PLAY);
        scene.width = gameView.getWidth();
        scene.height = gameView.getHeight();
        scene.player_id = player_id;
        sceneManager = new SceneManager(scene);

        gameView.scene = scene;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        pause = (ImageButton) findViewById(R.id.pause);

        onPauseListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.stop();
                pause.setImageResource(R.drawable.back);
                pause.setOnClickListener(onResumeListener);
            }
        };

        onResumeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.start();
                pause.setImageResource(R.drawable.pause);
                pause.setOnClickListener(onPauseListener);
            }
        };

        pause.setOnClickListener(onPauseListener);
    }

    public void onBackButtonClickRoadForOne(View view) {
        finish();
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
