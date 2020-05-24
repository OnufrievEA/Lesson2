package com.example.lesson2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.lesson2.weather_model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.stream.Collectors;

public class WeatherParser {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WeatherRequest parseData(BufferedReader in) {
        if(in != null){
            String result = getLines(in);
            // преобразование данных запроса в модель
            Gson gson = new Gson();
            WeatherRequest resultWeatherRequest = gson.fromJson(result, WeatherRequest.class);
            return resultWeatherRequest;
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
