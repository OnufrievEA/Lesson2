package com.example.lesson2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.lesson2.SettingsActivity.myLog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myTag: " + MainActivity.class.getName();
    private EditText cityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continueBtn = findViewById(R.id.continueBtn);
        cityET = findViewById(R.id.cityET);
        continueBtn.setOnClickListener(continueBtnListener);

        myLog(getApplicationContext(), TAG, "onCreate");
    }

    View.OnClickListener continueBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            String message = String.valueOf(cityET.getText());
            intent.putExtra(WeatherActivity.CITY, message);
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
