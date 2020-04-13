package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    public static final String CITY = "city";
    private TextView cityTV;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(settingsBtnListener);
        cityTV = findViewById(R.id.cityTV);
        Intent intent = getIntent();
        String city = intent.getStringExtra(CITY);
        cityTV.setText(city);
    }

    View.OnClickListener settingsBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };
}
