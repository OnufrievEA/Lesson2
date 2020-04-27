package com.example.lesson2.data;


public class Weather {
    private String weekDay;
    private String date;
    private int imageResourceId;
    private String dayTemperature;
    private String nightTemperature;
    private String description;

    public Weather(String weekDay, String date, int imageResourceId, String dayTemperature, String nightTemperature, String description) {
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

    public String getDayTemperature() {
        return dayTemperature;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public String getDescription() {
        return description;
    }
}
