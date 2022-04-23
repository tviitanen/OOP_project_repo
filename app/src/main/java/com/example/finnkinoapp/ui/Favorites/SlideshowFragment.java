package com.example.finnkinoapp.ui.Favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentSlideshowBinding;

import java.io.FileNotFoundException;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private Context context;
    private Button button1, button2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = this.getContext();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        HandleFavXML handleFavXML = new HandleFavXML();

        // initializing button1
        button1 = (Button) root.findViewById( R.id.button1 );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // setting favorites
                    Favorites favorites = new Favorites();
                    favorites.setUser( "mrMikoma" );
                    favorites.setFavorite1( "MCHelper beKINGs 1" );
                    favorites.setFavorite2( "MCHelper beKINGs 2" );
                    favorites.setFavorite3( "MCHelper beKINGs 3" );
                    favorites.setFavorite4( "MCHelper beKINGs 4" );
                    favorites.setFavorite5( "MCHelper beKINGs 5" );

                    // writing favorites into xml
                    handleFavXML.writeXML( context, favorites );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // initializing button2
        button2 = (Button) root.findViewById( R.id.button2 );
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFavXML.readXML( context );
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}