package com.example.finnkinoapp.ui.Auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogInViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LogInViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Auth fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}