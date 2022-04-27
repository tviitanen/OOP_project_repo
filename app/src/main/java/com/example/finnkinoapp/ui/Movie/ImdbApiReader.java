package com.example.finnkinoapp.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImdbApiReader {
    private String titleSearchUrlAddress = "https://imdb-api.com/en/API/SearchMovie/";
    private String movieDataSearchUrlAddress = "https://imdb-api.com/en/API/Title/";
    private String token = "k_2e41pggq/";
    private String json;
    private String imdbId;
    private String review;
    private String director;

    public ImdbApiReader(){}

        // Search from the API for the correct movie and get the JSON
    private String getImdbJSONTitle(String movieTitle) {
        String toReturn = null;
        try {
            // Connect to the URL
            URL url = new URL(titleSearchUrlAddress + token + movieTitle);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            // Form a string from the JSON
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            toReturn = stringBuilder.toString();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

        // Read the JSON
    public void readImdbJSONTitle(String movieName) {
        this.json = getImdbJSONTitle(movieName);
        System.out.println("JSON: " + this.json);

        if (this.json != null) {
            try {
                JSONObject jsonObject = new JSONObject(this.json);
                JSONArray array = jsonObject.getJSONArray("results");
                String results = array.getString(0);
                System.out.println("Results: " + results);
                JSONObject object2 = new JSONObject(results);
                this.imdbId = object2.getString("id");

                System.out.println("ID: " + this.imdbId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
        // Get the JSON with the correct ID
    private String getJSONReviews(String id) {
        String toReturn = null;
        try {
            // Connect to the URL
            URL url = new URL(movieDataSearchUrlAddress + token + id + "/Ratings");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            // Form a string from the JSON
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            toReturn = stringBuilder.toString();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

        // Get data from the api with the correct ID
    public void readJSONReviews(String movieID) {
        this.json = getJSONReviews(movieID);
        System.out.println(this.json);

        if (this.json != null) {
            try {
                JSONObject jsonObject = new JSONObject(this.json);
                this.review = jsonObject.getString("imDbRating");
                this.director = jsonObject.getString("directors");

                System.out.println("Rating: " + this.review);
                System.out.println("Director: " + this.director);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImdbId() {
        return this.imdbId;
    }

    public String getReview() {
        return this.review;
    }

    public String getDirector() {
        return this.director;
    }
}

