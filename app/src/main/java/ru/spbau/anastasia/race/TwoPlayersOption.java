package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public class TwoPlayersOption extends Activity {

    private int numOfTheme;
    private boolean isSound;
    private CheckBox scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_option);
        ImageView fon = (ImageView) findViewById(R.id.imageTwoPlayersOption);
        numOfTheme = getIntent().getExtras().getInt("theme");
        isSound = getIntent().getExtras().getBoolean("sound");

        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.two_players_option2);
        } else {
            fon.setImageResource(R.drawable.two_players_option);
        }

        scanner = (CheckBox) findViewById(R.id.checkBoxServer);
    }

    public void onClickButtonStartTwoPlayers(View view) {
        Intent intent = new Intent(TwoPlayersOption.this, RoadForTwo.class);
        intent.putExtra("isServer", scanner.isChecked());
        intent.putExtra("theme", numOfTheme);
        intent.putExtra("sound", isSound);
        startActivity(intent);
    }

    public void onClickButtonBackTwoPlayerOption(View view) {
        finish();
    }
}
