package com.example.eventfinderwebtech;

import androidx.annotation.NonNull;

public class RecyclerData {

    String event_name;
    String event_venue;
    String event_genre;
    String date;
    String time;
    String image_url;
    String event_id;

    public RecyclerData(String event_name,
    String event_venue,
    String event_genre,
    String date,
    String time,
    String image_url,String event_id){

        this.event_name=event_name;
        this.event_venue=event_venue;
        this.event_genre=event_genre;
        this.date=date;
        this.time=time;
        this.image_url=image_url;
        this.event_id=event_id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.image_url+"#"+this.event_name+"#"+this.event_venue+"#"+this.event_genre+"#"+this.date+"#"+this.time+"#"+this.event_id;
    }
}
