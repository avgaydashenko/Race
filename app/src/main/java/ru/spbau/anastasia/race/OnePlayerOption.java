package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class OnePlayerOption extends Activity {

    public static final int FINN = 0;
    public static final int JAKE = 1;

    int player_id;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_one_player_option);
        t = (TextView) findViewById(R.id.choose_text);
        chooseCharacter(R.string.finn, 0);
    }

    public void onClickButtonStartOnePlayer(View view) {
        Intent intent = new Intent(this, RoadForOne.class);
        intent.putExtra("player", player_id);
        startActivity(intent);
    }

    public void onClickButtonBack(View view) {
        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }

    protected void chooseCharacter(int string_id, int character) {
        t.setText(getResources().getString(R.string.choose_information) + getResources().getString(string_id));
        this.player_id = character;
    }

    public void onFinnChosen(View view) {
        chooseCharacter(R.string.finn, 0);
    }

    public void onJakeChosen(View view) {
        chooseCharacter(R.string.jake, 1);
    }
}
