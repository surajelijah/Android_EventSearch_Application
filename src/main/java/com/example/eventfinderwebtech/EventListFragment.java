package com.example.eventfinderwebtech;

import static com.example.eventfinderwebtech.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class EventListFragment extends Fragment {

    /* TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */
    private String data_string;
    View view,no_view;
    Button go_tosearch;
    FrameLayout eventlist_fragment;
    ProgressBar pb;
    RecyclerView rv;
    ArrayList<RecyclerData> ar;
    FragmentManager fma;

    int is_data=0;
    public EventListFragment() {
        // Required empty public constructor
    }

    public void backend_call(String data) throws UnsupportedEncodingException {


        String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/event?data=" + data ;
        //String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/event?data={%22keyword%22:%22Los%20Angeles%20Lakers%22,%22distance%22:10,%22category%22:%22Default%22,%22checkbox%22:true,%22location%22:%2234.0030,-118.2863%22}";
        RequestQueue q = Volley.newRequestQueue(getActivity());

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
                        try {
                            Log.d("Backend Call",response.getJSONArray("events").toString());

                            ar=new ArrayList<RecyclerData>();

                            for(int i=0;i<response.getJSONArray("events").length();i++){

                                //Log.d("A",response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                               ar.add(new RecyclerData(response.getJSONArray("events").getJSONObject(i).get("event").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("venue").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("genre").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("date").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("time").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("icon").toString(),
                                       response.getJSONArray("events").getJSONObject(i).get("event_id").toString()
                                       ));

                            }
                            rv=(RecyclerView)view.findViewById(R.id.event_list);

                            if(response.getJSONArray("events").length()==0){

                                FrameLayout elayout=view.findViewById(R.id.eventlist_fragment);

                                elayout.removeAllViews();

                                FragmentManager fm= getActivity().getSupportFragmentManager();
                                FragmentTransaction ft=fm.beginTransaction();

                                //Bundle parameters=new Bundle();
                                //parameters.putString("data_string",data_);

                                no_data nd=new no_data();

                                ft.add(R.id.eventlist_fragment,nd);
                                ft.commit();
                                rv.setVisibility(View.VISIBLE);
                                return;
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        //Here write code to got the EventList Fragment

                        //Traverse the response and creating a ArrayList

                        //rv=(RecyclerView)view.findViewById(R.id.event_list);
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                        RecyclerDataAdapter ad= new RecyclerDataAdapter(getActivity(),ar);
                        rv.setAdapter(ad);

                        Log.d("No_Data", String.valueOf(ar.size()));


                        /*if(ar.size()!=0){

                            FrameLayout elayout=view.findViewById(id.eventlist_fragment);

                            elayout.removeAllViews();

                            FragmentManager fm= getActivity().getSupportFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();

                            //Bundle parameters=new Bundle();
                            //parameters.putString("data_string",data_);

                            no_data nd=new no_data();

                            ft.add(id.eventlist_fragment,nd);
                            ft.commit();

                        }*/



                        //pb.setVisibility(View.INVISIBLE);
                        rv.setVisibility(View.VISIBLE);
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
            data_string=getArguments().getString("data_string");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(layout.fragment_event_list, container, false);

        eventlist_fragment=(FrameLayout)view.findViewById(id.eventlist_fragment);


        Toolbar toolbar=(Toolbar) view.findViewById(id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventlist_fragment.removeAllViews();

                FragmentManager fm= getActivity().getSupportFragmentManager();
                fma=fm;
                FragmentTransaction ft=fm.beginTransaction();


                ft.add(R.id.search_fragment,new SearchFragment());
                ft.commit();
            }
        });
        pb=(ProgressBar) view.findViewById(id.progressBar);

        pb.setVisibility(View.VISIBLE);

        try {
            backend_call(data_string);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return view;
    }


}