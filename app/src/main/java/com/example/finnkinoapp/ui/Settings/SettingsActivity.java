package com.example.finnkinoapp.ui.Settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.ui.Favorites.SetFavoritesActivity;
import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity {

    private Button button1;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        // actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
