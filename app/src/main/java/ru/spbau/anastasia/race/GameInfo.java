package ru.spbau.anastasia.race;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameInfo extends Activity {
    
    private TextView rulesTextView;
    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        rulesTextView = (TextView) findViewById(R.id.textView);
        rulesTextView.setMovementMethod(new ScrollingMovementMethod());

        backgroundImage = (ImageView) findViewById(R.id.imageGameInfo);
        int numOfTheme = getIntent().getExtras().getInt("theme");

        if (numOfTheme == GameMenu.IS_CHECKED) {
            backgroundImage.setImageResource(R.drawable.game_info2);
        } else {
            backgroundImage.setImageResource(R.drawable.dark_pic_4);
        }
    }

    public void onClickButtonBackGameInfo(View view) {
        finish();
    }
}
