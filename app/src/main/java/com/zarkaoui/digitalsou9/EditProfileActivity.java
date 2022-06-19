package com.zarkaoui.digitalsou9;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;
import com.zarkaoui.digitalsou9.classes.User;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userId;

    private EditText fullNameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private ImageView editProfilePic;
    private Button updateProfileBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        fullNameEditText = findViewById(R.id.fullName_editText);
        phoneEditText = findViewById(R.id.phone_editText);
        emailEditText = findViewById(R.id.email_editText);
        editProfilePic = findViewById(R.id.editProfilePic);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
        String phone = intent.getStringExtra("phoneNumber");
        String email = intent.getStringExtra("email");

        Log.d("TAG", "onCreate: " + fullName + " phone: " + phone + " email: " + email);

        fullNameEditText.setText(fullName);
        phoneEditText.setText(phone);
        emailEditText.setText(email);

        updateProfileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateProfileBtn:
                updateUserInfo();
                break;
        }
    }

    private void chooseProfilePic() {
    }

    private void updateUserInfo() {
        String newFullName = fullNameEditText.getText().toString();
        String newPhoneNumber = phoneEditText.getText().toString();
        String newEmail = emailEditText.getText().toString();

        Log.d("UPDATE", "newName: " + newFullName + " newPhone: " + newPhoneNumber + " newEmail : " + newEmail);

        if(newFullName.isEmpty()){
            fullNameEditText.setError("Full name is required!");
            fullNameEditText.requestFocus();
            return;
        }

        if(newPhoneNumber.isEmpty()){
            phoneEditText.setError("Phone Number is required!");
            phoneEditText.requestFocus();
            return;
        }

        if(newEmail.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            emailEditText.setError("Please provide a valid email");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        HashMap userUpdatedInfo = new HashMap();
        userUpdatedInfo.put("fullName", newFullName);
        userUpdatedInfo.put("phoneNumber", newPhoneNumber);
        userUpdatedInfo.put("email", newEmail);

        User newUser = new User(newFullName, newPhoneNumber, newEmail, "");

        databaseReference.child(userId).updateChildren(userUpdatedInfo).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(EditProfileActivity.this, "Success!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Failure!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}