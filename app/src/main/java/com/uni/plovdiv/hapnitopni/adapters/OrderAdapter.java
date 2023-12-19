package com.uni.plovdiv.hapnitopni.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.entities.Products;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<Orders> ordersArrayList;


    public OrderAdapter(Context context, ArrayList<Orders> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Orders order = ordersArrayList.get(position);

        holder.image.setImageResource(order.getImage());
        holder.name.setText(order.getName());
        holder.description.setText(order.getDescription());
        holder.price.setText(order.getPrice()+"元");
        holder.qualtity.setText(order.getQuantity());
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;
        TextView price;
        TextView qualtity;
        Button favouritebutton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.orderitem_image);
            name = itemView.findViewById(R.id.orderitem_name);
            description = itemView.findViewById(R.id.orderitem_description);
            price = itemView.findViewById(R.id.orderitem_price);
            qualtity = itemView.findViewById(R.id.orderitem_quantity);
        }
    }
}
