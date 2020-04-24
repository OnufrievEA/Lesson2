package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements CityFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContinueBtnClicked(String city) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            DetailFragment detailFragment = new DetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            detailFragment.setCity(city);
            ft.replace(R.id.fragment_container, detailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra(WeatherActivity.CITY, city);
            startActivity(intent);
        }
    }

}
