package com.example.lesson2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "myTag: " + SettingsActivity.class.getName();
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState != null) {
            //Состояние чекбоксов и radioButtonов android восстановит сам. Достаточно задать атрибут id этим компонентам
            counter = savedInstanceState.getInt("counter");
        }


        final TextView tv1 = findViewById(R.id.tv1);
        final TextView tv2 = findViewById(R.id.tv2);
        final MyPresenter presenter = MyPresenter.getInstance();

        tv1.setText(String.valueOf(counter));
        tv2.setText(String.valueOf(presenter.getCounter()));
        Button incrementBtn = findViewById(R.id.incrementBtn);
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                tv1.setText(String.valueOf(counter));
                presenter.incrementCounter();
                tv2.setText(String.valueOf(presenter.getCounter()));
            }
        });

        myLog(getApplicationContext(), TAG, "onCreate");


    }

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
        outState.putInt("counter", counter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myLog(getApplicationContext(), TAG, "onRestoreInstanceState");
    }

    public static void myLog(Context context, String tag, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.d(tag, message);
    }
}
