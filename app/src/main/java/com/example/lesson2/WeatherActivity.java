package com.example.lesson2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


public class WeatherActivity extends BaseActivity {

    public static final String CITY = "city";
    private static String city;
    private static final int SETTING_CODE = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, SETTING_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_CODE) {
            recreate();
            setResult(RESULT_OK);
        }
    }

}
