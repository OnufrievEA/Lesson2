package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class WeatherActivity extends AppCompatActivity {

    public static final String CITY = "city";
    private static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ImageButton settingsBtn = findViewById(R.id.settingsBtn);
        Button searchBtn = findViewById(R.id.searchBtn);
        TextView cityTV = findViewById(R.id.cityTV);

        settingsBtn.setOnClickListener(settingsBtnListener);
        searchBtn.setOnClickListener(searchBtnListener);

        Intent intent = getIntent();
        city = intent.getStringExtra(CITY);
        cityTV.setText(city);
        searchBtn.setText("Search " + city);
    }

    View.OnClickListener settingsBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener searchBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, city);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };
}
