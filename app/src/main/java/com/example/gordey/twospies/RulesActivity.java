package com.example.gordey.twospies;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class RulesActivity extends AppCompatActivity implements View.OnClickListener{

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        // AdMob
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView banner_rules_0 = findViewById(R.id.banner_rules_0);
        banner_rules_0.loadAd(adRequest);

        AdView banner_rules_1 = findViewById(R.id.banner_rules_1);
        banner_rules_1.loadAd(adRequest);

        AdView banner_rules_2 = findViewById(R.id.banner_rules_2);
        banner_rules_2.loadAd(adRequest);

        AdView banner_rules_3 = findViewById(R.id.banner_rules_3);
        banner_rules_3.loadAd(adRequest);

        AdView banner_rules_4 = findViewById(R.id.banner_rules_4);
        banner_rules_4.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}