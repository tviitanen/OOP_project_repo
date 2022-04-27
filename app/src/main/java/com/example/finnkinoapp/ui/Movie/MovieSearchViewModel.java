package com.example.finnkinoapp.ui.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieSearchViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MovieSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}