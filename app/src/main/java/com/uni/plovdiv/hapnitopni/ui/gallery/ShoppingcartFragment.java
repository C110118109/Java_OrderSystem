package com.uni.plovdiv.hapnitopni.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    private ArrayList<Orders> orders;


    Button toGMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //productsInfo = myDbHandler.allProducts();
        View root = inflater.inflate(R.layout.shoppinrcart_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                     ordersArrayList.get(i).getQuantity(),
                     ordersArrayList.get(i).getDescription(),
                     ordersArrayList.get(i).getPrice());

            orders.add(order);
        }

    }
}