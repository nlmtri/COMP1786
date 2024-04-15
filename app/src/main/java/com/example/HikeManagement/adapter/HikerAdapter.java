package com.example.HikeManagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HikeManagement.R;
import com.example.HikeManagement.activities.UpdateActivity;

import java.util.ArrayList;

public class HikerAdapter extends RecyclerView.Adapter<HikerAdapter.MyViewHolder> {

    private final Context context;
    Activity activity;
    private final ArrayList hike_id, hike_name, hike_location, hike_date, hike_parking_available, hike_length, hike_weather_forecast, hike_time_estimated, hike_difficulty_level, hike_description;
    public HikerAdapter(
            Activity activity,
            Context context,
            ArrayList hike_id,
            ArrayList hike_name,
            ArrayList hike_location,
            ArrayList hike_date,
            ArrayList hike_parking_available,
            ArrayList hike_length,
            ArrayList hike_weather_forecast,
            ArrayList hike_time_estimated,
            ArrayList hike_difficulty_level,
            ArrayList hike_description
    ){
        this.activity = activity;
        this.context = context;
        this.hike_id = hike_id;
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_date = hike_date;
        this.hike_parking_available = hike_parking_available;
        this.hike_length = hike_length;
        this.hike_weather_forecast = hike_weather_forecast;
        this.hike_time_estimated = hike_time_estimated;
        this.hike_difficulty_level = hike_difficulty_level;
        this.hike_description = hike_description;

    }

    @NonNull
    @Override
    public HikerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikerAdapter.MyViewHolder holder, int position) {
        holder.hike_id_txt.setText(String.valueOf(hike_id.get(position)));
        holder.hike_name_txt.setText(String.valueOf(hike_name.get(position)));
        holder.hike_location_txt.setText(String.valueOf(hike_location.get(position)));
        holder.hike_date_txt.setText(String.valueOf(hike_date.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(hike_id.get(position)));
                intent.putExtra("name", String.valueOf(hike_name.get(position)));
                intent.putExtra("location", String.valueOf(hike_location.get(position)));
                intent.putExtra("date", String.valueOf(hike_date.get(position)));
                intent.putExtra("parkingAvailable", String.valueOf(hike_parking_available.get(position)));
                intent.putExtra("length", String.valueOf(hike_length.get(position)));
                intent.putExtra("weatherForecast", String.valueOf(hike_weather_forecast.get(position)));
                intent.putExtra("estimatedTime", String.valueOf(hike_time_estimated.get(position)));
                intent.putExtra("difficultyLevel", String.valueOf(hike_difficulty_level.get(position)));
                intent.putExtra("description", String.valueOf(hike_description.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hike_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hike_id_txt, hike_name_txt, hike_location_txt, hike_date_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hike_id_txt = itemView.findViewById(R.id.hike_id_txt);
            hike_name_txt = itemView.findViewById(R.id.hike_name_txt);
            hike_location_txt = itemView.findViewById(R.id.hike_location_txt);
            hike_date_txt = itemView.findViewById(R.id.hike_date_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}