package com.example.eventfinderwebtech;

public class ArtistData {

    String artist_name;
    String people;
    String spotify;
    String popularity;

    String artist_image,album1,album2,album3;


    public ArtistData(String artist_name,
    String people,
    String spotify,
    String popularity,String album1,String album2, String album3,String artist_image){
        this.artist_name=artist_name;
        this.people=people;
        this.spotify=spotify;
        this.popularity=popularity;
        this.album1=album1;
        this.album2=album2;
        this.album3=album3;
        this.artist_image=artist_image;

    }
    public ArtistData(){

        this.artist_image=" ";
        this.album1=" ";
        this.album2= " ";
        this.album3=" ";
        this.spotify=" ";
        this.artist_name=" ";
        this.popularity= " ";
        this.people=" ";
    }
    public void set_artist(String name){
        this.artist_name=name;
    }
    public void set_people(String name){
        this.people=name;
    }
    public void set_spotify(String name){
        this.spotify=name;
    }
    public void set_popularity(String name){
        this.popularity=name;
    }
    public void set_album1(String name){
        this.album1=name;
    }
    public void set_album2(String name){
        this.album2=name;
    }
    public void set_album3(String name){
        this.album3=name;
    }
    public void set_artistimage(String name){
        this.artist_image=name;
    }
}
