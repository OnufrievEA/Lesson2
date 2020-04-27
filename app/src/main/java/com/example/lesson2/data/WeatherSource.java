package com.example.lesson2.data;

import android.content.res.Resources;

import java.util.List;

public class WeatherSource {
    private List<Weather> dataSource;
    private Resources resources;

    public WeatherSource(Resources resources){
        this.resources = resources;
    }
}
