package com.example.finnkinoapp.ui.Favorites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.finnkinoapp.R;

import java.io.FileNotFoundException;

public class SetFavorites extends AppCompatActivity {

    private Button button1, button2;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_setfavorites);

        // initializing editTexts
        editText1 = (EditText) findViewById( R.id.editText1 );
        editText2 = (EditText) findViewById( R.id.editText2 );
        editText3 = (EditText) findViewById( R.id.editText3 );
        editText4 = (EditText) findViewById( R.id.editText3 );
        editText5 = (EditText) findViewById( R.id.editText5 );
        editText6 = (EditText) findViewById( R.id.editText6 );

        // initializing button1
        button1 = (Button) findViewById( R.id.button1 );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to slideshow fragment after changing favorites
                SlideshowFragment slideshowFragment = new SlideshowFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.imageView, slideshowFragment).commit(); //imageView
                //Toast.makeText( SetFavorites.this, "\"You are now changed favorites.\"", Toast.LENGTH_LONG).show();

            }
        });

        // initializing button2
        button2 = (Button) findViewById( R.id.button2 );
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // THIS SHOULD BE MOVED TO DIFFERENT ACTIVITY
                    // setting favorites
                    Favorites favorites = new Favorites();
                    favorites.setUser( String.valueOf( editText1.getText() ) );
                    favorites.setFavorite1( String.valueOf( editText2.getText() ) );
                    favorites.setFavorite2( String.valueOf( editText3.getText() ) );
                    favorites.setFavorite3( String.valueOf( editText4.getText() ) );
                    favorites.setFavorite4( String.valueOf( editText5.getText() ) );
                    favorites.setFavorite5( String.valueOf( editText6.getText() ) );

                    // writing favorites into xml
                    HandleFavXML.getInstance().writeXML( getApplicationContext(), favorites );

                    // send toast
                    Toast.makeText( SetFavorites.this, "\"Favorites saved successfully.\"", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}