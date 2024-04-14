package com.example.eventfinderwebtech;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;


public class InfoFragment extends Fragment {

    TabLayout tab;
    ViewPager viewPager;
    ImageView iv;

    public InfoFragment() {
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
        View view=inflater.inflate(R.layout.fragment_info, container, false);

        tab=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.viewPager);

        TabLayout.Tab SearchTab = tab.newTab();
        SearchTab.setText("DETAILS");
        tab.addTab(SearchTab);

        //Second Tab
        TabLayout.Tab FavoritesTab = tab.newTab();
        FavoritesTab.setText("ARTISTS");
        tab.addTab(FavoritesTab);

        TabLayout.Tab VenueTab = tab.newTab();
        VenueTab .setText("VENUE");
        tab.addTab(VenueTab );

        InfoViewPagerAdapter adapter=new InfoViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        iv=(ImageView)view.findViewById(R.id.gosearch);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent gotoMain=new Intent(InfoActivity.this, MainActivity.class);
                //startActivity(gotoMain);
                //finish();
            }
        });


        return view;
    }
}