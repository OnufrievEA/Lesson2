package com.example.lesson2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson2.data.Weather;

public class DetailFragment extends Fragment {

    private static final String CITY = "city";
    private String city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resView = inflater.inflate(R.layout.fragment_detail, container, false);
        initRecyclerView(resView);
        if (savedInstanceState != null) {
            this.city = savedInstanceState.getString(CITY);
        }
        return resView;
    }

    private void initRecyclerView(View resView) {
        RecyclerView weatherRecycler = resView.findViewById(R.id.weather_recycler);

        String[] weekDays = new String[Weather.weatherArray.length];
        String[] dates = new String[weekDays.length];
        int[] imageResourceIds = new int[weekDays.length];
        int[] dayTemperatures = new int[weekDays.length];
        int[] nightTemperatures = new int[weekDays.length];
        String[] descriptions = new String[weekDays.length];

        for (int i = 0; i < weekDays.length; i++) {
            weekDays[i] = Weather.weatherArray[i].getWeekDay();
            dates[i] = Weather.weatherArray[i].getDate();
            imageResourceIds[i] = Weather.weatherArray[i].getImageResourceId();
            dayTemperatures[i] = Weather.weatherArray[i].getDayTemperature();
            nightTemperatures[i] = Weather.weatherArray[i].getNightTemperature();
            descriptions[i] = Weather.weatherArray[i].getDescription();
        }

        WeatherAdapter adapter = new WeatherAdapter(weekDays, dates, imageResourceIds, dayTemperatures, nightTemperatures, descriptions);
        adapter.setListener(new WeatherAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        weatherRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        weatherRecycler.setLayoutManager(layoutManager);
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView cityTV = (TextView) view.findViewById(R.id.cityTV);
            cityTV.setText(city);
            Button searchBtn = (Button) view.findViewById(R.id.searchBtn);
            searchBtn.setText("Search " + city);
            ImageButton settingsBtn = (ImageButton) view.findViewById(R.id.settingsBtn);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("city", city);
    }
}
