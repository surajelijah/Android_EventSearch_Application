package com.example.eventfinderwebtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class EventFinderSplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_finder_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent Code
                Intent gotoMain=new Intent(EventFinderSplashScreen.this, MainActivity.class);
                startActivity(gotoMain);
                finish();
            }
        },2000);
    }
}