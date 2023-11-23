package com.uni.plovdiv.hapnitopni.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.adapters.LocationAdapter;
import com.uni.plovdiv.hapnitopni.entities.Locations;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class LocationFragment extends Fragment  {

    private ArrayList<Locations> locationsArrayList;
    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    Locations location;

    private ArrayList<Locations> locations;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //productsInfo = myDbHandler.allProducts();
        return inflater.inflate(R.layout.location_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locations = new ArrayList<>();

        myDbHandler = new MyDBHandler(getContext(),null,null,1);
        locationsArrayList = myDbHandler.allLocations();
        dataInitialize();

        recycleview = view.findViewById(R.id.recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setHasFixedSize(true);

        LocationAdapter locationAdapter = new LocationAdapter(getContext(), locations);
        recycleview.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {

        for (int i = 0; i<locationsArrayList.size(); i++){

             location = new Locations(locationsArrayList.get(i).getPhoto(),locationsArrayList.get(i).getName(),
                                   locationsArrayList.get(i).getAddress()
                                   ,locationsArrayList.get(i).getTab());

            locations.add(location);
        }

    }
}