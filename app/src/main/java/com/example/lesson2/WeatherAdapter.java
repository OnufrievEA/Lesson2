package com.example.lesson2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2.data.Weather;
import com.example.lesson2.data.WeatherSource;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Listener listener;
    private WeatherSource dataSource;

    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public WeatherAdapter(WeatherSource dataSource) {
        this.dataSource = dataSource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView weekDay;
        private TextView date;
        private ImageView picture;
        private TextView dayTemperature;
        private TextView nightTemperature;
        private TextView description;

        public ViewHolder(CardView v) {
            super(v);
            this.cardView = v;
            this.weekDay = v.findViewById(R.id.week_day);
            this.date = v.findViewById(R.id.date);
            this.picture = v.findViewById(R.id.weather_image);
            this.dayTemperature = v.findViewById(R.id.day_temp);
            this.nightTemperature = v.findViewById(R.id.night_temp);
            this.description = v.findViewById(R.id.description);
        }

        public void setData(String weekDay, String date, int picture, String dayTemp, String nightTemp, String description) {
            getWeekDay().setText(weekDay);
            getDate().setText(date);
            getPicture().setImageResource(picture);
            getdayTemp().setText(dayTemp);
            getNightTemp().setText(nightTemp);
            getDescription().setText(description);
        }

        public TextView getWeekDay() {
            return this.weekDay;
        }

        public TextView getDate() {
            return this.date;
        }

        public ImageView getPicture() {
            return this.picture;
        }

        public TextView getdayTemp() {
            return this.dayTemperature;
        }

        public TextView getNightTemp() {
            return this.nightTemperature;
        }

        public TextView getDescription() {
            return this.description;
        }

        public void setOnClickListener(final Listener listener) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Получаем позицию адаптера
                    int adapterPosition = getAbsoluteAdapterPosition();
                    // Проверяем ее на корректность
                    if (adapterPosition == RecyclerView.NO_POSITION) return;
                    listener.onClick(adapterPosition);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_weather_broadcast, parent, false);
        ViewHolder vh = new ViewHolder(cv);
        if (listener != null) {
            vh.setOnClickListener(listener);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Weather weather = dataSource.getWeather(position);
        holder.setData(weather.getWeekDay(), weather.getDate(), weather.getImageResourceId(),
                weather.getDayTemperature(), weather.getNightTemperature(), weather.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }
}
