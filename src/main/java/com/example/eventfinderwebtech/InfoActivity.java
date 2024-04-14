package com.example.eventfinderwebtech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager viewPager;
    ImageView iv;

    int note=0;
    SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tab=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewPager);

        tab.setTabTextColors(Color.parseColor("#FFFFFFFF"),Color.parseColor("#59c639"));
        tab.setSelectedTabIndicatorColor(Color.parseColor("#59c639"));


        TabLayout.Tab SearchTab = tab.newTab();
        SearchTab.setText("DETAILS");
        SearchTab.setCustomView(R.layout.details);
        SearchTab.setIcon(R.drawable.info_icon);
        //tab.getTabAt(0).setCustomView(R.layout.details);
        tab.addTab(SearchTab);

        //Second Tab
        TabLayout.Tab FavoritesTab = tab.newTab();
        //FavoritesTab.setText("ARTISTS");
        tab.addTab(FavoritesTab);

        TabLayout.Tab VenueTab = tab.newTab();
        //VenueTab.setText("VENUE");

        tab.addTab(VenueTab );


        InfoViewPagerAdapter adapter=new InfoViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        iv=(ImageView)findViewById(R.id.gosearch);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain=new Intent(InfoActivity.this, MainActivity.class);
                startActivity(gotoMain);
                finish();
            }
        });

        String event_id_t;
        String event_id_name;
        String event_id_flag;


        String event_image_url,event_venue,event_genre,event_date,event_time;





        Bundle event_id_=getIntent().getExtras();

        event_id_t=event_id_.getString("event_id").toString();
        event_id_flag=event_id_.getString("event_flag").toString();
        event_id_name=event_id_.getString("event_name").toString();


        event_image_url=event_id_.getString("event_image_url").toString();
        event_venue=event_id_.getString("event_venue").toString();
        event_genre=event_id_.getString("event_genre").toString();
        event_date=event_id_.getString("event_date").toString();
        event_time=event_id_.getString("event_time").toString();


        String store_=event_image_url.toString() + "#" + event_id_name +"#"+event_venue+"#"+event_genre+"#"+event_date+"#"+event_time+"#"+event_id_t;



        Log.d("Details Hello",event_id_t);

        TextView t=findViewById(R.id.title_);
        t.setText(event_id_name);
        t.setSelected(true);

        ImageView iv=findViewById(R.id.heart_outline_);
        if(event_id_flag.equals("0")){
            iv.setImageResource(R.drawable.heart_outline);
        }
        else{
            iv.setImageResource(R.drawable.heart_filled);
        }

        s=getApplicationContext().getSharedPreferences("Favorites",0);

        if(event_id_flag.equals("0")){
            note=0;
        }else{
            note=1;
        }


        /*iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor= s.edit();
                if(note==0){
                    iv.setImageResource(R.drawable.heart_filled);
                    editor.putString(event_id_t.toString(),store_.toString());
                    editor.commit();
                   note=1;
                    Toast.makeText(v.getContext(),event_id_name+" added to favorites", Toast.LENGTH_SHORT).show();
                }
                else{

                    iv.setImageResource(R.drawable.heart_outline);
                    editor.remove(event_id_t.toString());
                    editor.commit();
                    Toast.makeText(v.getContext(),event_id_name+" removed from favorites", Toast.LENGTH_SHORT).show();
                    note=0;
                }
            }
        });*/











        SharedEventId s=new SharedEventId();
        s.eventid=event_id_t;

        //backendcall_details(event_id_t.toString());


        /*Bundle details=new Bundle();
        details.putString("event_id",event_id_t);

        DetailsFragment det=new DetailsFragment();
        det.setArguments(details);
        */

        ImageView fb=findViewById(R.id.fb);
        ImageView twitter=findViewById(R.id.twitter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + s.BTA.toString()));
                startActivity(browserIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=" + event_id_name.toString() + " on Ticketmaster."+ "\n" +s.BTA.toString()));
                startActivity(browserIntent);
            }
        });


    }

    public void backendcall_details(String event_id){

        String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/eventcard?data=" + "{"+ "%22eventid%22:"+"%22"+event_id +"%22}";
        RequestQueue q = Volley.newRequestQueue(InfoActivity.this);
        Log.d("URL",api_url.toString());
        JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Log.d("In Info AutoComplete",response.toString());

                        SharedEventId s=new SharedEventId();
                        s.details_=response;





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        q.add(jsonObj);


    }



}




