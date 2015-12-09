package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;


public class GameAbout extends Activity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_about);

        t = (TextView) findViewById(R.id.gameAbout);
        t.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onClickButtonBackGameAbout(View view) {
        finish();
    }
}
