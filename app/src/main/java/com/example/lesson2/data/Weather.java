package com.example.lesson2.data;


public class Weather {
    private String weekDay;
    private String date;
    private int imageResourceId;
    private int dayTemperature;
    private int nightTemperature;
    private String description;

    public Weather(String weekDay, String date, int imageResourceId, int dayTemperature, int nightTemperature, String description) {
        this.weekDay = weekDay;
        this.date = date;
        this.imageResourceId = imageResourceId;
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
        this.description = description;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getDate() {
        return date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public String getDescription() {
        return description;
    }
}
