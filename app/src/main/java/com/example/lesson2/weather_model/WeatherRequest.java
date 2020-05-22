package com.example.lesson2.weather_model;

public class WeatherRequest {
    private Main main;
    private Wind wind;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
