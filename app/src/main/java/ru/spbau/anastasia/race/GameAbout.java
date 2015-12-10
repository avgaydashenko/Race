package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class GameAbout extends Activity {

    TextView t;
    ImageView fon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(android.R.layout.activity_game_about);

        t = (TextView) findViewById(android.R.id.gameAbout);
        t.setMovementMethod(new ScrollingMovementMethod());
        fon = (ImageView) findViewById(android.R.id.imageGameAbout);
        int type = getIntent().getExtras().getInt("winter");
        if (type == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.game_rules2);
        } else {
            fon.setImageResource(R.drawable.game_about);
        }
    }

    public void onClickButtonBackGameAbout(View view) {
        finish();
    }
}
