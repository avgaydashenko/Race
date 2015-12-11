package ru.spbau.anastasia.race;

import ru.spbau.anastasia.race.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class GameAbout extends Activity {

    WinterTextView t;
    ImageView fon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_about);

        t = (WinterTextView) findViewById(R.id.gameAbout);
        t.setMovementMethod(new ScrollingMovementMethod());
        fon = (ImageView) findViewById(R.id.imageGameAbout);
        int numOfTheme = getIntent().getExtras().getInt("winter");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.game_rules2);
            t.setText(R.string.game_about2);
        } else {
            fon.setImageResource(R.drawable.dark_pic_3);
            t.setText(R.string.game_about);
        }
    }

    public void onClickButtonBackGameAbout(View view) {
        finish();
    }
}
