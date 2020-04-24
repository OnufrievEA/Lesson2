package com.example.lesson2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private static final String CITY = "city";
    private String city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            this.city = savedInstanceState.getString(CITY);
        }
        return inflater.inflate(R.layout.fragment_detail, container, false);
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
