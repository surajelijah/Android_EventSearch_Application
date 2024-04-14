package com.example.eventfinderwebtech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {

    View v;

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //v=inflater.inflate(R.layout.needed, container, false);

        v=inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mp=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        SharedEventId s=new SharedEventId();
        mp.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //LatLng sydney = new LatLng(-33.852, 151.211);
                LatLng sydney = new LatLng(Double.parseDouble(s.lat),Double.parseDouble(s.lng));
                googleMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(" "));

                //googleMap.clear();
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
            }
        });

        return v;
    }
}