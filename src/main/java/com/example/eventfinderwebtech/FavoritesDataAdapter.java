package com.example.eventfinderwebtech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FavoritesDataAdapter extends RecyclerView.Adapter<FavoritesDataAdapter.ViewHolder> {

    Context context;
    ArrayList<RecyclerData> data;
    int pos;
    public FavoritesDataAdapter(Context context, ArrayList<RecyclerData> data) {

        this.context=context;
        this.data=data;

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
        //holder.event_date.setText(data.get(position).date);
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_=sd.parse(data.get(position).date);
            sd=new SimpleDateFormat("MM/dd/yyyy");
            holder.event_date.setText(sd.format(date_).toString());
            holder.event_date.setSelected(true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.event_time.setText(data.get(position).time);
        holder.event_time.setSelected(true);
        holder.event_id.setText(data.get(position).event_id);

        holder.event_position.setText(Integer.toString(position));

        //Check from the Saved Favorites and set this flag
        holder.event_flag.setText("1");
        holder.event_favorite.setImageResource(R.drawable.heart_filled);


        //Set Image using Picasso or Glide
        Picasso.with(context)
                .load(data.get(position).image_url)
                .into(holder.event_icon);

        holder.event_image_url.setText(data.get(position).image_url);


        holder.event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TextView t=v.findViewById(R.id.event_id);
                //Toast.makeText(context.getApplicationContext(),"Clicked"+t.getText(), Toast.LENGTH_SHORT).show();


                TextView tset=v.findViewById(R.id.event_flag);

                TextView tname=v.getRootView().findViewById(R.id.event_name);
                */

                Intent gotoMain=new Intent(v.getContext(), InfoActivity.class);
                gotoMain.putExtra("event_id",holder.event_id.getText().toString());
                gotoMain.putExtra("event_flag",holder.event_flag.getText().toString());
                gotoMain.putExtra("event_name",holder.event_name.getText().toString());

                gotoMain.putExtra("event_image_url",holder.event_image_url.getText().toString());
                gotoMain.putExtra("event_venue",holder.event_venue.getText().toString());
                gotoMain.putExtra("event_genre",holder.event_genre.getText().toString());
                gotoMain.putExtra("event_date",holder.event_date.getText().toString());
                gotoMain.putExtra("event_time",holder.event_time.getText().toString());




                //gotoMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //finish();
                v.getContext().startActivity(gotoMain);

            }

        });
















        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView tp=v.findViewById(R.id.event_position);
                SharedPreferences pref=context.getSharedPreferences("Favorites",0);
                SharedPreferences.Editor editor= pref.edit();
                FrameLayout favorites_frame=(FrameLayout) v.findViewById(R.id.favorites_frame);

                pos= Integer.parseInt(tp.getText().toString());


                    //Write code to remove the event details
                    editor.remove(data.get(pos).event_id);
                    editor.commit();

                    Toast.makeText(context.getApplicationContext(),data.get(pos).event_name+" removed from favorites", Toast.LENGTH_SHORT).show();
                        data.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, data.size());

                        //notifyDataSetChanged();

                if(data.size()==0){
                    RecyclerView rv= v.getRootView().findViewById(R.id.favorites_list);
                    rv.setVisibility(View.GONE);
                }



                //Toast.makeText(context.getApplicationContext(),data.get(pos).event_name+" removed from favorites", Toast.LENGTH_SHORT).show();
                }

        });

        //data.remove(pos);
        //this.notifyItemChanged(pos);

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
