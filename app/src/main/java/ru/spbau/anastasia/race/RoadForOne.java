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
import android.widget.TextView;


public class RoadForOne extends Activity implements mScene.SceneListener {

    public static final String TAG =  RoadForOne.class.getSimpleName();
    OnePlayerGameView gameView;
    SceneManager sceneManager;
    SensorManager sensorManager;
    Sensor sensor;
    ImageButton pause, restart;
    View.OnClickListener onPauseListener, onResumeListener;

    Runnable activateRestartButton = new Runnable() {
        @Override
        public void run() {
            restart.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
        }
    };


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
        restart = (ImageButton) findViewById(R.id.restart);


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

        scene.sceneListener = this;

        restart.setVisibility(View.GONE);
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

    @Override
    public void onGameOver() {
        runOnUiThread(activateRestartButton);
    }

    public void onRestartButtonClick(View view) {
        sceneManager.scene.restart();
        restart.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
    }
}
