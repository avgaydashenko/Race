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
    private int type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_menu);
        winter = (CheckBox) findViewById(R.id.winter);
        fon = (ImageView) findViewById(R.id.imageGameMenu);

    }

    public void onClickButtonOnePlayerOption(View view) {
        Intent intent = new Intent(GameMenu.this, OnePlayerOption.class);
        if (type == IS_CHECKED){
            intent.putExtra("winter", IS_CHECKED);
        } else {
            intent.putExtra("winter", NOT_IS_CHECKED);
        }
        startActivity(intent);
    }

    public void onClickWinter (View view){
        if (winter.isChecked()){
            fon.setImageResource(R.drawable.game_menu2);
            type = IS_CHECKED;
        } else {
            fon.setImageResource(R.drawable.game_menu);
            type = NOT_IS_CHECKED;
        }
    }

    public void onClickButtonTwoPlayersOption(View view) {
        Intent intent = new Intent(GameMenu.this, TwoPlayersOption.class);
        if (type == IS_CHECKED){
            intent.putExtra("winter", IS_CHECKED);
        } else {
            intent.putExtra("winter", NOT_IS_CHECKED);
        }
        startActivity(intent);
    }

    public void onClickButtonGameInfo(View view) {
        Intent intent = new Intent(GameMenu.this, GameInfo.class);
        if (type == IS_CHECKED){
            intent.putExtra("winter", IS_CHECKED);
        } else {
            intent.putExtra("winter", NOT_IS_CHECKED);
        }
        startActivity(intent);
    }

    public void onClickButtonGameAbout(View view) {
        Intent intent = new Intent(GameMenu.this, GameAbout.class);
        if (type == IS_CHECKED){
            intent.putExtra("winter", IS_CHECKED);
        } else {
            intent.putExtra("winter", NOT_IS_CHECKED);
        }
        startActivity(intent);
    }
}
