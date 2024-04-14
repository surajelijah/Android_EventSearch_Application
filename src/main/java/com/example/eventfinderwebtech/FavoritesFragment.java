package com.example.eventfinderwebtech;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;


public class FavoritesFragment extends Fragment {



    FrameLayout favorites_frame;
    RecyclerView rv;
    ArrayList<RecyclerData> ar;
    public FavoritesFragment() {
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
        View v=inflater.inflate(R.layout.fragment_favorites, container, false);

        favorites_frame=v.findViewById(R.id.favorites_frame);

        //TextView t= v.findViewById(R.id.favoriteslist);
        //t.setText(pref.getAll().toString());

        SharedPreferences pref=getActivity().getSharedPreferences("Favorites",0);
        pref.getAll();


        ar=new ArrayList<RecyclerData>();

        for( Map.Entry<String, ?> entry : pref.getAll().entrySet()){
            Log.d("Favorite Values",entry.getKey().toString() +": " +entry.getValue().toString());
            String[] stored_data=entry.getValue().toString().split("#");
            for(int i=0;i< stored_data.length;i++){
                Log.d("Data",stored_data[i].toString());
            }
            ar.add(new RecyclerData(stored_data[1],stored_data[2],stored_data[3],stored_data[4],stored_data[5],stored_data[0],stored_data[6]));
        }

        rv=(RecyclerView)v.findViewById(R.id.favorites_list);


        if(ar.size()==0){
            View no_view=inflater.inflate(R.layout.nodata, container, false);
            rv.setVisibility(View.GONE);
            return no_view;
        }




        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FavoritesDataAdapter ad=new FavoritesDataAdapter(getActivity(),ar);

        rv.setAdapter(ad);

        rv.setVisibility(View.VISIBLE);




        /*if(ad.data.size()==0){
            View no_view=inflater.inflate(R.layout.nodata, container, false);
            return no_view;
        }*/

        return v;
    }


}