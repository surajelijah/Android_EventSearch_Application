package com.example.eventfinderwebtech;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class InfoViewPagerAdapter extends FragmentPagerAdapter {
    public InfoViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new DetailsFragment();
        else if(position==1){
            return new ArtistsFragment();
        }
        else if(position==2){
            return new VenuesFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
            return "DETAILS";
        if(position==1)
            return "ARTISTS";
        if(position==2)
            return "VENUE";

        return null;
    }

}
