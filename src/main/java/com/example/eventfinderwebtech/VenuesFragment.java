package com.example.eventfinderwebtech;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import org.json.JSONException;
import org.json.JSONObject;


public class VenuesFragment extends Fragment  {

    View v;
    String lat,lng;
    static JSONObject venue_details;

    int gr_t=0;
    int gr_s=0;
    int cr_t=0;
    int cr_s=0;

    int oh_t=0;
    int oh_s=0;


    public VenuesFragment() {
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
        v=inflater.inflate(R.layout.fragment_venues, container, false);

        SharedEventId s=new SharedEventId();

        JSONObject response=s.details;
        //JSONObject response=this.venue_details;
        //JSONObject response_=s.details_;
        //Log.d("In Venue for sure ",this.venue_details.toString());


        Log.d("In Venue for sure ",response.toString());


        TextView venue=v.findViewById(R.id.venue);
        venue.setSelected(true);

        TextView address=v.findViewById(R.id.address);
        address.setSelected(true);

        TextView city_state=v.findViewById(R.id.city_state);
        city_state.setSelected(true);


        TextView contactinfo=v.findViewById(R.id.contactinfo);
        contactinfo.setSelected(true);

        TextView oh=v.findViewById(R.id.oh);
        TextView gr=v.findViewById(R.id.gr);
        TextView cr=v.findViewById(R.id.cr);



        try {
            if(response.getJSONArray("venue").getJSONObject(0).has("name"))
                venue.setText(response.getJSONArray("venue").getJSONObject(0).get("name").toString());

            if(response.getJSONArray("venue").getJSONObject(0).has("address"))
                address.setText(response.getJSONArray("venue").getJSONObject(0).get("address").toString());

            if(response.getJSONArray("venue").getJSONObject(0).has("city"))
                city_state.setText(response.getJSONArray("venue").getJSONObject(0).get("city").toString());

            if(response.getJSONArray("venue").getJSONObject(0).has("phone"))
                contactinfo.setText(response.getJSONArray("venue").getJSONObject(0).get("phone").toString());

            if(response.getJSONArray("venue").getJSONObject(0).has("oh")){
                oh.setText(response.getJSONArray("venue").getJSONObject(0).get("oh").toString());

                if(response.getJSONArray("venue").getJSONObject(0).get("oh").toString().trim().length()!=0){

                    if(response.getJSONArray("venue").getJSONObject(0).get("oh").toString().trim().length()<32) {

                        oh.setText(response.getJSONArray("venue").getJSONObject(0).get("oh").toString());
                        oh_s=0;
                    }
                    else{
                        oh.setText(response.getJSONArray("venue").getJSONObject(0).get("oh").toString().substring(0,33).toString() +"...");
                        oh_s=1;
                        oh_t=0;
                    }

                }




            }
            oh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(oh_s!=0 && response.getJSONArray("venue").getJSONObject(0).get("oh").toString().trim().length()!=0){
                            if(oh_t==1){

                                try {
                                    oh.setText(response.getJSONArray("venue").getJSONObject(0).get("oh").toString().substring(0,33).toString() +"...");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                oh_t=0;
                            }
                            else{
                                try {
                                    oh.setText(response.getJSONArray("venue").getJSONObject(0).get("oh").toString());
                                    oh_t=1;
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });





            if(response.getJSONArray("venue").getJSONObject(0).has("gr")){
                gr.setText(response.getJSONArray("venue").getJSONObject(0).get("gr").toString());

                if(response.getJSONArray("venue").getJSONObject(0).get("gr").toString().trim().length()!=0){

                    if(response.getJSONArray("venue").getJSONObject(0).get("gr").toString().trim().length()<32) {

                        gr.setText(response.getJSONArray("venue").getJSONObject(0).get("gr").toString());
                        gr_s=0;
                    }
                    else{
                        gr.setText(response.getJSONArray("venue").getJSONObject(0).get("gr").toString().substring(0,33).toString() +"...");
                        gr_s=1;
                        gr_t=0;
                    }

                }

            }

            gr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(gr_s!=0 && response.getJSONArray("venue").getJSONObject(0).get("gr").toString().trim().length()!=0){
                            if(gr_t==1){

                                try {
                                    gr.setText(response.getJSONArray("venue").getJSONObject(0).get("gr").toString().substring(0,33).toString() +"...");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                gr_t=0;
                            }
                            else{
                                try {
                                    gr.setText(response.getJSONArray("venue").getJSONObject(0).get("gr").toString());
                                    gr_t=1;
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            if(response.getJSONArray("venue").getJSONObject(0).has("cr")){
                cr.setText(response.getJSONArray("venue").getJSONObject(0).get("cr").toString());

                if(response.getJSONArray("venue").getJSONObject(0).get("cr").toString().trim().length()!=0){

                    if(response.getJSONArray("venue").getJSONObject(0).get("cr").toString().trim().length()<32) {

                        cr.setText(response.getJSONArray("venue").getJSONObject(0).get("cr").toString());
                        cr_s=0;
                    }
                    else{
                        cr.setText(response.getJSONArray("venue").getJSONObject(0).get("cr").toString().substring(0,33).toString() +"...");
                        cr_s=1;
                        cr_t=0;
                    }

                }


            }

            cr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(cr_s!=0 && response.getJSONArray("venue").getJSONObject(0).get("cr").toString().trim().length()!=0){
                            if(cr_t==1){

                                try {
                                    cr.setText(response.getJSONArray("venue").getJSONObject(0).get("cr").toString().substring(0,33).toString() +"...");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                cr_t=0;
                            }
                            else{
                                try {
                                    cr.setText(response.getJSONArray("venue").getJSONObject(0).get("cr").toString());
                                    cr_t=1;
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });










            lat=response.getJSONArray("venue").getJSONObject(0).get("latitude").toString();
            lng=response.getJSONArray("venue").getJSONObject(0).get("longitude").toString();

            s.lat=lat;
            s.lng=lng;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }



        Fragment fm=new MapFragment();
        if(fm!=null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.actualmap, fm).commit();
        }
        return v;
    }




}

