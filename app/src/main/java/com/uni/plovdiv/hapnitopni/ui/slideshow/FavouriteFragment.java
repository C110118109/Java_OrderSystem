package com.uni.plovdiv.hapnitopni.ui.slideshow;

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
import com.uni.plovdiv.hapnitopni.adapters.FavouriteAdapter;
import com.uni.plovdiv.hapnitopni.adapters.MenuAdapter;
import com.uni.plovdiv.hapnitopni.entities.Favourites;
import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private ArrayList<Favourites> favouritesArrayList;
    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    Favourites favourite;

    private ArrayList<Favourites> favourites;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.favourite_fragment, container, false);

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favourites = new ArrayList<>();

        myDbHandler = new MyDBHandler(getContext(),null,null,1);
        favouritesArrayList = myDbHandler.allFavourites();
        dataInitialize();

        recycleview = view.findViewById(R.id.recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setHasFixedSize(true);

        FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(), favourites);
        recycleview.setAdapter(favouriteAdapter);
        favouriteAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {

        for (int i = 0; i<favouritesArrayList.size(); i++){

            favourite = new Favourites(favouritesArrayList.get(i).getImage(),favouritesArrayList.get(i).getName(),
                    favouritesArrayList.get(i).getDescription()
                    ,favouritesArrayList.get(i).getPrice());

            favourites.add(favourite);
        }

    }

}