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
    TextView t;
    ImageView fon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_about);

        t = (TextView) findViewById(R.id.textView);
        t.setMovementMethod(new ScrollingMovementMethod());
        fon = (ImageView) findViewById(R.id.imageGameAbout);
        int numOfTheme = getIntent().getExtras().getInt("theme");
        if (numOfTheme == GameMenu.IS_CHECKED) {
            fon.setImageResource(R.drawable.game_info2);
        } else {
            fon.setImageResource(R.drawable.dark_pic_3);
        }
    }

    public void onClickButtonBackGameAbout(View view) {
        finish();
    }
}
