package com.example.finnkinoapp.ui.Settings;

import java.util.ArrayList;

public class Settings {

    private static Settings instance = null;
    private String language;
    private String theme;
    private ArrayList<String> languages;


    private Settings(){

        // initializing languages
        languages = new ArrayList<>();
        languages.add("Finnish");
        languages.add("English");
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }
}
