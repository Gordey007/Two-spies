package com.example.gordey.twospies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    // AdMob
    private RewardedVideoAd mRewardedVideoAd;
    private MediaPlayer attentionSound;

    Button menu;
    TextView timeGame, attention;
    ProgressBar progressBar;

    int flagStopSound;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // Используйте контекст действия, чтобы получить экземпляр видео с вознаграждением.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        // AdMob banner
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView game_banner_0 = findViewById(R.id.game_banner_0);
        game_banner_0.loadAd(adRequest);

        AdView game_banner_1 = findViewById(R.id.game_banner_1);
        game_banner_1.loadAd(adRequest);

        attentionSound = MediaPlayer.create(this, R.raw.attention);

        timeGame = findViewById(R.id.timeGame);
        attention = findViewById(R.id.attention);

        progressBar = findViewById(R.id.progressBar);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        final int[] time = {arguments.getInt("time")};

        timeGame.setText(Integer.toString(time[0]));

        new CountDownTimer(time[0] * 60000L, 1000) {
            @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
            @Override
            public void onTick(long l) {
                Bundle arguments = getIntent().getExtras();
                assert arguments != null;
                int time = arguments.getInt("time");

                if (l / 1000 > 60)
                    timeGame.setText("Осталось времени: " + new SimpleDateFormat("mm:ss").
                            format(new Date(TimeUnit.SECONDS.toMillis(l / 1000))));

                else if (l / 1000 < 60 && l / 1000 > 21){
                    attention.setText("Внимание!");
                    timeGame.setText("Осталось времени: " + new SimpleDateFormat("mm:ss").
                            format(new Date(TimeUnit.SECONDS.toMillis(
                                    l / 1000))));

                }
                else if(l / 1000 < 60 && l / 1000 > 11) {
                    soundPlay(attentionSound);
                    attention.setText("");
                    timeGame.setText("Голосование через: " + new SimpleDateFormat("mm:ss").
                            format(new Date(TimeUnit.SECONDS.toMillis(
                                    l / 1000))));
                }

                else if (l / 1000 < 11){
                    attention.setText("");
                    timeGame.setText("Голосование через: " + new SimpleDateFormat("mm:ss").
                            format(new Date(TimeUnit.SECONDS.toMillis(
                                    l / 1000))));
                }

                progressBar.setMax(time * 60);
                progressBar.setProgress((int) l / 1000);
            }

            @Override
            public void onFinish() {
                timeGame.setText("Кто шпион? \uD83D\uDE42");
                // AdMob
                if (mRewardedVideoAd.isLoaded ()) {
                    mRewardedVideoAd.show ();
                }
            }
        }.start();
    }

    // AdMob video
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    private void soundPlay(MediaPlayer soung) {
        if(flagStopSound != 1)
            soung.start();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu) {
            flagStopSound = 1;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Это будет быстро ☺",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Вот уже началось ☻",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Спасибо за просмотр ☺",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}