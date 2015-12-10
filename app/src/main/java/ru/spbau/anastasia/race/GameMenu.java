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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;


public class GameMenu extends Activity {

    private CheckBox winter;
    private ImageView fon;
    public static final int IS_CHECKED = 1;
    public static final int NOT_IS_CHECKED = 0;
    private int numOfTheme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_menu);
        winter = (CheckBox) findViewById(R.id.winter);
        fon = (ImageView) findViewById(R.id.imageGameMenu);

    }

    public void onClickButtonOnePlayerOption(View view) {
        startActivity(new Intent(GameMenu.this, OnePlayerOption.class));
    }

    public void onClickWinter (View view){
        if (winter.isChecked()){
            fon.setImageResource(R.drawable.game_menu2);
            numOfTheme = IS_CHECKED;
        } else {
            fon.setImageResource(R.drawable.game_menu);
            numOfTheme = NOT_IS_CHECKED;
        }
    }

    public void onClickButtonTwoPlayersOption(View view) {
        startActivity(new Intent(GameMenu.this, TwoPlayersOption.class));
    }

    public void onClickButtonGameInfo(View view) {
        startActivity(new Intent(GameMenu.this, GameInfo.class));
    }

    public void onClickButtonGameAbout(View view) {
        startActivity(new Intent(GameMenu.this, GameAbout.class));
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("winter", numOfTheme);
        super.startActivity(intent);
    }


}
