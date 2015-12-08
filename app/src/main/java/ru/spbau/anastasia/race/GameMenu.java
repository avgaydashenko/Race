package ru.spbau.anastasia.race;

import ru.spbau.anastasia.race.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


public class GameMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_menu);


    }

    public void onClickButtonOnePlayerOption(View view) {
        Intent intent = new Intent(GameMenu.this, OnePlayerOption.class);
        startActivity(intent);
    }

    public void onClickButtonTwoPlayersOption(View view) {
        Intent intent = new Intent(GameMenu.this, TwoPlayersOption.class);
        startActivity(intent);
    }

    public void onClickButtonGameInfo(View view) {
        Intent intent = new Intent(GameMenu.this, GameInfo.class);
        startActivity(intent);
    }

    public void onClickButtonGameAbout(View view) {
        Intent intent = new Intent(GameMenu.this, GameAbout.class);
        startActivity(intent);
    }
}
