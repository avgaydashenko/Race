package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameMenu extends Activity {

    private CheckBox chooseWinterTheme;
    private ImageButton soundButton;
    private ImageView backgroundImage;

    public static final int IS_CHECKED = 1;
    public static final int NOT_IS_CHECKED = 0;
    public static final int MENU_ACTIVITY = 100;

    private int numOfTheme = 0;
    private boolean isSound;
    private Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        chooseWinterTheme = (CheckBox) findViewById(R.id.winter);
        backgroundImage = (ImageView) findViewById(R.id.imageGameMenu);

        soundButton = (ImageButton) findViewById(R.id.buttonSound);
        isSound = true;
        numOfTheme = NOT_IS_CHECKED;
        sound = new Sound(getAssets(), numOfTheme, MENU_ACTIVITY);
        sound.isStoped = !isSound;
    }

    public void onClickButtonOnePlayerOption(View view) {
        startActivity(new Intent(GameMenu.this, OnePlayerOption.class));
    }

    public void onClickChooseWinterTheme(View view) {
        if (chooseWinterTheme.isChecked()) {
            backgroundImage.setImageResource(R.drawable.game_menu2);
            numOfTheme = IS_CHECKED;
        } else {
            backgroundImage.setImageResource(R.drawable.game_menu);
            numOfTheme = NOT_IS_CHECKED;
        }
        sound.theme = numOfTheme;
    }

    public void onClickSound(View view) {
        if (isSound) {
            soundButton.setImageResource(R.drawable.no_sound);
        } else {
            soundButton.setImageResource(R.drawable.sound);
        }

        isSound = !isSound;
        sound.isStoped = !isSound;
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
        intent.putExtra("theme", numOfTheme);
        intent.putExtra("sound", isSound);
        super.startActivity(intent);
    }
}
