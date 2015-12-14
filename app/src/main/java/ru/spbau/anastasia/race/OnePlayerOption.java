package ru.spbau.anastasia.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class OnePlayerOption extends Activity {

    public static final int FINN = 0;
    public static final int JAKE = 1;
    private ImageView fon;
    ImageButton finn;
    ImageButton jacke;
    private boolean isSound;

    int player_id;
    int numOfTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_one_player_option);
        fon = (ImageView) findViewById(R.id.imagePlayersOption);
        numOfTheme = getIntent().getExtras().getInt("theme");
        isSound = getIntent().getExtras().getBoolean("sound");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.option_for_one);
        } else {
            fon.setImageResource(R.drawable.players_option);
        }
        finn = (ImageButton) findViewById(R.id.buttonChooseFinn);
        jacke = (ImageButton) findViewById(R.id.buttonChooseJacke);
        finn.setImageResource(R.drawable.choose_finn);
        jacke.setImageResource(R.drawable.choose_jake);


    }

    public void startOnePlayer() {
        Intent intent = new Intent(this, RoadForOne.class);
        intent.putExtra("player", player_id);
        intent.putExtra("theme", numOfTheme);
        intent.putExtra("sound", isSound);
        startActivity(intent);
    }

    public void onClickButtonBackOnePlayerOption(View view) {
        finish();
    }

    protected void chooseCharacter(int string_id, int character) {
        this.player_id = character;
    }

    public void onFinnChosen(View view) {
        chooseCharacter(R.string.finn, 0);
        finn.setImageResource(R.drawable.chosen_finn);
        jacke.setImageResource(R.drawable.choose_jake);
        startOnePlayer();
    }

    public void onJakeChosen(View view) {
        chooseCharacter(R.string.jake, 1);
        finn.setImageResource(R.drawable.choose_finn);
        jacke.setImageResource(R.drawable.chosen_jake);
        startOnePlayer();
    }
}
