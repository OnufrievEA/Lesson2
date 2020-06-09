package com.example.lesson2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2.data.WeatherSource;
import com.example.lesson2.databaseHelper.DatabaseHelper;
import com.example.lesson2.weather_model.WeatherRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class DetailFragment extends Fragment {

    private static final String CITY = "city";
    private static final String TEMPERATURE = "TEMPERATURE";
    private static final String AUTOGETWEATHER = "AUTOGETWEATHER";
    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private String city;
    private double lat;
    private double lng;
    private static int Temperature;
    private static final String WEATHER_API_KEY = "ee84ad36add2f9b733e58aac7389c3e8";

    private Button refresh;
    private CheckBox favorite;
    private boolean isFavorite;
    private ThermometerView thermometerView;
    private boolean autoGetWeather = false;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init(getView());

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(getActivity());
        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("CITIES",
                    new String[]{"CITY"},
                    null, null, null, null, null);
            List<String> favoriteCities = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                favoriteCities.add(cursor.getString(0));
            }
            if (favoriteCities.contains(city)) {
                isFavorite = true;
            }
            favorite.setChecked(isFavorite);
        } catch (SQLException e) {
            Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        if (autoGetWeather) {
            refresh.callOnClick();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (savedInstanceState != null) {
            this.city = savedInstanceState.getString(CITY);
            this.Temperature = savedInstanceState.getInt(TEMPERATURE);
            this.autoGetWeather = savedInstanceState.getBoolean(AUTOGETWEATHER);
            this.lat = savedInstanceState.getFloat(LAT);
            this.lng = savedInstanceState.getFloat(LNG);
        }

        WeatherSource sourceData = new WeatherSource(getResources());
        initRecyclerView(resView, sourceData.initData());

        return resView;
    }

    private void init(View v) {
        TextView cityTV = v.findViewById(R.id.cityTV);
        cityTV.setText(city);
        refresh = v.findViewById(R.id.refresh);
        refresh.setOnClickListener(clickListener);
        thermometerView = v.findViewById(R.id.myTherm);
        favorite = v.findViewById(R.id.favorite);
        favorite.setOnClickListener(cbClickListener);
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
                public void positiveAction(Response<WeatherRequest> response) {
                    displayWeather(response);
                }
            });
            if (autoGetWeather) {
                weatherLoader.getWeather(lat, lng, WEATHER_API_KEY, getString(R.string.city_error), getString(R.string.connection_error));
            } else {
                weatherLoader.getWeather(city, WEATHER_API_KEY, getString(R.string.city_error), getString(R.string.connection_error));
            }
        }
    };

    View.OnClickListener cbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isFavorite) {
                ContentValues favoriteCity = new ContentValues();
                favoriteCity.put("CITY", city);

                SQLiteOpenHelper databaseHelper = new DatabaseHelper(getActivity());
                try {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    db.insert("CITIES",
                            null,
                            favoriteCity);
                } catch (SQLException e) {
                    Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT).show();
                }
            } else {
                SQLiteOpenHelper databaseHelper = new DatabaseHelper(getActivity());
                try {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    db.delete("CITIES",
                            "CITY = ?",
                            new String[]{city});
                } catch (SQLException e) {
                    Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT).show();
                }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY, city);
        outState.putInt(TEMPERATURE, Temperature);
        outState.putBoolean(AUTOGETWEATHER, autoGetWeather);
        outState.putDouble(LAT, lat);
        outState.putDouble(LNG, lng);
    }

    public void setCity(String city) {
        this.city = city;
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

    public void setWeatherFlag(boolean b) {
        autoGetWeather = b;
    }

    public void setCoords(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
