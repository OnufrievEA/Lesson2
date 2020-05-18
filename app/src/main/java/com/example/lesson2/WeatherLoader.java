package com.example.lesson2;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.example.lesson2.weather_model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherLoader {

    private cityListener cityListener;

    interface cityListener {
        void negativeAction();
        void positiveAction(WeatherRequest weatherRequest);
    }

    public void setCityListener(cityListener cityListener) {
        this.cityListener = cityListener;
    }

    public void getWeather(String url) {
        try {
            final URL uri = new URL(url);
            final Handler handler = new Handler(); // Запоминаем основной поток
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

//                         Возвращаемся к основному потоку
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                cityListener.positiveAction(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        cityListener.negativeAction();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        } else {
                            cityListener.negativeAction();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
