package com.example.eventfinderwebtech;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fm_;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {

        super(fm);
        fm_=fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return new SearchFragment();
        else if(position==1){
            return new FavoritesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
            return "SEARCH";
        if(position==1)
            return "FAVORITES";

        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
