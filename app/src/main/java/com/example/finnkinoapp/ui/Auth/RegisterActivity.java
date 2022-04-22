package com.example.finnkinoapp.ui.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finnkinoapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }
}