package com.zarkaoui.digitalsou9;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.material.snackbar.Snackbar;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zarkaoui.digitalsou9.classes.User;
import com.zarkaoui.digitalsou9.databinding.ActivityEditProfileBinding;

import java.util.HashMap;
import java.util.UUID;

public class EditProfileActivity extends DrawerBaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userId;
    private StorageReference storageReference;
    private Uri imageUri;

    private EditText fullNameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private ImageView editProfilePic;
    private Button updateProfileBtn;
    private ProgressBar progressBar;

    ActivityEditProfileBinding activityEditProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(activityEditProfileBinding.getRoot());
        activityTitle("Edit profile");

        user = mAuth.getInstance().getCurrentUser();
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

        editProfilePic.setOnClickListener(this);
        updateProfileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateProfileBtn:
                updateUserInfo();
                break;
            case R.id.editProfilePic:
                chooseProfilePic();
                break;
        }
    }

    private void chooseProfilePic() {
        //intent to open the gallery of the phone:
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            editProfilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Image Uploading....");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storageReference.child("images/" + randomKey);

        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress : " + (int) progressPercent + "%.");
                    }
                });
    }

//    //This is to choose a profile picture:
//    private void chooseProfilePic() {
////        Toast.makeText(this, "Photo profile clicked", Toast.LENGTH_LONG).show();
//        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        openGallery.setType("image/*");
//        startActivityForResult(openGallery, 1000);
//
//    }
//    //For that we need to override the onActivityResult :
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            if(resultCode == RESULT_OK){
//                Uri imageUri = data.getData();
//                uploadImage(imageUri);
//            }
//        }
//    }
//
////    This is for uploading the image to Firebase:
//    private void uploadImage(Uri imageUri) {
//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setTitle("Image Uploading....");
//        pd.show();
//
//        final String randomKey = UUID.randomUUID().toString();
//        StorageReference mountainsRef = storageReference.child("images/" + randomKey );
//
//        mountainsRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        pd.dismiss();
//                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                        pd.setMessage("Progress : " + (int) progressPercent + "%.");
//                    }
//                });
//    }

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

        User newUser = new User(newFullName, newPhoneNumber, newEmail, "https://static.wikia.nocookie.net/hunterxhunter/images/7/7c/Killua-2011.png/revision/latest?cb=20120115021804");

        databaseReference.child(userId).updateChildren(userUpdatedInfo).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                user.updateEmail(newEmail);
                Toast.makeText(EditProfileActivity.this, "Your have successfully updated your profile information", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong. Please try later!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}