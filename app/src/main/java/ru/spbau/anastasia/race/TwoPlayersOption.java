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
import android.widget.ImageView;
import android.widget.TextView;

public class TwoPlayersOption extends Activity {
    int numOfTheme;
    ImageView fon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_option);
        fon = (ImageView) findViewById(R.id.imageTwoPlayersOption);
        numOfTheme = getIntent().getExtras().getInt("winter");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.option_for_twojpg);
        } else {
            fon.setImageResource(R.drawable.players_option);
        }
    }
    public void onClickButtonStartTwoPlayers(View view) {
        Intent intent = new Intent(TwoPlayersOption.this, RoadForTwo.class);
        intent.putExtra("winter", numOfTheme);
        startActivity(intent);
    }
    public void onClickButtonBackTwoPlayerOption(View view) {
        finish();
    }
}
