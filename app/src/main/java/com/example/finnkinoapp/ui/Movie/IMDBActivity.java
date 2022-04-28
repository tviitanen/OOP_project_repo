package com.example.finnkinoapp.ui.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.finnkinoapp.R;


public class IMDBActivity extends AppCompatActivity {
    private Movie movie;
    TextView movieTitle;
    TextView movieDirector;
    TextView movieDirectorName;
    TextView movieReview;
    TextView movieReviewGrade;
    TextView moviePlot;
    TextView moviePlotText;


    @Override
    public void onStart() {
        super.onStart();
        this.movie = (Movie) getIntent().getSerializableExtra("movie");
        System.out.println("In activity: " + movie.getTitle());

        searchApiData();
        changeTextviewTexts();
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
    // Search for ImDb-data
    private void searchApiData() {
        ImdbApiReader apiReader = new ImdbApiReader();

        apiReader.readImdbJSONTitle(this.movie.getOriginalTitle());
        this.movie.setImdbID(apiReader.getImdbId());
        apiReader.readJSONReviews(this.movie.getImdbID());

        this.movie.setImdbReview(apiReader.getReview());
        this.movie.setDirector(apiReader.getDirector());
        this.movie.setPlot(apiReader.getPlot());
    }

    private void changeTextviewTexts() {
        movieTitle = findViewById(R.id.movieTitle);
        movieDirector = findViewById(R.id.movieDirector);
        movieDirectorName = findViewById(R.id.movieDirectorName);
        movieReview = findViewById(R.id.movieReview);
        movieReviewGrade = findViewById(R.id.movieReviewGrade);
        moviePlot = findViewById(R.id.moviePlot);
        moviePlotText = findViewById(R.id.moviePlotText);

        movieTitle.setText(this.movie.getOriginalTitle());
        movieDirector.setText("Director of the movie: ");
        movieDirectorName.setText(this.movie.getDirector());
        movieReview.setText("This movie has received a grade of: ");
        movieReviewGrade.setText(this.movie.getImdbReview());
        moviePlot.setText("Plot of the movie: ");
        if (this.movie.getPlot() == "") {
            moviePlotText.setText("Not available. ");
        } else {
            moviePlotText.setText(this.movie.getPlot());
        }
    }


}