package com.example.lesson2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CityFragment extends Fragment {

    private static final String CITY = "city";
    private String city = "";
    private EditText cityEt;
    private Button continueBtn;
    private Listener listener;

    interface Listener {
        void onContinueBtnClicked(String city);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            city = savedInstanceState.getString(CITY);
        }
        return inflater.inflate(R.layout.fragment_city, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null && listener != null) {
            continueBtn = view.findViewById(R.id.continueBtn);
            cityEt = view.findViewById(R.id.cityET);
            cityEt.setText(city);
            city = String.valueOf(cityEt.getText());
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContinueBtnClicked(String.valueOf(cityEt.getText()));
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        city = String.valueOf(cityEt.getText());
        outState.putString(CITY, city);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            city = savedInstanceState.getString(CITY);
        }
    }
}
