package com.example.finnkinoapp.ui.Movie;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
    Button button1;
    int theatreID;
    View root;

    // initializinf dates
    Date date = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd.MM.yyyy" );
    SimpleDateFormat timeFormatter = new SimpleDateFormat( "HH:mm" );
    String stringDate = dateFormatter.format( date );
    String stringTime = timeFormatter.format( date );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        context = GalleryFragment.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );

        // creating class for movie theatres
        theatreAreas = TheatreAreas.getInstance();
        theatreAreas.readTheatreAreasXML();

        System.out.println( theatreAreas.theatreAmount() ); // --debug
        //System.out.println( "Tänään on " + stringDate ); // --debug

        // initializing textfields
        textDate = (EditText) root.findViewById( R.id.editTextDate );
        textDate.setText( stringDate );
        textTime = (EditText) root.findViewById( R.id.editTextTime );
        textTime.setText( stringTime );

        // initializing button1
        button1 = (Button) root.findViewById( R.id.button1 );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        // INITIALIZING SPINNER
        spinner1 = (Spinner) root.findViewById( R.id.spinner1 );
        ArrayList<String> theatreNames = new ArrayList<>();

        // creating list from movie names
        for (int i = 0; i < theatreAreas.theatreAmount(); i++) {
            theatreNames.add( theatreAreas.getTheatre( i ).getName() );
        }

        // setting up spinner1
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>( getContext(), android.R.layout.simple_spinner_dropdown_item, theatreNames );
        adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner1.setAdapter( adapter1 );
        spinner1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                if (TheatreAreas.getTheatreID((int) spinner1.getSelectedItemId()) != -1){
                    search();
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

    // movie search method
    public void search() {
        // initializing variables for movieseach
        latestMovies = new MovieSearch();
        theatreID = TheatreAreas.getInstance().getTheatreID( Integer.valueOf( (int) spinner1.getSelectedItemId() ) );

        // get movies by search criteria
        latestMovies.readMoviesXML( theatreID, String.valueOf(textDate.getText()), String.valueOf(textTime.getText()) );

        // set information on recyclerview
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter2 = new RecyclerViewAdapter( getContext(), latestMovies.getMovieList() );
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter( adapter2 );

        System.out.println("########RECYCLERVIEW CHANGED########"); // -debug
    }
}