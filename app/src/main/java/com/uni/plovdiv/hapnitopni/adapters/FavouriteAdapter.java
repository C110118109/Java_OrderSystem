package com.uni.plovdiv.hapnitopni.adapters;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    Context context;
    //ArrayList<Products> productsArrayList;
    ArrayList<Favourites> favouritesArrayList;
    private ListView listView = null;

    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor maincursor; // 記錄目前資料庫查詢指標

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
    List<Favourites> favourites = new ArrayList<Favourites>();

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Favourites favourite = favouritesArrayList.get(position);

        holder.image.setImageResource(favourite.getImage());
        holder.name.setText(favourite.getName());
        holder.description.setText(favourite.getDescription());
        holder.price.setText(favourite.getPrice()+"元");
        holder.favouritebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 將菜單添加至收藏清單
                int image =favourite.getImage();
                String price =favourite.getPrice();
                String name =favourite.getName();
                String de = favourite.getDescription();
                String status ="F";
                favourites.add(new Favourites(image,name,de,price,status));
                myDbHandler = new MyDBHandler(context, null,null, 1);
                //Log.d("ResID", "price ID: " + price);

                for(Favourites x : favourites){
                    if (myDbHandler.checkFavouriteExist(x) !=false){
                        myDbHandler.deleteFavourites(name);
                        Log.d("delFromFavourites", "sucess");

                    }
                }
                Toast.makeText(context, "餐點取消收藏!", Toast.LENGTH_SHORT).show();

                notifyItemChanged(holder.getAdapterPosition());

            }
        });


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
