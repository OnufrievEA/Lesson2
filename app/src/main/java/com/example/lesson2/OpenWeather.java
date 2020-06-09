package com.example.lesson2;

import com.example.lesson2.weather_model.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeatherByCity(@Query("q") String cityCountry, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeatherByCoords(@Query("lat") double lat, @Query("lon") double lng, @Query("appid") String keyApi);
}
