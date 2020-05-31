package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    // Имя настроек
    private static final String ThemePreference = "THEME";
    private static final String CityPreference = "CITY";

    // Имя параметра в настройках
    private static final String IsDarkTheme = "IS_DARK_THEME";
    private static final String CurrentCity = "CURRENT_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    // Чтение настроек, параметр тема
    protected boolean isDarkTheme() {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(ThemePreference, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию true
        return sharedPref.getBoolean(IsDarkTheme, true);
    }

    // Сохранение настроек
    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(ThemePreference, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsDarkTheme, isDarkTheme);
        editor.apply();
    }

    protected void saveCurrentCity(String city) {
        SharedPreferences sharedPref = getSharedPreferences(CityPreference, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CurrentCity, city);
        editor.apply();
    }

    protected String loadCurrentCity(){
        SharedPreferences sharedPref = getSharedPreferences(CityPreference, MODE_PRIVATE);
        return sharedPref.getString(CurrentCity, "");
    }


}
