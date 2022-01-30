package com.example.gordey.twospies;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//import android.util.Log;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Date;
import java.util.Random;

public class AcquaintanceActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String MyTag = "MyTag";

    Button hideBtn, nextBtn, menu;
    TextView location, txtTutorial;

    int contPlayersInt, countSpyInt, timeInt;
    int sum, sum1, playersInt, start = 0;

    String locationStr, complexity;

    int[] players;

    String[] locationArr;

    Random rand = new Random();

    int randSpy, randLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquaintance);

        // AdMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView location_banner = findViewById(R.id.location_banner);
        location_banner.loadAd(adRequest);

        hideBtn = findViewById(R.id.hideBtn);
        hideBtn.setOnClickListener(this);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);

        location = findViewById(R.id.location);
        location.setOnClickListener(this);

        txtTutorial = findViewById(R.id.txtTutorial);
        txtTutorial.setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        int contPlayers = arguments.getInt("contPlayers");
        int countSpy = arguments.getInt("countSpy");
        int time = arguments.getInt("time");
        String levelDifficulty = arguments.getString("levelDifficulty");
        players = new int[contPlayers];

        contPlayersInt = contPlayers;
        countSpyInt = countSpy;
        timeInt = time;
        complexity = levelDifficulty;

        assert complexity != null;
        switch (complexity){
            case "Легкий":
                locationArr = getResources().getStringArray(R.array.easy);
                break;
            case "Средний":
                locationArr = getResources().getStringArray(R.array.middle);
                break;
            case "Тяжелый":
                locationArr = getResources().getStringArray(R.array.hard);
                break;
        }

        int min = rand.nextInt(locationArr.length) + 1;
        int max = rand.nextInt(locationArr.length);

        if(min == max && min != 0)
            min--;
        if(min == 0 && max == 0)
            max++;
        if (min > max){
            int min2 = max;
            max = min;
            min = min2;
        }

        long seconds_now = System.currentTimeMillis();
        int rnd_seconds_now = rand.nextInt(5);
        String seconds_now_str = Long.toString(seconds_now);

        int add_path_seconds_now = Integer.parseInt(String.valueOf(
                seconds_now_str.charAt(seconds_now_str.length() - (rnd_seconds_now + 1))));

        int rnd_for = rand.nextInt(10);

        for (int i_rnd_for = 0; i_rnd_for <= rnd_for; i_rnd_for++){
            randLocation = min + rand.nextInt(max - min) + add_path_seconds_now;

            while (randLocation > locationArr.length)
                randLocation = randLocation - rand.nextInt(10);

            locationStr = locationArr[randLocation - 1];
        }

        for (int i = 1; i <= countSpyInt; i++) {
            for (int player : players) sum1 = sum1 + player;

            rnd_for = rand.nextInt(4);

            for (int i_rnd_for = 0; i_rnd_for <= rnd_for; i_rnd_for++) {
                rnd_seconds_now = rand.nextInt(4);
                seconds_now_str = Long.toString(seconds_now);
                add_path_seconds_now = Integer.parseInt(String.valueOf(
                        seconds_now_str.charAt(seconds_now_str.length() - (rnd_seconds_now + 1))));

                randSpy = rand.nextInt(contPlayersInt + 1) + add_path_seconds_now;

                int randSpy_minus = 0;

                while (randSpy > contPlayersInt) {
                    for(int i_randSpy = 0; i_randSpy <= rnd_for; i_randSpy++) {
                        randSpy_minus = rand.nextInt(5);
                        Log.d(MyTag, "1 randSpy_minus - " + randSpy_minus);
                    }
                    randSpy = randSpy - randSpy_minus;
                }
            }

            if (randSpy == 0)
                randSpy++;

            if (players[randSpy-1] == 1){
                do {
                    sum = 0;

                    for (int i_rnd_for = 0; i_rnd_for <= rnd_for; i_rnd_for++) {
                        rnd_seconds_now = rand.nextInt(4);
                        seconds_now_str = Long.toString(seconds_now);
                        add_path_seconds_now = Integer.parseInt(String.valueOf(seconds_now_str.
                                charAt(seconds_now_str.length() - (rnd_seconds_now + 1))));

                        randSpy = rand.nextInt(contPlayersInt + 1) + add_path_seconds_now;

                        while (randSpy > contPlayersInt)
                            randSpy = randSpy - rand.nextInt(5);
                    }

                    if (randSpy == 0) randSpy++;

                    players[randSpy - 1] = 1;

                    for (int player : players) sum = sum + player;

                } while(sum1 == sum);
                sum1 = 0;
                sum = 0;
            }
            else {
                players[randSpy - 1] = 1;
                sum1 = 0;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hideBtn:
                location.setText("");
                break;
            case R.id.nextBtn:
                if (playersInt != contPlayersInt){
                    if (players[playersInt] == 1) {
                        location.setText("Поздравляю Вы шпион.\nДелайте вид, что Вы не шпион ;)");
                        location.setGravity(Gravity.CENTER);
                    }else {
                        location.setText(locationStr);
                    }
                    playersInt++;
                }
                if (playersInt == contPlayersInt) {
                    nextBtn.setText("Начать");
                    txtTutorial.setText("Теперь нажмите \"Начать\"");
                }
                if(start == contPlayersInt) {
                    Bundle arguments = getIntent().getExtras();
                    assert arguments != null;
                    int time = arguments.getInt("time");

                    Intent intent = new Intent(this, GameActivity.class);
                    intent.putExtra("time", time);
                    startActivity(intent);
                }
                start++;
                break;
            case R.id.menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}