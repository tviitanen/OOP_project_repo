package com.example.finnkinoapp.ui.Movie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentGalleryBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MovieSearchFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private MovieSearchFragment context;
    RecyclerViewAdapter adapter2;
    TheatreAreas theatreAreas;
    MovieSearch latestMovies;
    Spinner spinner1;
    RecyclerView recyclerView1;
    EditText textTime, textDate;
    Button button1;
    int theatreID;
    View root;
    final Calendar calendar = Calendar.getInstance();

    // initializing dates
    Date date = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd.MM.yyyy" );
    SimpleDateFormat timeFormatter = new SimpleDateFormat( "HH:mm" );
    String stringDate = dateFormatter.format( date );
    String stringTime = timeFormatter.format( date );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        context = MovieSearchFragment.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );

        // creating class for movie theatres
        theatreAreas = TheatreAreas.getInstance();
        theatreAreas.readTheatreAreasXML();

        System.out.println( theatreAreas.theatreAmount() ); // --debug
        //System.out.println( "Tänään on " + stringDate ); // --debug

        // initializing text fields
        textDate = (EditText) root.findViewById( R.id.editTextDate );
        textDate.setText( stringDate );
        textTime = (EditText) root.findViewById( R.id.editTextTime );
        textTime.setText( stringTime );

        // initializing button1
        button1 = (Button) root.findViewById( R.id.button_back );
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
            @RequiresApi(api = Build.VERSION_CODES.O)
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

        // calendar functionality
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateLabel();
            }
        };
        // time picker functionality
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        // click listener for date
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // click listener for time
        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), time, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                ).show();
            }
        });


        // set up the RecyclerView
        recyclerView1 = (RecyclerView) root.findViewById(R.id.recyclerView1);

        // item separator lines in recycler view
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView1.addItemDecoration(itemDecoration);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // update textDate from calendar
    private void updateDateLabel(){
        textDate.setText(dateFormatter.format(calendar.getTime()));
    }
    // update textTime from clock
    private void updateTimeLabel(){
        textTime.setText(timeFormatter.format(calendar.getTime()));
    }

    // movie search method
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search() {
        // initializing variables for movie search
        latestMovies = new MovieSearch();
        theatreID = TheatreAreas.getInstance().getTheatreID( Integer.valueOf( (int) spinner1.getSelectedItemId() ) );

        // error handle for user date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(String.valueOf(textDate.getText()), dtf);

        } catch (java.time.format.DateTimeParseException e){
            textDate.setError(getText(R.string.dateForm));
            textDate.requestFocus();
            return;
        }

        // error handle for user time
        DateTimeFormatter dtt = DateTimeFormatter.ofPattern("H:mm");
        try {
            LocalTime.parse(String.valueOf(textTime.getText()), dtt);

        } catch (java.time.format.DateTimeParseException e){
            textTime.setError(getText(R.string.timeForm));
            textTime.requestFocus();
            return;
        }

        // get movies by search criteria
        latestMovies.readMoviesXML( theatreID, String.valueOf(textDate.getText()) , String.valueOf(textTime.getText()) );

        // set information on recyclerview
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter2 = new RecyclerViewAdapter( getContext(), latestMovies.getMovieList() );
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter( adapter2 );

        System.out.println("########RECYCLERVIEW CHANGED########"); // -debug
        return;
    }
}