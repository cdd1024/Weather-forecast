package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChooseActivity extends AppCompatActivity {
    private CardView cdWeather;
    private CardView cdMusic;
    private CardView cdPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        cdWeather = (CardView) findViewById(R.id.cd_weather);
        cdMusic = (CardView) findViewById(R.id.cd_music);
        cdPerson = (CardView) findViewById(R.id.cd_person);
        cdWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });
        cdMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });
        cdPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this,PersonActivity.class);
                startActivity(intent);
            }
        });
    }
}