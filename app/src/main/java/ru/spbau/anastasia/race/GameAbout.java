package ru.spbau.anastasia.race;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameAbout extends Activity {

    private TextView aboutTextView;
    private ImageView backGroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_about);

        aboutTextView = (TextView) findViewById(R.id.textView);
        aboutTextView.setMovementMethod(new ScrollingMovementMethod());

        backGroundImage = (ImageView) findViewById(R.id.imageGameAbout);
        int numOfTheme = getIntent().getExtras().getInt("theme");

        if (numOfTheme == GameMenu.IS_CHECKED) {
            backGroundImage.setImageResource(R.drawable.game_info2);
        } else {
            backGroundImage.setImageResource(R.drawable.dark_pic_3);
        }
    }

    public void onClickButtonBackGameAbout(View view) {
        finish();
    }
}
