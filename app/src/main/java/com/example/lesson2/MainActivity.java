package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button continueBtn;
    private EditText cityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continueBtn = findViewById(R.id.continueBtn);
        cityET = findViewById(R.id.cityET);
        continueBtn.setOnClickListener(continueBtnListener);
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




}
