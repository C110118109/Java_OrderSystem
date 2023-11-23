package com.uni.plovdiv.hapnitopni.adapters;



import android.content.Intent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.entities.Locations;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    Context context;
    ArrayList<Locations> locationsArrayList;


    public LocationAdapter(Context context, ArrayList<Locations> locationsArrayList) {
        this.context = context;
        this.locationsArrayList = locationsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.address_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Locations location = locationsArrayList.get(position);

        holder.photo.setImageResource(location.getPhoto());
        holder.name.setText(location.getName());
        holder.address.setText(location.getAddress());
        holder.tab.setText(location.getTab()+"                       ");
       // Set click listener for the button
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action you want to perform when the button is clicked
                // For example, open Google Maps with the location's address
                String address = location.getAddress();

                // Create an intent to open Google Maps with the address
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                context.startActivity(mapIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return locationsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        TextView name;
        TextView address;
        TextView tab;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            photo = itemView.findViewById(R.id.location_photo);
            name = itemView.findViewById(R.id.location_name);
            address = itemView.findViewById(R.id.location_address);
            tab = itemView.findViewById(R.id.location_tab);
            button = itemView.findViewById(R.id.location_button);
        }
    }
}
