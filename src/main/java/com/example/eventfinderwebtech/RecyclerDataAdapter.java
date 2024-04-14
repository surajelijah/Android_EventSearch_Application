package com.example.eventfinderwebtech;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder>{

    Context context;
    ArrayList<RecyclerData> data;

    SharedPreferences pref;

    String date_updated,time_updated;


    RecyclerDataAdapter(Context context,ArrayList<RecyclerData> data){
        this.context=context;
        this.data=data;
        this.pref=context.getSharedPreferences("Favorites",0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.eventrow,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.event_name.setText(data.get(position).event_name);
        holder.event_name.setSelected(true);

        holder.event_venue.setText(data.get(position).event_venue);
        holder.event_venue.setSelected(true);

        holder.event_genre.setText(data.get(position).event_genre);
        holder.event_genre.setSelected(true);

        holder.event_date.setText(data.get(position).date);
        holder.event_date.setSelected(true);





        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(data.get(position).date.toString().trim().length()!=0) {



                Date date_ = sd.parse(data.get(position).date);
                sd = new SimpleDateFormat("MM/dd/yyyy");
                holder.event_date.setText(sd.format(date_).toString());
                holder.event_date.setSelected(true);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        holder.event_time.setText(data.get(position).time);
        holder.event_time.setSelected(true);



        SimpleDateFormat td = new SimpleDateFormat("H:mm:ss");
        try {
        if(data.get(position).time.toString().trim().length()!=0){



            Date time_ = td.parse(data.get(position).time);
            td=new SimpleDateFormat("K:mm:ss aa");
            holder.event_time.setText(td.format(time_));
            holder.event_time.setSelected(true);}
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        holder.event_id.setText(data.get(position).event_id);

        holder.event_position.setText(Integer.toString(position));

        //Check from the Saved Favorites and set this flag
        if(pref.getString(data.get(position).event_id,null)==null){
            holder.event_flag.setText("0");
            holder.event_favorite.setImageResource(R.drawable.heart_outline);
        }
        else{
            holder.event_flag.setText("1");
            holder.event_favorite.setImageResource(R.drawable.heart_filled);
        }


        //Set Image using Picasso or Glide
        Picasso.with(context)
                .load(data.get(position).image_url)
                .into(holder.event_icon);


        holder.event_image_url.setText(data.get(position).image_url);

        holder.event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t=v.findViewById(R.id.event_id);
                //Toast.makeText(context.getApplicationContext(),"Clicked"+t.getText(), Toast.LENGTH_SHORT).show();


                TextView tset=v.findViewById(R.id.event_flag);

                TextView tname=v.getRootView().findViewById(R.id.event_name);


                TextView t_image_url=v.getRootView().findViewById(R.id.event_image_url);
                TextView tvenue=v.getRootView().findViewById(R.id.event_venue);
                TextView tgenre=v.getRootView().findViewById(R.id.event_genre);
                TextView tdate=v.getRootView().findViewById(R.id.event_date);
                TextView ttime=v.getRootView().findViewById(R.id.event_time);





                Intent gotoMain=new Intent(v.getContext(), InfoActivity.class);
                gotoMain.putExtra("event_id",t.getText().toString());
                gotoMain.putExtra("event_flag",tset.getText().toString());
                gotoMain.putExtra("event_name",tname.getText().toString());


                gotoMain.putExtra("event_image_url",t_image_url.getText().toString());
                gotoMain.putExtra("event_venue",tvenue.getText().toString());
                gotoMain.putExtra("event_genre",tgenre.getText().toString());
                gotoMain.putExtra("event_date",tdate.getText().toString());
                gotoMain.putExtra("event_time",ttime.getText().toString());



                //gotoMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //finish();
                v.getContext().startActivity(gotoMain);



            }

        });



        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TextView t=v.findViewById(R.id.event_flag);
                    ImageView iv= v.findViewById(R.id.event_favorite);
                    TextView tp=v.findViewById(R.id.event_position);

                    SharedPreferences.Editor editor= pref.edit();

                    int pos= Integer.parseInt(tp.getText().toString());

                    if(t.getText().toString()=="0"){
                        iv.setImageResource(R.drawable.heart_filled);
                        t.setText("1");
                        //Write code to store the event details



                        editor.putString(data.get(pos).event_id,data.get(pos).toString());
                        editor.commit();


                        Toast.makeText(context.getApplicationContext(),data.get(pos).event_name+" added to favorites", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        iv.setImageResource(R.drawable.heart_outline);
                        t.setText("0");
                        //Write code to remove the event details
                        editor.remove(data.get(pos).event_id);
                        editor.commit();
                        Toast.makeText(context.getApplicationContext(),data.get(pos).event_name+" removed from favorites", Toast.LENGTH_SHORT).show();
                    }

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Here we set the data
        TextView event_name,event_venue,event_genre,event_date,event_time,event_id,event_position,event_flag,event_image_url;
        ImageView event_icon,event_favorite;

        LinearLayout heart,event_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            event_name=itemView.findViewById(R.id.event_name);
            event_venue=itemView.findViewById(R.id.event_venue);
            event_genre=itemView.findViewById(R.id.event_genre);
            event_date=itemView.findViewById(R.id.event_date);
            event_time=itemView.findViewById(R.id.event_time);
            event_id=itemView.findViewById(R.id.event_id);
            event_icon=itemView.findViewById(R.id.event_image);

            event_position=itemView.findViewById(R.id.event_position);
            event_flag=itemView.findViewById(R.id.event_flag);

            event_favorite=itemView.findViewById(R.id.event_favorite);

            heart=itemView.findViewById(R.id.heart);
            event_card=itemView.findViewById(R.id.event_card);

            event_image_url=itemView.findViewById(R.id.event_image_url);



        }
    }
}
