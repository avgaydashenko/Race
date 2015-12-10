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


public class GameInfo extends Activity {

    WinterTextView t;
    ImageView fon;
    int numOfTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        t = (WinterTextView) findViewById(R.id.winterTextView);
        t.setMovementMethod(new ScrollingMovementMethod());
        fon = (ImageView) findViewById(R.id.imageGameInfo);
        numOfTheme = getIntent().getExtras().getInt("winter");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.game_info2);
            t.setIsWinterInfo(true);

        } else {
            fon.setImageResource(R.drawable.game_info);
            t.setIsWinterInfo(false);
        }
    }


        public void onClickButtonBackGameInfo(View view) {
        finish();
    }

}
