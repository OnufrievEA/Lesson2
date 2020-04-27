package com.example.lesson2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class WeatherActivity extends AppCompatActivity {

    public static final String CITY = "city";
    private static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        city = getIntent().getStringExtra(CITY);

        DetailFragment frag = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        frag.setCity(city);


//        ImageButton settingsBtn = findViewById(R.id.settingsBtn);
//        Button searchBtn = findViewById(R.id.searchBtn);
//        TextView cityTV = findViewById(R.id.cityTV);
//
//        settingsBtn.setOnClickListener(settingsBtnListener);
//        searchBtn.setOnClickListener(searchBtnListener);
//
//        Intent intent = getIntent();
//        city = intent.getStringExtra(CITY);
//        cityTV.setText(city);
//        searchBtn.setText("Search " + city);
    }

}
