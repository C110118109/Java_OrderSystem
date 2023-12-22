package com.uni.plovdiv.hapnitopni.adapters;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<Orders> ordersArrayList;
    MyDBHandler myDbHandler;
    private SQLiteDatabase db;

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
        holder.quantity.setText(order.getQuantity()+"杯");
        holder.description.setText(order.getDescription());
        holder.price.setText(order.getPrice()+"元");
//        holder.checkout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                myDbHandler = new MyDBHandler(context, null,null, 1);
//                // 创建并显示 AlertDialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("送出餐點");
//                builder.setMessage("確定將購物車內的餐點送出結帳嗎?");
//                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        myDbHandler.clearOrderData();
//                        Toast.makeText(context, "餐點已送出!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

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
        TextView quantity;
//        Button checkout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.orderitem_image);
            name = itemView.findViewById(R.id.orderitem_name);
            quantity = itemView.findViewById(R.id.orderitem_quantity);
            description = itemView.findViewById(R.id.orderitem_description);
            price = itemView.findViewById(R.id.orderitem_price);
//            checkout = itemView.findViewById(R.id.checkoutButton);
        }
    }
}
