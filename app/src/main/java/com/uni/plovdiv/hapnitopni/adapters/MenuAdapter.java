package com.uni.plovdiv.hapnitopni.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.activities.LoginActivity;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> productsArrayList;
    List<Favourites> favourites = new ArrayList<Favourites>();
    MyDBHandler myDbHandler;

    public MenuAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Products product = productsArrayList.get(position);

        holder.image.setImageResource(product.getImage());
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(product.getPrice()+"元");

        holder.favouritebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 將菜單添加至收藏清單
                int image =product.getImage();
                String price =product.getPrice();
                String name =product.getName();
                String de = product.getDescription();
                String status ="T";
                favourites.add(new Favourites(image,name,de,price,status));
                myDbHandler = new MyDBHandler(context, null,null, 1);
                //Log.d("ResID", "price ID: " + price);

                for(Favourites x : favourites){
                    if (myDbHandler.checkFavouriteExist(x) !=true){
                        myDbHandler.addFavourite(x);
                        Log.d("addToFavourites", "sucess");
                        Toast.makeText(context, "餐點收藏成功!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, "餐點已在收藏夾!", Toast.LENGTH_SHORT).show();
                    }

                }
                //Toast.makeText(context, "餐點收藏成功!", Toast.LENGTH_SHORT).show();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;
        TextView price;
        Button favouritebutton;
        Button addtoorder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            favouritebutton = itemView.findViewById(R.id.favourite_button);
            addtoorder = itemView.findViewById(R.id.add_to_order_button);
        }
    }
}
