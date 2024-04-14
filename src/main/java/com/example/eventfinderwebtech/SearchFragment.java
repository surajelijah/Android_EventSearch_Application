package com.example.eventfinderwebtech;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String[] categories={"All","Music","Sports","Arts & Theatre","Film","Miscellaneous"};







    EditText keyword_,distance_,location_;
    Boolean auto_detection=false;
    String keyword,distance,category_selected,location,lat_long;
    String auto_keyword;
    Spinner categories_spinner;
    View view;
    FrameLayout search_frame;
    Switch auto_detect;

    Button search,clear;

    AutoCompleteTextView auto_complete;
    Handler handler;

    AutoCompleteTextView auto_c;
    ArrayList<String> auto_complete_list=new ArrayList<>();
    ArrayAdapter<String> auto_complete_adapter;


    public void api_call_autocomplete(String keyword){

    String ticket_API_key="J3XzwuSYkREWmocLsLrgmFLRAAX7UDpt";
    String api_url = "https://app.ticketmaster.com/discovery/v2/suggest?apikey="+ticket_API_key+"&keyword="+keyword;
    RequestQueue q = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


                        //auto_complete_list.clear();


                        /*try {
                            if(response.getJSONObject("_embedded").length()!=0){
                                if(response.getJSONObject("_embedded").getJSONArray("venues").length()!=0){
                                    for (JSONObject names:
                                         response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject()) {
                                        Log.d("Hello",(String)names.get("name"));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }*/


                        try {
                            //Log.d("AutoComplete",response.getJSONObject("_embedded").getJSONArray("venues").toString());
                            auto_complete_list.clear();
                            for(int i=0;i<response.getJSONObject("_embedded").getJSONArray("venues").length();i++){

                                //Log.d("A",response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                                auto_complete_list.add(response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                            }
                            
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "AutoCompleteAPI did not happen", Toast.LENGTH_LONG).show();
                    }
                }
        );

        q.add(jsonObj);
    }


    public void getcurrentLocation_call(){
        RequestQueue q = Volley.newRequestQueue(getActivity());

        String ipinfo_key="ee8392e5a888a4";
        String ipinfo_url="https://ipinfo.io/?token=" + ipinfo_key;

        JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, ipinfo_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String latlong_ = response.get("loc").toString();
                            Log.d("msg",latlong_);
                            lat_long=latlong_;
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "AutoDetectAPI did not happen", Toast.LENGTH_LONG).show();
                    }
                }
        );

        q.add(jsonObj);
    }



    public void search_() throws UnsupportedEncodingException {



        auto_keyword=auto_c.getText().toString();
        Log.d("Auto Complete Selection",auto_keyword.toString());


        keyword=auto_keyword.toString();
        //keyword=keyword_.getText().toString();
        distance=distance_.getText().toString();
        if(keyword.length()==0){
            Toast.makeText(getActivity(),"Please fill Keyword field",Toast.LENGTH_LONG).show();
            return;
        } else{
            //keyword=keyword_.getText().toString();
            keyword=auto_keyword.toString();
        }

        if(distance.length()==0){
            distance="10";
        }else{
            distance=distance_.getText().toString();
        }



        if(auto_detection){
            location="";
        }
        else{
            location=location_.getText().toString();
            if(location.length()==0){
                Toast.makeText(getActivity(),"Please fill Location field",Toast.LENGTH_LONG).show();
                return;
            }
        }

        //Toast.makeText(getActivity(),keyword+" "+distance+" "+category_selected+" "+auto_detection+" "+location,Toast.LENGTH_LONG).show();

        Map<String, String> data = new HashMap<String, String>();
        String data_="";
        if(auto_detection) {


            data.put("%22keyword%22", "%22"+keyword.toString().replace(" ","%20")+"%22");
            data.put("%22location%22", "%22"+lat_long.toString()+"%22");
            data.put("%22distance%22", "%22"+distance.toString()+"%22");
            data.put("%22category%22", "%22"+category_selected.toString().replace(" ","%20")+"%22");
            data.put("%22checkbox%22", "%22"+auto_detection.toString()+"%22");

            int count=0;
            int length=data.size();
            for(Map.Entry<String,String> elements:data.entrySet()){
                if(count==length-1)
                    data_= data_ + elements.getKey().toString()+":"+ elements.getValue().toString();
                else
                    data_= data_ + elements.getKey().toString()+":"+ elements.getValue().toString()+",";
                count++;
            }
            data_="{"+data_+"}";
        }
        else{

            data.put("%22keyword%22", "%22"+keyword.toString().replace(" ","%20")+"%22");
            data.put("%22location%22", "%22"+location.toString().replace(" ","%20")+"%22");
            data.put("%22distance%22", "%22"+distance.toString()+"%22");
            data.put("%22category%22", "%22"+category_selected.toString().replace(" ","%20")+"%22");
            data.put("%22checkbox%22", "%22"+auto_detection.toString()+"%22");

            int count=0;
            int length=data.size();
            for(Map.Entry<String,String> elements:data.entrySet()){
                if(count==length-1)
                    data_= data_ + elements.getKey().toString()+":"+ elements.getValue().toString();
                else
                    data_= data_ + elements.getKey().toString()+":"+ elements.getValue().toString()+",";
                count++;
            }
            data_="{"+data_+"}";
        }

        search_frame.removeAllViews();

        FragmentManager fm= getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        Bundle parameters=new Bundle();
        parameters.putString("data_string",data_);

        EventListFragment el=new EventListFragment();
        el.setArguments(parameters);
        ft.add(R.id.search_fragment,el);
        ft.commit();




        //backend_call(data_.toString());

    }

    public SearchFragment() {
        // Required empty public constructor
    }

    public void get_auto_complete(String keyword,AutoCompleteTextView s){

        String ticket_API_key="J3XzwuSYkREWmocLsLrgmFLRAAX7UDpt";
        String api_url = "https://app.ticketmaster.com/discovery/v2/suggest?apikey="+ticket_API_key+"&keyword="+keyword;
        RequestQueue q = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObj=new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.d("Inside AutoComplete",response.toString());
                        try {
                            //Log.d("AutoComplete",response.getJSONObject("_embedded").getJSONArray("venues").toString());
                            auto_complete_list.clear();
                            for(int i=0;i<response.getJSONObject("_embedded").getJSONArray("attractions").length();i++){

                                //Log.d("A",response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(i).get("name").toString());

                                auto_complete_list.add(response.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(i).get("name").toString());

                            }

                            //Log.d("ArrayList Filled",auto_complete_list.toString());

                            auto_complete_adapter=new ArrayAdapter<String>(getActivity(),R.layout.autocomplete,auto_complete_list);

                            s.setAdapter(auto_complete_adapter);
                            s.setThreshold(1);
                            s.showDropDown();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "AutoCompleteAPI did not happen", Toast.LENGTH_LONG).show();
                    }
                }
        );

        q.add(jsonObj);



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_search, container, false);
        search_frame=(FrameLayout)view.findViewById(R.id.search_fragment);
        //Categories
        categories_spinner=(Spinner)view.findViewById(R.id.categories);
        //ArrayAdapter<String> categories_adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,categories);
        //categories_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> categories_adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinnercategories,categories);
        categories_adapter.setDropDownViewResource(R.layout.spinnercategories);

        categories_spinner.setAdapter(categories_adapter);
        categories_spinner.setOnItemSelectedListener(this);
        categories_spinner.setSelection(0);


        //AutoDetect

        auto_detect=(Switch) view.findViewById(R.id.auto_detection);
        auto_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auto_detect.isChecked()){
                    //Disable the location EditText to take input
                    //Write the Code for getting the coordinates
                    auto_detection=true;
                    //location_.setEnabled(false);

                    location_.setVisibility(View.INVISIBLE);

                    Toast.makeText(getActivity(),"Auto Detect" ,Toast.LENGTH_SHORT).show();
                    getcurrentLocation_call();
                }
                else{
                    //Write the code for fetching the Coordinates from the API
                    auto_detection=false;
                    location_.setEnabled(true);
                    location_.setVisibility(View.VISIBLE);
                    //Toast.makeText(getActivity(),"No Auto Detection Needed",Toast.LENGTH_SHORT).show();
                }
            }
        });



        /*Write the code to fill the autocomplete List
        auto_complete=(AutoCompleteTextView)view.findViewById(R.id.search_keyword);
        //ArrayAdapter<String> autocomplete_adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,auto_complete_list);


        auto_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), auto_complete_list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        auto_complete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeMessages(100);
                handler.sendEmptyMessageDelayed(100,300);
                auto_complete.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler= new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                if(msg.what== 100){
                    if(!TextUtils.isEmpty(auto_complete.getText())){
                        api_call_autocomplete(auto_complete.getText().toString());
                    }
                }
                return false;
            }
        });


        ArrayAdapter<String> autocomplete_adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,auto_complete_list);
        autocomplete_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auto_complete.setAdapter(autocomplete_adapter);
        auto_complete.setThreshold(1);
        */



        auto_c=(AutoCompleteTextView)view.findViewById(R.id.auto_complete);

        auto_complete_adapter=new ArrayAdapter<String>(getActivity(),R.layout.autocomplete,auto_complete_list);



       /* auto_c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                auto_c.dismissDropDown();
            }
        });*/
        auto_c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()!=0){
                    get_auto_complete(s.toString(),auto_c);
                auto_complete_adapter.notifyDataSetChanged();}
            }

            @Override
            public void afterTextChanged(Editable s) {
                //make api call

                //Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }
        });


























        //keyword_=(EditText)view.findViewById(R.id.keyword);
        distance_=(EditText)view.findViewById(R.id.distance);
        location_=(EditText)view.findViewById(R.id.location);



        search=(Button)view.findViewById(R.id.search);
        clear=(Button)view.findViewById(R.id.clear);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the Function to use the data and make HTTP call

                try {
                    search_();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to clear and put the texts

                //keyword_.setText("");
                auto_c.setText("");
                distance_.setText("");
                distance_.setHint("10");

                categories_spinner.setSelection(0);
                auto_detect.setChecked(false);

                location_.setText("");
                location_.setVisibility(View.VISIBLE);

            }
        });


        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //set the selected category variable here
            category_selected=categories[position];

            if(category_selected=="All")
                category_selected="Default";
            //Toast.makeText(getActivity(),categories[position] ,Toast.LENGTH_SHORT).show();

            SharedEventId s= new SharedEventId();
            s.genre=category_selected.toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}