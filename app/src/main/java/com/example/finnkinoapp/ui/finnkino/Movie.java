package com.example.finnkinoapp;

public class Movie {

    private int ID;
    private String dttmShowStart;
    private String title;

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

}
