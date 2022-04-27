package com.example.finnkinoapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.ui.Movie.Movie;

public class IMDBActivity extends AppCompatActivity {
    private Movie movie;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        this.movie = (Movie) getIntent().getSerializableExtra("movie");
        System.out.println("In activity: " + movie.getTitle());

        // Search for ImDb-data
        ImdbApiReader apiReader = new ImdbApiReader();

        apiReader.readImdbJSONTitle(this.movie.getOriginalTitle());
        this.movie.setImdbID(apiReader.getImdbId());
        apiReader.readJSONReviews(this.movie.getImdbID());

        this.movie.setImdbReview(apiReader.getReview());
        this.movie.setDirector(apiReader.getDirector());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdbactivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    // back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}