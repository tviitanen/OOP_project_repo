package com.example.finnkinoapp.ui.Movie;

import java.io.Serializable;

public class Movie implements Serializable {

    private int ID;
    private String dttmShowStart;
    private String title;
    private String originalTitle;
    private String ImdbID;
    private String ImdbReview;
    private String director;

    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return(this.ID);
    }

    public void setShowStart(String showStart) {
        this.dttmShowStart = showStart;
    }

    public String getShowStart() {
        return(this.dttmShowStart);
    }

    public void setTitle(String ttle) {
        this.title = ttle;
    }

    public String getTitle() {
        return(this.title);
    }

    public void setOriginalTitle(String ogTitle) {this.originalTitle = ogTitle;}

    public String getOriginalTitle() {return(this.originalTitle);}

    public void setImdbID(String IDNumber) {this.ImdbID = IDNumber;}

    public String getImdbID() {return(this.ImdbID);}

    public void setImdbReview(String review) {this.ImdbReview = review;}

    public String getImdbReview() {return(this.ImdbReview);}

    public void setDirector(String director) {this.director = director;}

    public String getDirector() {return(this.director);}
}
