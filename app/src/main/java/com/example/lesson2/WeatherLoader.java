package com.example.lesson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

public class WeatherLoader {

    private cityListener cityListener;

    interface cityListener {
        void negativeAction(String message);
    }

    public void setCityListener(cityListener cityListener) {
        this.cityListener = cityListener;
    }

    public BufferedReader getWeather(String url, final String cityErrorMsg, final String connectionErrorMsg) {
        try {
            final URL uri = new URL(url);
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                BufferedReader resBufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                return resBufferedReader;
            } catch (UnknownHostException e) {
                cityListener.negativeAction(connectionErrorMsg);
            } catch (IOException e) {
                cityListener.negativeAction(cityErrorMsg);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
        }
        return null;
    }
}