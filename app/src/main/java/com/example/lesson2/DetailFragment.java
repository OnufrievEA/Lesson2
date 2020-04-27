package com.example.lesson2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.lesson2.data.WeatherSource;

public class DetailFragment extends Fragment {

    private static final String CITY = "city";
    private String city;

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

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),  LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        weatherRecycler.addItemDecoration(itemDecoration);

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

    public void setCity(String city) {
        this.city = city;
    }
}
