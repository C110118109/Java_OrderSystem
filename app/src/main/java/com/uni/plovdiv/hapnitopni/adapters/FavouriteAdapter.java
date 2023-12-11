package com.uni.plovdiv.hapnitopni.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    Context context;
    //ArrayList<Products> productsArrayList;
    ArrayList<Favourites> favouritesArrayList;

    public FavouriteAdapter(Context context, ArrayList<Favourites> favouritesArrayList) {
        this.context = context;
        this.favouritesArrayList = favouritesArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favourite_item,parent, false);
        return new MyViewHolder(v);
    }
    MyDBHandler myDbHandler;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Favourites favourite = favouritesArrayList.get(position);

        holder.image.setImageResource(favourite.getImage());
        holder.name.setText(favourite.getName());
        holder.description.setText(favourite.getDescription());
        holder.price.setText(favourite.getPrice()+"元");



    }

    @Override
    public int getItemCount() {
        return favouritesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;
        TextView price;
        Button favouritebutton;
        Button addtoorder;

        public MyViewHolder(@NonNull View favouriteView) {
            super(favouriteView);
            image = favouriteView.findViewById(R.id.fa_product_image);
            name = favouriteView.findViewById(R.id.fa_product_name);
            description = favouriteView.findViewById(R.id.fa_product_description);
            price = favouriteView.findViewById(R.id.fa_product_price);
            favouritebutton = favouriteView.findViewById(R.id.fa_favourite_button);
            addtoorder = favouriteView.findViewById(R.id.fa_add_to_order_button);
        }
    }
}
