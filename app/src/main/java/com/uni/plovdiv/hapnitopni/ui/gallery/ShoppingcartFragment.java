package com.uni.plovdiv.hapnitopni.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.adapters.LocationAdapter;
import com.uni.plovdiv.hapnitopni.adapters.OrderAdapter;
import com.uni.plovdiv.hapnitopni.entities.Locations;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class ShoppingcartFragment extends Fragment  {

    private ArrayList<Orders> ordersArrayList;
    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    Orders order;
    Button checkout;
    private ArrayList<Orders> orders;
    private Cursor maincursor; // 記錄目前資料庫查詢指標
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private ListView listView = null;
    Button toGMap;
    private MyDBHandler dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //productsInfo = myDbHandler.allProducts();
        View root = inflater.inflate(R.layout.shoppinrcart_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        checkout = view.findViewById(R.id.checkoutButton);
        checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myDbHandler = new MyDBHandler(getContext(), null,null, 1);
                // 创建并显示 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("送出餐點");
                builder.setMessage("確定將購物車內的餐點送出結帳嗎?");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDbHandler.clearOrderData();
                        Toast.makeText(getContext(), "餐點已送出!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        // 更新数据源
                        orders.clear();
                        ordersArrayList.clear();
                        ordersArrayList = myDbHandler.allOrders();
                        dataInitialize();

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
            }
        });

        super.onViewCreated(view, savedInstanceState);

        orders = new ArrayList<>();

        myDbHandler = new MyDBHandler(getContext(),null,null,1);
        ordersArrayList = myDbHandler.allOrders();
        dataInitialize();

        recycleview = view.findViewById(R.id.recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setHasFixedSize(true);

        OrderAdapter orderAdapter = new OrderAdapter(getContext(), orders);
        recycleview.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {

        for (int i = 0; i<ordersArrayList.size(); i++){

             order = new Orders(ordersArrayList.get(i).getImage(),
                     ordersArrayList.get(i).getName(),
                     ordersArrayList.get(i).getDescription(),
                     ordersArrayList.get(i).getPrice(),
                     ordersArrayList.get(i).getQuantity());

            orders.add(order);
        }

    }



}