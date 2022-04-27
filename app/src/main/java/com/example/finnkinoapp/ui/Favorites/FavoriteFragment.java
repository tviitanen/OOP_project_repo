package com.example.finnkinoapp.ui.Favorites;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    private HandleFavXML handleFavXML;
    private Context context;
    private Button button1;
    RecyclerViewAdapterFavorites adapter1;
    RecyclerView recyclerView1;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = this.getContext();

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        // initializing classes
        handleFavXML = HandleFavXML.getInstance();

        // setting up recycler view for favorites
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        ArrayList<Favorites> favoritesArrayList = handleFavXML.readXML( context );
        setRecyclerView(favoritesArrayList);

        // initializing button1
        button1 = (Button) view.findViewById( R.id.button1 );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start 'setfavorites' activity
                Intent intentSetFavorites = new Intent( context , SetFavoritesActivity.class);
                startActivity(intentSetFavorites);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView(handleFavXML.readXML( context ));
    }

    //set data to recyclerview
    public void setRecyclerView(ArrayList<Favorites> favoritesArrayList){
        // set information on recyclerview
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new RecyclerViewAdapterFavorites( getContext(), favoritesArrayList );
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter( adapter1 );
        System.out.println("########RECYCLERVIEW CHANGED########"); // -debug

    }

}