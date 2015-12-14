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
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoPlayersOption extends Activity {
    int numOfTheme;
    ImageView fon;
    private  boolean isSound;
    private CheckBox scaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_option);
        fon = (ImageView) findViewById(R.id.imageTwoPlayersOption);
        numOfTheme = getIntent().getExtras().getInt("theme");
        isSound = getIntent().getExtras().getBoolean("sound");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.players_option2);
        } else {
            fon.setImageResource(R.drawable.players_option);
        }
        scaner = (CheckBox) findViewById(R.id.checkBoxServer);
    }


    public void onClickButtonStartTwoPlayers(View view) {
        Intent intent = new Intent(TwoPlayersOption.this, RoadForTwo.class);
        intent.putExtra("isServer", scaner.isChecked());
        intent.putExtra("theme", numOfTheme);
        intent.putExtra("sound", isSound);
        startActivity(intent);
    }
    public void onClickButtonBackTwoPlayerOption(View view) {
        finish();
    }
}
