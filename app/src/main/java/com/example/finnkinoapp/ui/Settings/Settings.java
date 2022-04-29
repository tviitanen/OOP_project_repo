package com.example.finnkinoapp.ui.Settings;

import java.util.ArrayList;
import java.util.Locale;

public class Settings {

    private static Settings instance = null;
    private String language;
    private Boolean theme;
    private ArrayList<String> languages;


    private Settings(){
        // initializing languages
        languages = new ArrayList<>();
        setLanguages(languages);
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

    public void setTheme(Boolean theme) {
        this.theme = theme;
    }

    public Boolean getTheme() {
        return theme;
    }

    public void setLanguages(ArrayList<String> languages) {
        languages = new ArrayList<>();

        // set in finnish if needed
        if (Locale.getDefault().getLanguage() == "fi") {
            languages.add("Suomi");
            languages.add("Englanti");
        } else {
            languages.add("Finnish");
            languages.add("English");
        }

        this.languages = languages;
    }

    public ArrayList<String> getLanguages() {
        if (Locale.getDefault().getLanguage() == "fi") {
            setLanguages(this.languages);
        }

        return languages;
    }
}
