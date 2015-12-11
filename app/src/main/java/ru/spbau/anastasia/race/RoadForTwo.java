package ru.spbau.anastasia.race;

import ru.spbau.anastasia.race.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.ImageButton;


public class RoadForTwo extends Activity implements mScene.SceneListener {

    TwoPlayerGameView gameView;
    SceneManager sceneManager;
    SensorManager sensorManager;
    Sensor sensor;
    ImageButton pause;
    mScene scene;
    int numOfTheme;
    private boolean isServer;

    View.OnClickListener onPauseListener, onResumeListener;

    Runnable activateRestartButton = new Runnable() {
        @Override
        public void run() {
            pause.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numOfTheme = getIntent().getExtras().getInt("winter");
        isServer = getIntent().getExtras().getBoolean("isServer");

        setContentView(R.layout.activity_road_for_two);
        gameView = (TwoPlayerGameView)  findViewById(R.id.game_view);

        scene = new mScene(getResources(), mScene.PLAY_TOGETHER, numOfTheme);
        scene.width = gameView.getWidth();
        scene.height = gameView.getHeight();
        scene.player_id = mScene.FINN;
        scene.isServer = isServer;
        sceneManager = new SceneManager(scene);
        gameView.scene = scene;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        pause = (ImageButton) findViewById(R.id.buttonPause);

        onPauseListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.stop();
                pause.setImageResource(R.drawable.play);
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
        pause.setOnClickListener(onPauseListener);
        gameView.initFon(numOfTheme);
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
    public void onRestartButtonClick(View view) {}

    public void onClickButtonBackRoadForTwo(View view) {
        finish();
    }
}
