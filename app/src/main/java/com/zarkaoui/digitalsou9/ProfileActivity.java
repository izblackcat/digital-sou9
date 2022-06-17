package com.zarkaoui.digitalsou9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zarkaoui.digitalsou9.classes.User;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userId;

    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        signOutBtn = (Button) findViewById(R.id.signOutBtn);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

//        final TextView greeting = (TextView) findViewById(R.id.greeting);
        final TextView fullName = (TextView) findViewById(R.id.fullName_textview);
        final TextView phoneNumber = (TextView) findViewById(R.id.phone_textView);
        final TextView email = (TextView) findViewById(R.id.email_textView);
        final ImageView profilePic = findViewById(R.id.user_imageView);

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String userFullName = userProfile.getFullName();
                    String userEmail = userProfile.getEmail();
                    String userPhone = userProfile.getPhoneNumber();
                    String userPhoto = userProfile.getProfilePicture();


//                    greeting.setText("Hello, " + userFullName + "!");
                    fullName.setText(userFullName);
                    phoneNumber.setText(userPhone);
                    email.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong. Try agian later!", Toast.LENGTH_LONG).show();
            }
        });
    }
}