package com.example.eventfinderwebtech;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    String event_id;
    View v;
    public DetailsFragment() {
        // Required empty public constructor
    }

    public void backendcall_details(){

        String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/eventcard?data=" + "{"+ "%22eventid%22:"+"%22"+event_id +"%22}";

        //String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/event?data={%22keyword%22:%22Los%20Angeles%20Lakers%22,%22distance%22:10,%22category%22:%22Default%22,%22checkbox%22:true,%22location%22:%2234.0030,-118.2863%22}";
        RequestQueue q = Volley.newRequestQueue(getActivity());
        Log.d("URL",api_url.toString());



        JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        //rv.setVisibility(View.INVISIBLE);
                        /*try {
                            //Log.d("AutoComplete",response.getJSONObject("_embedded").getJSONArray("venues").toString());
                            auto_complete_list.clear();
                            for(int i=0;i<response.getJSONObject("_embedded").getJSONArray("venues").length();i++){

                                //Log.d("A",response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                                auto_complete_list.add(response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }*/
                        Log.d("In Details AutoComplete",response.toString());

                        TextView at=v.findViewById(R.id.artists_teams);
                        at.setSelected(true);

                        TextView venue_name=v.findViewById(R.id.venue_name);
                        venue_name.setSelected(true);

                        TextView date=v.findViewById(R.id.date);
                        date.setSelected(true);

                        TextView time=v.findViewById(R.id.time);
                        time.setSelected(true);

                        TextView genres=v.findViewById(R.id.genres);
                        genres.setSelected(true);

                        TextView pricerange=v.findViewById(R.id.pricerange);
                        pricerange.setSelected(true);

                        TextView ts=v.findViewById(R.id.ts);
                        ts.setSelected(true);

                        TextView bt=v.findViewById(R.id.bt);
                        bt.setSelected(true);



                        ImageView iv=v.findViewById(R.id.venue_image);




                        try {
                            at.setText(response.getJSONArray("events").getJSONObject(0).get("name").toString());


                            if(response.getJSONArray("events").getJSONObject(0).has("venue"))
                                venue_name.setText(response.getJSONArray("events").getJSONObject(0).get("venue").toString());

                            if(response.getJSONArray("events").getJSONObject(0).has("data")) {
                                date.setText(response.getJSONArray("events").getJSONObject(0).get("data").toString());

                                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    if(response.getJSONArray("events").getJSONObject(0).get("data").toString().trim().length()!=0) {
                                        Date date_ = sd.parse(response.getJSONArray("events").getJSONObject(0).get("data").toString());
                                        sd = new SimpleDateFormat("MMMM dd,yyyy");
                                        date.setText(sd.format(date_).toString());
                                        date.setSelected(true);
                                    }
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                            if(response.getJSONArray("events").getJSONObject(0).has("time")) {
                                time.setText(response.getJSONArray("events").getJSONObject(0).get("time").toString());


                                SimpleDateFormat td = new SimpleDateFormat("H:mm:ss");
                                try {
                                    if(response.getJSONArray("events").getJSONObject(0).get("time").toString().trim().length()!=0){
                                        Date time_ = td.parse(response.getJSONArray("events").getJSONObject(0).get("time").toString());
                                        td=new SimpleDateFormat("K:mm:ss aa");
                                        time.setText(td.format(time_));
                                        time.setSelected(true);}
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }






                            }
                            if(response.getJSONArray("events").getJSONObject(0).has("genres"))
                                genres.setText(response.getJSONArray("events").getJSONObject(0).get("genres").toString());

                            if(response.getJSONArray("events").getJSONObject(0).has("PR"))
                                pricerange.setText(response.getJSONArray("events").getJSONObject(0).get("PR").toString());

                            if(response.getJSONArray("events").getJSONObject(0).has("TS")) {

                                ts.setText(response.getJSONArray("events").getJSONObject(0).get("TS").toString());

                                if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().trim().length()!=0){

                                    if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().equals("onsale"))
                                        ts.setBackgroundColor(Color.parseColor("#A4C639"));

                                    if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().equals("offsale"))
                                        ts.setBackgroundColor(Color.parseColor("#E32636"));
                                    if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().equals("canceled"))
                                        ts.setBackgroundColor(Color.parseColor("#848482\t"));
                                    if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().equals("postponed"))
                                        ts.setBackgroundColor(Color.parseColor("#FFBF00"));
                                    if(response.getJSONArray("events").getJSONObject(0).get("TS").toString().equals("rescheduled"))
                                        ts.setBackgroundColor(Color.parseColor("#FFBF00"));

                                }

                            }
                            if(response.getJSONArray("events").getJSONObject(0).has("BTA")) {
                                bt.setText(response.getJSONArray("events").getJSONObject(0).get("BTA").toString());

                                bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bt.getText().toString()));
                                        startActivity(browserIntent);
                                    }
                                });

                                SharedEventId s=new SharedEventId();
                                s.BTA=bt.getText().toString();
                                s.details=response;





                            }
                            if(response.getJSONArray("events").getJSONObject(0).has("SM")){

                                Picasso.with(getActivity())
                                        .load(response.getJSONArray("events").getJSONObject(0).get("SM").toString())
                                        .into(iv);
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            event_id=getArguments().getString("event_id").toString();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_details, container, false);

        SharedEventId s=new SharedEventId();


        //TextView t=v.findViewById(R.id.event_name_);
        //t.setText(s.eventid.toString());
        event_id=s.eventid.toString();

        backendcall_details();



        return v;
    }
}