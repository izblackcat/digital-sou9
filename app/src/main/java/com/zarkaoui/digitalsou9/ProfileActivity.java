package com.zarkaoui.digitalsou9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.zarkaoui.digitalsou9.databinding.ActivityProfileBinding;

import java.nio.charset.StandardCharsets;

public class ProfileActivity extends DrawerBaseActivity {
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userId;


    private Button editProfileBtn;
    private Button signOutBtn;

    ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        activityTitle("Profile");

        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
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
        final TextView fullName = (TextView) findViewById(R.id.fullName_textView);
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
                    Log.d("PHOTO", "onDataChange: " + userPhoto);

//                    greeting.setText("Hello, " + userFullName + "!");
                    fullName.setText(userFullName);
                    phoneNumber.setText(userPhone);
                    email.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong. Try again later!", Toast.LENGTH_LONG).show();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("fullName", fullName.getText().toString());
                intent.putExtra("phoneNumber", phoneNumber.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            }
        });
    }
}