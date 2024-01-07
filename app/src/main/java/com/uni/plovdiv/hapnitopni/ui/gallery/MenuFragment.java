package com.uni.plovdiv.hapnitopni.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.adapters.MenuAdapter;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class MenuFragment extends Fragment  {

    private ArrayList<Products> productsArrayList;
    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    Products product;

    private ArrayList<Products> products;
    FloatingActionButton fab;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //productsInfo = myDbHandler.allProducts();
        View root = inflater.inflate(R.layout.menu_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabToShoppingCart);


        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_nav_menu_to_nav_order);

            }
        });

        products = new ArrayList<>();

        myDbHandler = new MyDBHandler(getContext(),null,null,1);
        productsArrayList = myDbHandler.allProducts();
        dataInitialize();

        recycleview = view.findViewById(R.id.recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setHasFixedSize(true);

        MenuAdapter menuAdapter = new MenuAdapter(getContext(), products);
        recycleview.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {

        for (int i = 0; i<productsArrayList.size(); i++){

             product = new Products(productsArrayList.get(i).getImage(),productsArrayList.get(i).getName(),
                                   productsArrayList.get(i).getDescription()
                                   ,productsArrayList.get(i).getPrice());

            products.add(product);
        }

    }
}