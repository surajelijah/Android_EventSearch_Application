package com.example.eventfinderwebtech;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ArtistsFragment extends Fragment {

    View v;
    RecyclerView rv;
    SharedEventId s_;

    static JSONObject artists_data;
    public ArtistsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_artists, container, false);

        //View noview =inflater.inflate(R.layout.noartists, container, false);
        RelativeLayout el=(RelativeLayout)v.findViewById(R.id.artist_layout);


        SharedEventId s=new SharedEventId();

        Log.d("In Artists",s.genre);
        //Log.d("In Info to Artists",this.artists_data.toString());



        if(s.genre.equals("Music")){

            Log.d("In Artists","Entered this space where call is made");
            String api_url = "https://hw8-mse4876294242-571.wl.r.appspot.com/eventcard?data=" + "{"+ "%22eventid%22:"+"%22"+s.eventid.toString() +"%22}";
            RequestQueue q = Volley.newRequestQueue(getActivity());
            Log.d("URL",api_url.toString());
            JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, api_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {



                            Log.d("In Info AutoComplete",response.toString());
                            ArrayList<ArtistData> ar=new ArrayList<ArtistData>();
                            SharedEventId s=new SharedEventId();
                            s.details_=response;


                            try {
                                Log.d("In artist to data",String.valueOf(response.getJSONArray("music").length()));
                                //Log.d("In artist data",response.getJSONArray("venue").getJSONObject(0).get("name").toString());
                                //Log.d("In artist data",response.getJSONArray("venue").getJSONObject(1).get("name").toString());
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            try{
                                int count = 0;
                                for (int i = 0; i < response.getJSONArray("music").length(); i++) {

                                    ArtistData artist=new ArtistData();

                                    if(response.getJSONArray("music").getJSONObject(i).has("name")){
                                        artist.set_artist(response.getJSONArray("music").getJSONObject(i).get("name").toString());
                                    }
                                    else{
                                        artist.set_artist(" ");
                                    }
                                    Log.d("Counter",String.valueOf(count));
                                    if(response.getJSONArray("music").getJSONObject(i).has("popularity")){
                                        artist.set_popularity(String.valueOf(response.getJSONArray("music").getJSONObject(i).get("popularity")));
                                    }
                                    else{
                                        artist.set_popularity(" ");
                                    }
                                    if(response.getJSONArray("music").getJSONObject(i).has("followers")){
                                        artist.set_people(String.valueOf(response.getJSONArray("music").getJSONObject(i).get("followers")));
                                    }
                                    else{
                                        artist.set_people(" ");
                                    }
                                    if(response.getJSONArray("music").getJSONObject(i).has("spotify")){
                                        artist.set_spotify(response.getJSONArray("music").getJSONObject(i).get("spotify").toString());
                                    }
                                    else{
                                        artist.set_spotify(" ");
                                    }
                                    if(response.getJSONArray("music").getJSONObject(i).has("images")){


                                        if(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").length()==1){
                                            artist.set_album1(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(0).toString());
                                            artist.set_artistimage(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(0).toString());
                                        }

                                        if(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").length()==2){
                                            artist.set_album1(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(0).toString());
                                            artist.set_album2(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(1).toString());
                                        }

                                        if(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").length()==3){
                                            artist.set_album1(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(0).toString());
                                            artist.set_album2(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(1).toString());
                                            artist.set_album3(response.getJSONArray("music").getJSONObject(i).getJSONArray("images").get(2).toString());
                                        }

                                    }

                                    ar.add(artist);
                                    count++;
                                    if (count == 40)
                                        break;
                                }


                                }catch (JSONException e) {
                                throw new RuntimeException(e);
                            }




                            //ar.add(new ArtistData("Elijah","100","Spotify","100"," "," "," "," "));
                            //ar.add(new ArtistData("Elijah","100","Spotify","100"," "," "," "," "));
                            //ar.add(new ArtistData("Elijah","100","Spotify","100"," "," "," "," "));

                            rv=(RecyclerView)v.findViewById(R.id.artists_list);
                            rv.setVisibility(View.VISIBLE);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            ArtistsDataAdapter ad=new ArtistsDataAdapter(getActivity(),ar);

                            rv.setAdapter(ad);







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
        else {

            el.setVisibility(View.VISIBLE);
            return v;

        }




        return v;
    }
}