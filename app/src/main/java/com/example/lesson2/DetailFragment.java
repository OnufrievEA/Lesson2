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
import com.example.lesson2.weather_model.WeatherRequest;

import retrofit2.Response;


public class DetailFragment extends Fragment {

    private static final String CITY = "city";
    private static final String TEMPERATURE = "TEMPERATURE";
    private static String city;
    private static int Temperature;
    private static final String WEATHER_API_KEY = "ee84ad36add2f9b733e58aac7389c3e8";

    private String cityErrorMsg;
    private String connectionErrorMsg;

    private Button refresh;
    private ThermometerView thermometerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resView = inflater.inflate(R.layout.fragment_detail, container, false);

        WeatherSource sourceData = new WeatherSource(getResources());
        initRecyclerView(resView, sourceData.initData());

        if (savedInstanceState != null) {
            this.city = savedInstanceState.getString(CITY);
            this.Temperature = savedInstanceState.getInt(TEMPERATURE);
        }

        cityErrorMsg = getString(R.string.city_error);
        connectionErrorMsg = getString(R.string.connection_error);

        return resView;
    }

    private void init(View v) {
        TextView cityTV = v.findViewById(R.id.cityTV);
        cityTV.setText(city);
        refresh = v.findViewById(R.id.refresh);
        refresh.setOnClickListener(clickListener);
        thermometerView = v.findViewById(R.id.myTherm);
    }



    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WeatherLoader weatherLoader = new WeatherLoader();
            weatherLoader.setCityListener(new WeatherLoader.cityListener() {
                @Override
                public void negativeAction(String message) {
                    MyDialogFragment myDialogFragment = new MyDialogFragment(message);
                    if (!MyDialogFragment.isDialogShown()) {
                        myDialogFragment.show(getFragmentManager(), "myDialog");
                    }
                }

                @Override
                public  void positiveAction(Response<WeatherRequest> response) {
                    displayWeather(response);
                }
            });
            weatherLoader.getWeather(city, WEATHER_API_KEY, cityErrorMsg, connectionErrorMsg);
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
        outState.putString(CITY, city);
        outState.putInt("TEMPERATURE", Temperature);
    }

    public void setCity(String city) {
        this.city = city.trim().substring(0, 1).toUpperCase() + city.trim().substring(1).toLowerCase();
    }

    private void displayWeather(Response<WeatherRequest> response) {
        if (response.body() != null) {
            View view = getView();
            EditText temperature = view.findViewById(R.id.textTemprature);
            EditText pressure = view.findViewById(R.id.textPressure);
            EditText humidity = view.findViewById(R.id.textHumidity);
            EditText windSpeed = view.findViewById(R.id.textWindspeed);
            temperature.setText(String.valueOf(getCelsius(response.body().getMain().getTemp())));
            pressure.setText(String.format("%d", response.body().getMain().getPressure()));
            humidity.setText(String.format("%d", response.body().getMain().getHumidity()));
            windSpeed.setText(String.format("%.2f", response.body().getWind().getSpeed()));
            Temperature = getCelsius(response.body().getMain().getTemp());
            displayTemperature(response, thermometerView);
        }
    }

    private void displayTemperature(Response<WeatherRequest> response, ThermometerView thermometerView) {
        thermometerView.displayTemp(Temperature);
    }

    private int getCelsius(float kelvin) {
        return (int) (kelvin - 273.15);
    }
}
