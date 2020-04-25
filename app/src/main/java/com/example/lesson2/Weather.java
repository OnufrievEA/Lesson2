package com.example.lesson2;


import androidx.annotation.NonNull;

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

    public static Weather[] weatherArray = {
            new Weather("Сегодня", "1 мая", R.drawable.iconfinder_sunny_01_1221018, 21, 15, "Ясно"),
            new Weather("ВТ", "2 мая", R.drawable.iconfinder_lightrain_01_1221023, 20, 15, "Небольшой дождь"),
            new Weather("СР", "3 мая", R.drawable.iconfinder_rainnyday_01_1221019, 19, 14, "Дождь"),
            new Weather("ЧТ", "4 мая", R.drawable.iconfinder_thunderstorm_01_1221017, 15, 12, "Гроза"),
            new Weather("ПТ", "5 мая", R.drawable.iconfinder_thunderstorm_ranny_1221016, 15, 13, "Ливень с грозой"),
            new Weather("СБ", "6 мая", R.drawable.iconfinder_rainnyday_1221020, 23, 16, "Пасмурно")
    };

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
