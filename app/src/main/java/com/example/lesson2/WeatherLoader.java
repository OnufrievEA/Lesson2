package com.example.lesson2;

import android.util.Log;

import com.example.lesson2.weather_model.WeatherRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherLoader {

    OpenWeather openWeather;
    Retrofit retrofit;
    private cityListener cityListener;

    interface cityListener {
        void negativeAction(String message);

        void positiveAction(Response<WeatherRequest> response);
    }

    public void setCityListener(cityListener cityListener) {
        this.cityListener = cityListener;
    }

    public void getWeather(String city, String apikey, String cityErrorMsg, String connectionsErrorMsg) {
        initRetorfit();
        requestRetrofit(city, apikey, cityErrorMsg, connectionsErrorMsg);
    }

    private void initRetorfit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/") // Базовая часть
                    // адреса
                    // Конвертер, необходимый для преобразования JSON
                    // в объекты
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        // Создаём объект, при помощи которого будем выполнять запросы
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String city, String keyApi, String cityErrorMsg, String connectionsErrorMsg) {
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful() && cityListener != null) {
                            cityListener.positiveAction(response);
                            Log.i("MYTAG", "OK");
                        }
                        if (!response.isSuccessful() && response.errorBody() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String error = jsonObject.getString("message");
                                if (error.equals("city not found")) {
                                    cityListener.negativeAction(cityErrorMsg);
                                } else {
                                    cityListener.negativeAction(error);
                                }

                            } catch (IOException | JSONException e) {
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        if (cityListener != null) {
                            cityListener.negativeAction(connectionsErrorMsg);
                        }
                    }
                });
    }
}