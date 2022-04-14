package com.example.finnkinoapp.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentGalleryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryFragment context;
    RecyclerViewAdapter adapter2;
    TheatreAreas theatreAreas;
    MovieSearch latestMovies;
    Spinner spinner1;
    RecyclerView recyclerView1;
    EditText textTime, textDate;
    int teatteriID;
    View root;

    // määritetään päivämäärä
    Date date = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd.MM.yyyy" );
    SimpleDateFormat timeFormatter = new SimpleDateFormat( "HH:mm" );
    String stringDate = dateFormatter.format( date );
    String stringTime = timeFormatter.format( date );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //setContentView( R.layout.finnkino_activity);

        context = GalleryFragment.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );


        // luodaan tietorakenne elokuvateattereille
        theatreAreas = TheatreAreas.getInstance();
        theatreAreas.readTheatreAreasXML();

        System.out.println( theatreAreas.theatreAmount() ); // --debug
        System.out.println( "Tänään on " + stringDate ); // --debug

        // maaritetaan tekstikentat
        textDate = (EditText) root.findViewById( R.id.editTextDate );
        textDate.setText( stringDate );
        textTime = (EditText) root.findViewById( R.id.editTextTime );
        textTime.setText( stringTime );


        // MAARITETAAN SPINNERI
        spinner1 = (Spinner) root.findViewById( R.id.spinner1 );
        ArrayList<String> theatreNames = new ArrayList<>();

        // luodaan lista teattereiden nimistä
        for (int i = 0; i < theatreAreas.theatreAmount(); i++) {
            theatreNames.add( theatreAreas.getTheatre( i ).getName() );

        }

        // asetetaan listojen sisallot spinnereihin
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>( getContext(), android.R.layout.simple_spinner_dropdown_item, theatreNames );
        adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner1.setAdapter( adapter1 );

        spinner1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                if (TheatreAreas.getTheatreID((int) spinner1.getSelectedItemId()) != -1){
                    // get information on user choice theatre
                    System.out.println( "Valinta " + spinner1.getSelectedItemId() ); // --debug
                    latestMovies = new MovieSearch();
                    teatteriID = TheatreAreas.getInstance().getTheatreID( Integer.valueOf( (int) spinner1.getSelectedItemId() ) );
                    latestMovies.readMoviesXML( teatteriID, stringDate );


                    // set information on recyclerview
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter2 = new RecyclerViewAdapter( getContext(), latestMovies.getMovieList() );
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setAdapter( adapter2 );


                    System.out.println("########RECYCLERVIEW CHANGED########");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        } );

        // set up the RecyclerView
        recyclerView1 = (RecyclerView) root.findViewById(R.id.recyclerView1);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}