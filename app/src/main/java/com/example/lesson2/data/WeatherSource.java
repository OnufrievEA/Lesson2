package com.example.lesson2.data;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.lesson2.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherSource {
    private List<Weather> dataSource;
    private Resources resources;

    public WeatherSource(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public WeatherSource initData() {
        String[] weekDays = resources.getStringArray(R.array.weekDays);
        String[] dates = resources.getStringArray(R.array.dates);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dayTemperatures = resources.getStringArray(R.array.dayTemperatures);
        String[] nightTemperatures = resources.getStringArray(R.array.nightTemperatures);
        int[] pictures = getImageArray();

        for (int i = 0; i < weekDays.length; i++) {
            dataSource.add(new Weather(weekDays[i],
                    dates[i],
                    pictures[i],
                    dayTemperatures[i],
                    nightTemperatures[i],
                    descriptions[i]));
        }

        return this;
    }

    public Weather getWeather(int position) {
        return dataSource.get(position);
    }

    public int getSize() {
        return dataSource.size();
    }

    private int[] getImageArray() {
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }
}
