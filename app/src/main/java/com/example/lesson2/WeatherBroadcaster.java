package com.example.lesson2;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.example.lesson2.weather_model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherBroadcaster {



    public void broadcastWeather(String url, final View view) {
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
                                displayWeather(view, weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("неправильный город");
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
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

    private void displayWeather(View view, WeatherRequest weatherRequest) {
        EditText temperature = view.findViewById(R.id.textTemprature);
        EditText pressure = view.findViewById(R.id.textPressure);
        EditText humidity = view.findViewById(R.id.textHumidity);
        EditText windSpeed = view.findViewById(R.id.textWindspeed);
        temperature.setText(String.format("%f2", weatherRequest.getMain().getTemp()));
        pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
        humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
        windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
    }
}
