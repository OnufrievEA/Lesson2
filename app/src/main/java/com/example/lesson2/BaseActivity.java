package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.lesson2.receivers.BatteryChangeReceiver;
import com.example.lesson2.receivers.NetworkChangeReceiver;

public abstract class BaseActivity extends AppCompatActivity {

    // Имя настроек
    private static final String ThemePreference = "THEME";
    private static final String CityPreference = "CITY";

    // Имя параметра в настройках
    private static final String IsDarkTheme = "IS_DARK_THEME";
    private static final String CurrentCity = "CURRENT_CITY";

    private BroadcastReceiver networkChangeReceiver;
    private BroadcastReceiver batteryChangeReceiver;
    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        initNotificationChannel();

        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        batteryChangeReceiver = new BatteryChangeReceiver();
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_LOW);
        batteryFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(batteryChangeReceiver, batteryFilter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(batteryChangeReceiver);
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel_1 = new NotificationChannel("1", "name", importance);
            notificationManager.createNotificationChannel(channel_1);
            NotificationChannel channel_2 = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel_2);
        }
    }
}
