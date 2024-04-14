package com.example.eventfinderwebtech;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class no_data extends Fragment {


    View view;


    public no_data() {
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
        View v=inflater.inflate(R.layout.fragment_no_data, container, false);

        FrameLayout eventlist_fragment=(FrameLayout)v.findViewById(R.id.eventlist_fragment);

        Toolbar toolbar=(Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventlist_fragment.removeAllViews();

                FragmentManager fm= getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();


                ft.add(R.id.search_fragment,new SearchFragment());
                ft.commit();
            }
        });

        return v;
    }
}