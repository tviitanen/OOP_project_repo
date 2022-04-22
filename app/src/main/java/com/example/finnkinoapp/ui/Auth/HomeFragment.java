package com.example.finnkinoapp.ui.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finnkinoapp.MainActivity;
import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements  View.OnClickListener {

    private FragmentHomeBinding binding;
    private TextView register;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button logIn;
    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Login button
        logIn = binding.loginButton;
        logIn.setOnClickListener(this);

        // user inputs email & password
        editTextEmail = binding.editTextTextEmailAddress;
        editTextPassword = binding.editTextTextPassword;

        // register button
        register = binding.registerUser;
        register.setOnClickListener(this);

        // final TextView textView = binding.textHome;
        // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    // clicking register text opens new activity for register view
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(getActivity().getBaseContext(), RegisterActivity.class));
                break;
            case R.id.login_button:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        // check email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is incorrect!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        // check lenght of the password  (firebase minimum lenght is 6)
        if (password.length() < 6) {
            editTextPassword.setError("Password too short. Atleast 6 characters required.");
            editTextPassword.requestFocus();
            return;
        }

        // firebase authorization
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if data added to a db succesfully, make a toast notice
                if (task.isSuccessful()) {
                    // redirect to user profile
                    Toast.makeText(getActivity(), "\"Login completed successfully!\"", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
                } else {
                Toast.makeText(getActivity(), "\"An error occurred during login, check your credentials!\"", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}