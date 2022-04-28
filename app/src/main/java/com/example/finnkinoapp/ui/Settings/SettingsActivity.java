package com.example.finnkinoapp.ui.Settings;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.ui.Favorites.Favorites;
import com.example.finnkinoapp.ui.Favorites.HandleFavXML;
import com.example.finnkinoapp.ui.Favorites.SetFavoritesActivity;
import com.example.finnkinoapp.ui.Movie.TheatreAreas;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private Button button1, button2;
    private Spinner spinner1;
    private Switch switch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        // actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing settings class
        if (settings == null) {
            settings = HandleSettingsXML.getInstance().readSetXML( getApplicationContext() );
        }

        // initializing switch1
        switch1 = (Switch) findViewById( R.id.switch1 );

        // setting up spinner1
        spinner1 =  (Spinner) findViewById( R.id.spinner1 );
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, settings.getLanguages() );
        adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner1.setAdapter( adapter1 );

        // initializing button1
        button1 = (Button) findViewById( R.id.button_back );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.finish(); // close SetFavorites
            }
        });

        // initializing button2
        button2 = (Button) findViewById( R.id.button_save );
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // setting spinner data (language)
                    settings.setLanguage( String.valueOf( spinner1.getSelectedItem() ) );

                    // get switch data (theme)
                    if (switch1.isChecked()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        settings.setTheme( true );

                    }
                    // disable textedit in main_activity
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        settings.setTheme( false );

                    }

                    // write setting on xml
                    HandleSettingsXML.getInstance().writeSetXML( getApplicationContext(), settings );

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // send toast
                Toast.makeText( SettingsActivity.this, "\"Settings saved successfully.\"", Toast.LENGTH_LONG).show();

            }
        });

        // load current settings
        try {
            switch1.setChecked( settings.getTheme() );

            if (settings.getLanguage() != null) {
                int spinnerPosition = adapter1.getPosition( settings.getLanguage() );
                spinner1.setSelection( spinnerPosition );
            }

        } catch (RuntimeException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


    // back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SettingsActivity.this.finish(); // close SetFavorites
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
