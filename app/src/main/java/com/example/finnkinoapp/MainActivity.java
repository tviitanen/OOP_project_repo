/*****************************************************
* Finnkino app
*
* @authors Teemu Viitanen, Miko Mattila, Otto Oikarinen
*
* Minimum Android API level 26
*
* Licensed under GNU General Public License v3.0
*
*
******************************************************/


package com.example.finnkinoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.finnkinoapp.ui.Favorites.SetFavoritesActivity;
import com.example.finnkinoapp.ui.Settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finnkinoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        setSupportActionBar( binding.appBarMain.toolbar );
        binding.appBarMain.fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Send feedback via email", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();

                // open email app and set mail address + subject
                try {
                    Intent intent = new Intent( Intent.ACTION_MAIN );
                    intent.putExtra( Intent.EXTRA_EMAIL, "support@finnkino.fi" );
                    intent.putExtra( Intent.EXTRA_SUBJECT, "Feedback" );
                    intent.addCategory( Intent.CATEGORY_APP_EMAIL );
                    MainActivity.this.startActivity( intent );
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText( MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow )
                .setOpenableLayout( drawer )
                .build();
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment_content_main );
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController( navigationView, navController );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        System.out.println( "Käydäänkö täällä?" );
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment_content_main );
        return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp();
    }

    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected( item );

        // start 'settings' activity
        Intent intentSetFavorites = new Intent( getApplicationContext() , SettingsActivity.class);
        startActivity(intentSetFavorites);

        return true;
    }
}
