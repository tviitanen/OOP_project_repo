package com.example.finnkinoapp.ui.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finnkinoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextTextPersonName, editTextBirthDate, editTextTextPassword, editTextTextEmailAddress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // firebase auth
        mAuth = FirebaseAuth.getInstance();

        editTextBirthDate = (EditText) findViewById(R.id.editTextBirthDate);
        editTextTextPersonName = (EditText) findViewById(R.id.editTextPersonName);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextPassword);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = editTextTextEmailAddress.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();
        String fullName = editTextTextPersonName.getText().toString().trim();
        String dateOfBirth = editTextBirthDate.getText().toString().trim();

        // check that necessary info is given by user
        if (fullName.isEmpty()) {
            editTextTextPersonName.setError("Full name is required!");
            editTextTextPersonName.requestFocus();
            return;
        }
        if (dateOfBirth.isEmpty()) {
            editTextBirthDate.setError("Date of Birth is required!");
            editTextBirthDate.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextTextEmailAddress.setError("Email is required!");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        // check email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextTextEmailAddress.setError("Email is incorrect!");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextTextPassword.setError("Password is required!");
            editTextTextPassword.requestFocus();
            return;
        }
        // check lenght of the password  (firebase minimum lenght is 6)
        if (password.length() < 12) {
            editTextTextPassword.setError("Password too short. Atleast 6 characters required.");
            editTextTextPassword.requestFocus();
            return;
        }
        // regex to check that password contains letters and numbers
        if (!password.matches("^(?=.*[A-Ö])(?=.*[0-9])[A-Ö0-9]+$")) {
            editTextTextPassword.setError("Password too weak. Password must contain letters and numbers.");
            editTextTextPassword.requestFocus();
            return;
        }


        // registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if register succesfull, create user object
                        if (task.isSuccessful()) {
                            User user = new User(fullName, email, dateOfBirth);
                            FirebaseDatabase.getInstance("https://finnkino-app-7cc1d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // if data added to a db succesfully, make a toast notice
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "\"Registration completed successfully\"", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "\"An error occurred during registration, try again\"", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "\"An error occurred during registration, try again\"", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}