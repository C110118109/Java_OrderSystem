package com.uni.plovdiv.hapnitopni.adapters;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.uni.plovdiv.hapnitopni.activities.LoginActivity;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> productsArrayList;
    ArrayList<Orders> ordersArrayList;
    List<Favourites> favourites = new ArrayList<Favourites>();
    List<Orders> orders = new ArrayList<Orders>();
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
        //Orders order = ordersArrayList.get(position);
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


        holder.addtoorder.setOnClickListener(new View.OnClickListener() {
            String name =product.getName();
            int image =product.getImage();
            int price = Integer.parseInt(product.getPrice());
            String de = product.getDescription();
            //int quantity=order.getQuantity();
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialogView = inflater.inflate(R.layout.dialog_picker, null);

                // 获取视图中的控件
                final NumberPicker quantityPicker = dialogView.findViewById(R.id.picker);
                quantityPicker.setMinValue(1);
                quantityPicker.setMaxValue(10);
                // 创建并显示 AlertDialog
                builder.setView(dialogView);
                builder.setTitle("新增至購物車");
                builder.setMessage("選擇'"+name+"'的餐點數量:");
                //builder.setMessage("");
                builder.setPositiveButton("新增", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedQuantity = quantityPicker.getValue();
                        price=price*selectedQuantity;
                        // 在这里添加到订单的逻辑`
                        // 添加代码以将产品添加到订单中
                        // ...
                        orders.add(new Orders(image,name,de,price,selectedQuantity));
                        myDbHandler = new MyDBHandler(context, null,null, 1);
                        for(Orders x : orders){
                            if (myDbHandler.checkOrderExist(x) !=true){
                                myDbHandler.addOrder(x);
                                Log.d("addToOrders", "sucess");
                                Toast.makeText(context, "餐點以新增至購物車!", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context, "餐點已在購物車內!", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                builder.setTitle("重複新增餐點");
//                                builder.setMessage("是否直接再次增加數量至購物車?");
//                                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        Toast.makeText(context, "數量新增成功!", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//                                });
//                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });

                            }

                        }
                        //Toast.makeText(context, "餐點收藏成功!", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(holder.getAdapterPosition());

                        Toast.makeText(context, "新增" + selectedQuantity + "杯"+name, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
