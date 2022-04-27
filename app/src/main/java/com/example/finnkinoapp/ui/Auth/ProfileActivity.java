package com.example.finnkinoapp.ui.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finnkinoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.finnkinoapp.R.layout.activity_profile);

        logout = (Button) findViewById(R.id.logOutButton);

        // logout functionality on button press
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                // Close activity and go back to home fragment after logout
                ProfileActivity.this.finish();

                Toast.makeText(ProfileActivity.this, "\"You are now logged out.\"", Toast.LENGTH_LONG).show();

            }
        });
        // current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        // db reference
        reference = FirebaseDatabase.getInstance("https://finnkino-app-7cc1d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        // user id
        userID = user.getUid();

        final TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        final TextView userName = (TextView) findViewById(R.id.userName);
        final TextView userEmail = (TextView) findViewById(R.id.userEmail);
        final TextView userDob = (TextView) findViewById(R.id.userDob);

        // get user data
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String dob = userProfile.dob;
                    // set text fields
                    welcomeText.setText("Welcome, " + fullName + "!");
                    userName.setText(fullName);
                    userEmail.setText(email);
                    userDob.setText(dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "\"Something went wrong.\"", Toast.LENGTH_LONG).show();

            }
        });
    }
}