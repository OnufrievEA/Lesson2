package com.example.lesson2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lesson2.databaseHelper.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CityFragment extends Fragment {

    private static final String CITY = "city";
    private String city;

    private Listener listener;

    private TextInputLayout cityLayout;
    private TextInputEditText cityEt;
    private MaterialButton continueBtn;
    private ListView listFavorites;

    interface Listener {
        void onContinueBtnClicked(String city);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            city = savedInstanceState.getString(CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_city, container, false);
        cityLayout = result.findViewById(R.id.cityContainer);
        cityEt = result.findViewById(R.id.cityET);
        listFavorites = result.findViewById(R.id.list_favorites);
        return result;
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null && listener != null) {
            continueBtn = view.findViewById(R.id.continueBtn);
            cityEt = view.findViewById(R.id.cityET);
            city = ((MainActivity) getActivity()).loadCurrentCity();
            cityEt.setText(city);
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateCity(cityLayout, String.valueOf(cityEt.getText()), getString(R.string.error_message))) {
                        listener.onContinueBtnClicked(String.valueOf(cityEt.getText()));
                    }
                }
            });
            setupFavoritesListView();
        }
    }

    private boolean validateCity(TextInputLayout layout, String text, String errorMessage) {
        if (text.length() == 0) {
            layout.setError(errorMessage);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        city = String.valueOf(cityEt.getText());
        outState.putString(CITY, city);
    }

    private void setupFavoritesListView() {
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(getActivity());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor favoritesCursor = db.query("CITIES",
                    new String[]{"_id", "CITY"},
                    null, null, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"CITY"},
                    new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

}
