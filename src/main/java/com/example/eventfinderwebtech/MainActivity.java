package com.example.eventfinderwebtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewPager);

        tab.setTabTextColors(Color.parseColor("#FFFFFFFF"),Color.parseColor("#59c639"));
        tab.setSelectedTabIndicatorColor(Color.parseColor("#59c639"));
        //First Tab
        TabLayout.Tab SearchTab = tab.newTab();
        SearchTab.setText("SEARCH");
        tab.addTab(SearchTab);

        //Second Tab
        TabLayout.Tab FavoritesTab = tab.newTab();
        FavoritesTab.setText("FAVORITES");
        tab.addTab(FavoritesTab);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*TextView t=findViewById(R.id.title);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain=new Intent(MainActivity.this, InfoActivity.class);
                gotoMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //finish();
                startActivity(gotoMain);
            }
        });*/

    }

}