package com.example.lesson2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2.data.WeatherSource;

public class DetailFragment extends Fragment {


    private static final String CITY = "city";
    private static String city;
    private static final String WEATHER_API_KEY = "ee84ad36add2f9b733e58aac7389c3e8";

    //    private EditText temperature;
//    private EditText pressure;
//    private EditText humidity;
//    private EditText windSpeed;
    private Button refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resView = inflater.inflate(R.layout.fragment_detail, container, false);

        WeatherSource sourceData = new WeatherSource(getResources());
        initRecyclerView(resView, sourceData.initData());

        if (savedInstanceState != null) {
            this.city = savedInstanceState.getString(CITY);
        }

        return resView;
    }

    private void init(View v) {
        TextView cityTV = v.findViewById(R.id.cityTV);
        cityTV.setText(city);
//        temperature = v.findViewById(R.id.textTemprature);
//        pressure = v.findViewById(R.id.textPressure);
//        humidity = v.findViewById(R.id.textHumidity);
//        windSpeed = v.findViewById(R.id.textWindspeed);
        refresh = v.findViewById(R.id.refresh);
        refresh.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String WEATHER_URL = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,RU&appid=%s", city, WEATHER_API_KEY);
                WeatherBroadcaster weatherBroadcaster = new WeatherBroadcaster();
                weatherBroadcaster.broadcastWeather(WEATHER_URL, getView());
            } catch (Exception e) {
                getActivity().finish();
            }

        }
    };


    private void initRecyclerView(View resView, WeatherSource sourceData) {
        RecyclerView weatherRecycler = resView.findViewById(R.id.weather_recycler);

        weatherRecycler.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        weatherRecycler.setLayoutManager(layoutManager);

        WeatherAdapter adapter = new WeatherAdapter(sourceData);
        weatherRecycler.setAdapter(adapter);
        adapter.setListener(new WeatherAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            }
        });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        weatherRecycler.addItemDecoration(itemDecoration);

    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            init(view);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("city", city);
    }

    public void setCity(String city) {
        this.city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
    }


}
