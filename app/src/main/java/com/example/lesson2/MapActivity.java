package com.example.lesson2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private final int SETTING_CODE = 88;

    private GoogleMap mMap;

    private double lat;
    private double lng;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                lat = latLng.latitude;
                lng = latLng.longitude;
                Thread thread = getAddressThread(latLng);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                }

                Intent intent = new Intent(MapActivity.this, WeatherActivity.class);
                intent.putExtra(WeatherActivity.CITY, address);
                intent.putExtra(WeatherActivity.AUTOGETWEATHER, true);
                intent.putExtra(WeatherActivity.LAT, lat);
                intent.putExtra(WeatherActivity.LNG, lng);
                startActivityForResult(intent, SETTING_CODE);
            }
        });
    }

    // Получаем адрес по координатам
    private Thread getAddressThread(final LatLng location) {
        final Geocoder geocoder = new Geocoder(this);
        // Поскольку Geocoder работает по интернету, создаём отдельный поток
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                    address = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                }
            }
        });
        return thread;
    }
}