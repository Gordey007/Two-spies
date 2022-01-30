package com.example.gordey.twospies;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button next, rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        next = findViewById(R.id.next);
        next.setOnClickListener(this);

        rules = findViewById(R.id.rules);
        rules.setOnClickListener(this);

        Spinner levelDifficulty = findViewById(R.id.levelDifficulty);
        // Создание ArrayAdapter, используя массив строк и макет спиннера по умолчанию
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficultyLevels, android.R.layout.simple_spinner_item);
        // Макет, который будет использоваться при появлении списка вариантов
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Приложите адаптер к счетчику
        levelDifficulty.setAdapter(adapter);

        // AdMob
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView menu_banner = findViewById(R.id.menu_banner);
        menu_banner.loadAd(adRequest);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                final EditText contPlayers = findViewById(R.id.contPlayers);
                final EditText countSpy = findViewById(R.id.countSpy);
                final EditText time = findViewById(R.id.time);
                final Spinner levelDifficulty = findViewById(R.id.levelDifficulty);

                Toast toast;

                if((contPlayers.getText().toString().length() < 1) ||
                        (countSpy.getText().toString().length() < 1) ||
                        (time.getText().toString().length() < 1)) {
                    toast = Toast.makeText(getApplicationContext(), "Заполните поля ;)",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (Integer.parseInt(contPlayers.getText().toString()) <=
                        Integer.parseInt(countSpy.getText().toString())) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Игроков должно быть больше чем шпионов", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (Integer.parseInt(contPlayers.getText().toString()) < 1 ||
                        Integer.parseInt(countSpy.getText().toString()) < 1) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Количество игроков и шпионов должно быть больше 0",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (Integer.parseInt(time.getText().toString()) < 1 ||
                        Integer.parseInt(time.getText().toString()) > 60) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Время должно быть больше 0 и меньше 61", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    int contPlayersInt = Integer.parseInt(contPlayers.getText().toString());
                    int countSpyInt = Integer.parseInt(countSpy.getText().toString());
                    int timeInt = Integer.parseInt(time.getText().toString());

                    Intent intent = new Intent(this, AcquaintanceActivity.class);
                    intent.putExtra("contPlayers", contPlayersInt);
                    intent.putExtra("countSpy", countSpyInt);
                    intent.putExtra("time", timeInt);
                    intent.putExtra("levelDifficulty", levelDifficulty.getSelectedItem().
                            toString());
                    startActivity(intent);
                }
                break;

            case R.id.rules:
                Intent intent2 = new Intent(this, RulesActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
