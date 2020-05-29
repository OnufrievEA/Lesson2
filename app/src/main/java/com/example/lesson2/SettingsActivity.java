package com.example.lesson2;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends BaseActivity {

    private boolean themeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            themeChanged = savedInstanceState.getBoolean("THEME");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwitchCompat switchDarkTheme = findViewById(R.id.themeSwitcher);
        switchDarkTheme.setChecked(isDarkTheme());
        switchDarkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDarkTheme(isChecked);
                setResult(RESULT_OK);
                themeChanged = true;
                recreate();
            }
        });

        if (themeChanged) {
            Snackbar.make(findViewById(R.id.themeSwitcher), "Тема изменена", Snackbar.LENGTH_LONG).setAction("Отмена", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = switchDarkTheme.isChecked();
                    switchDarkTheme.setChecked(!isChecked);
                    setResult(RESULT_OK);
                    themeChanged = false;
                    recreate();
                }
            }).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("THEME", themeChanged);
    }
}
