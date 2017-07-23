package com.bryancapps.blackjack.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bryancapps.blackjack.R;


//@EFBO: MainActivityClass hasInteractionWith client-agent
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //@EFBO: "Event_Start" hasNextEvent "Display Main Activity Screen"
        //@EFBO: MainActivityClass isInterfaceOf "Display Main Activity Screen"
        setContentView(R.layout.activity_main);

        //@EFBO playButton isInterfaceOf "Click Play Button"
        Button playButton = (Button) findViewById(R.id.button_play);

        //@EFBO: "Display Main Activity Screen" hasNextEvent "Click Play Button"
        //@EFBO: playButton hasInteractionWith user-agent
        playButton.setOnClickListener(v -> play());
    }

    private void play() {
        //@EFBO: "Click Play Button" hasNextEvent "Display Game Activity Screen"
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finish();
    }

}
