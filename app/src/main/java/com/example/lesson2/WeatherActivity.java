package com.example.lesson2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.lesson2.SettingsActivity.myLog;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "myTag: " + WeatherActivity.class.getName();
    public static final String CITY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ImageButton settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(settingsBtnListener);
        TextView cityTV = findViewById(R.id.cityTV);

        Intent intent = getIntent();
        String city = intent.getStringExtra(CITY);
        cityTV.setText(city);

        myLog(getApplicationContext(), TAG, "onCreate");
    }

    View.OnClickListener settingsBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onRestart() {
        super.onRestart();
        myLog(getApplicationContext(), TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        myLog(getApplicationContext(), TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLog(getApplicationContext(), TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLog(getApplicationContext(), TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLog(getApplicationContext(), TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myLog(getApplicationContext(), TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        myLog(getApplicationContext(), TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myLog(getApplicationContext(), TAG, "onRestoreInstanceState");
    }

}
