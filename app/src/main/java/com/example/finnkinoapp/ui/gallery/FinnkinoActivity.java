package com.example.finnkinoapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finnkinoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinnkinoActivity extends AppCompatActivity {

    // määritetään luokan muuttujat
    private FinnkinoActivity context;
    RecyclerViewAdapter adapter2;
    TheatreAreas theatreAreas;
    MovieSearch latestMovies;
    Spinner spinner1;
    RecyclerView recyclerView1;
    EditText textTime, textDate;
    int teatteriID;


    // määritetään päivämäärä
    Date date = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd.MM.yyyy" );
    SimpleDateFormat timeFormatter = new SimpleDateFormat( "HH:mm" );
    String stringDate = dateFormatter.format( date );
    String stringTime = timeFormatter.format( date );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finnkino_activity);

        context = FinnkinoActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );
        System.out.println( "KANSION SIJAINTI " + context.getFilesDir() ); // --debug


        // luodaan tietorakenne elokuvateattereille
        theatreAreas = TheatreAreas.getInstance();
        theatreAreas.readTheatreAreasXML();

        System.out.println( theatreAreas.theatreAmount() ); // --debug
        System.out.println( "Tänään on " + stringDate ); // --debug

        // maaritetaan tekstikentat
        textDate = (EditText) findViewById( R.id.editTextDate );
        textDate.setText( stringDate );
        textTime = (EditText) findViewById( R.id.editTextTime );
        textTime.setText( stringTime );


        // MAARITETAAN SPINNERI
        spinner1 = (Spinner) findViewById( R.id.spinner1 );
        ArrayList<String> theatreNames = new ArrayList<>();

        // luodaan lista teattereiden nimistä
        for (int i = 0; i < theatreAreas.theatreAmount(); i++) {
            theatreNames.add( theatreAreas.getTheatre( i ).getName() );

        }

        // asetetaan listojen sisallot spinnereihin
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, theatreNames );
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
                    recyclerView1.setLayoutManager(new LinearLayoutManager(context));
                    adapter2 = new RecyclerViewAdapter( context, latestMovies.getMovieList() );
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
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);

    }
}
