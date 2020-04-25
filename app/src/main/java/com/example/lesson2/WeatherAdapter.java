package com.example.lesson2;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private String[] weekDays;
    private String[] dates;
    private int[] imageResourceIds;
    private int[] dayTemperatures;
    private int[] nightTemperatures;

    public WeatherAdapter(String[] weekDays, String[] dates, int[] imageResourceIds, int[] dayTemperatures, int[] nightTemperatures, String[] descriptions) {
        this.weekDays = weekDays;
        this.dates = dates;
        this.imageResourceIds = imageResourceIds;
        this.dayTemperatures = dayTemperatures;
        this.nightTemperatures = nightTemperatures;
        this.descriptions = descriptions;
    }

    private String[] descriptions;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_weather_broadcast, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView week_day = (TextView) cardView.findViewById(R.id.week_day);
        TextView date = (TextView) cardView.findViewById(R.id.date);
        ImageView weather_image = (ImageView) cardView.findViewById(R.id.weather_image);
        TextView day_temp = (TextView) cardView.findViewById(R.id.day_temp);
        TextView night_temp = (TextView) cardView.findViewById(R.id.night_temp);

        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageResourceIds[position]);
        weather_image.setImageDrawable(drawable);

        week_day.setText(weekDays[position]);
        date.setText(dates[position]);
        day_temp.setText(dayTemperatures[position]);
        night_temp.setText(nightTemperatures[position]);
    }

    @Override
    public int getItemCount() {
        return weekDays.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            this.cardView = v;
        }
    }

}
