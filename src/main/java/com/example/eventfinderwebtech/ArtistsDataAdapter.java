package com.example.eventfinderwebtech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistsDataAdapter extends RecyclerView.Adapter<ArtistsDataAdapter.ViewHolder> {

    Context context;
    ArrayList<ArtistData> data;
    int pos;
    public ArtistsDataAdapter(Context context, ArrayList<ArtistData> data) {

        this.context=context;
        this.data=data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.artistrow,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.artist_name.setText(data.get(position).artist_name);
        holder.artist_name.setSelected(true);
        holder.artist_people.setText(data.get(position).people);
        holder.artist_people.setSelected(true);

        if(data.get(position).people.toString().trim().length()!=0) {
            int a = Integer.parseInt(data.get(position).people.toString());
            if (a / 1000000 != 0) {
                holder.artist_people.setText(String.valueOf(a / 1000000) + "M Followers");
                holder.artist_people.setSelected(true);

            } else if (a / 1000 != 0) {
                holder.artist_people.setText(String.valueOf(a / 1000) + "K Followers");
                holder.artist_people.setSelected(true);
            }
        }




        holder.artist_spotify.setText(data.get(position).spotify);
        holder.artist_spotify.setSelected(true);
        //holder.artist_spotify.setText(data.get(position).spotify);
        holder.artist_spotify.setText("Check out on Spotify");

        holder.artist_spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.artist_spotify.getText().toString()));
                if(data.get(position).spotify.toString().trim().length()!=0){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(position).spotify.toString()));
                context.startActivity(browserIntent);
                }
            }
        });

        //holder.artist_progress.setMax(100);
        holder.artist_progress.setProgress( 100 - Integer.parseInt(data.get(position).popularity));
        //holder.artist_progress.setIndeterminate(false);

        holder.artist_popularity.setText(data.get(position).popularity);

        //holder.artist_image.setImageResource(R.drawable.heart_filled);

        if(data.get(position).album1.trim().length()!=0){
            Picasso.with(context)
                    .load(data.get(position).album1)
                    .into(holder.artist_image);
        }

        if(data.get(position).album1.trim().length()!=0){
            Picasso.with(context)
                    .load(data.get(position).album1)
                    .into(holder.image1);
        }

        //holder.artist_image.setImageAlpha(holder.image1.getImageAlpha());

        if(data.get(position).album2.trim().length()!=0){
            Picasso.with(context)
                    .load(data.get(position).album2)
                    .into(holder.image2);
        }
        if(data.get(position).album3.trim().length()!=0){
            Picasso.with(context)
                    .load(data.get(position).album3)
                    .into(holder.image3);
        }




        /*holder.event_name.setText(data.get(position).event_name);
        holder.event_name.setSelected(true);

        holder.event_venue.setText(data.get(position).event_venue);
        holder.event_genre.setText(data.get(position).event_genre);
        holder.event_date.setText(data.get(position).date);
        holder.event_time.setText(data.get(position).time);
        holder.event_id.setText(data.get(position).event_id);

        holder.event_position.setText(Integer.toString(position));

        //Check from the Saved Favorites and set this flag
        holder.event_flag.setText("1");
        holder.event_favorite.setImageResource(R.drawable.heart_filled);


        //Set Image using Picasso or Glide
        Picasso.with(context)
                .load(data.get(position).image_url)
                .into(holder.event_icon);





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
        */
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Here we set the data

        TextView artist_name,artist_people,artist_spotify,artist_popularity;
        ImageView artist_image,image1,image2,image3;
        ProgressBar artist_progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            artist_name=itemView.findViewById(R.id.artist_name);
            artist_people=itemView.findViewById(R.id.artist_people);
            artist_spotify=itemView.findViewById(R.id.artist_spotify);
            artist_popularity=itemView.findViewById(R.id.artist_popularity);


            //artist_progress=itemView.findViewById(R.id.artist_progress);
            artist_progress=itemView.findViewById(R.id.artist_progress_);

            artist_image=itemView.findViewById(R.id.artist_image);
            image1=itemView.findViewById(R.id.image1);
            image2=itemView.findViewById(R.id.image2);
            image3=itemView.findViewById(R.id.image3);


        }
    }
}
