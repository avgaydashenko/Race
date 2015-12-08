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


public class RoadForTwo extends Activity {
    public static final String TAG =  RoadForOne.class.getSimpleName();

    TwoPlayerGameView gameView;
    SceneManager sceneManager;
    SensorManager sensorManager;
    Sensor sensor;
    Button pause;
    Button resum;

    public RoadForTwo() {
       pause = (Button) findViewById(R.id.buttonPause);
       resum = (Button) findViewById(R.id.buttonResum);
       resum.setClickable(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_road_for_two);
        gameView = (TwoPlayerGameView)  findViewById(R.id.game_view);

        mScene scene = new mScene(getResources(), mScene.PLAY_TOGETHER);
        scene.width = gameView.getWidth();
        scene.height = gameView.getHeight();
        scene.player_id = mScene.FINN;
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

    public void onClickButtonPause(View view){
        onPause();
        pause.setClickable(false);
    }

    public void onClickButtonResum(View view){
        onResume();
        resum.setClickable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sceneManager);
        sceneManager.stop();
    }
}
