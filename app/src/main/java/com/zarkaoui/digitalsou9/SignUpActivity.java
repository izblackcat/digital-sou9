package com.zarkaoui.digitalsou9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zarkaoui.digitalsou9.classes.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView banner;
    private TextView registerUser;
    private EditText fullNameRegister;
    private EditText phoneNumber;
    private EditText emailRegister;
    private EditText passwordRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);

        fullNameRegister = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        registerUser = (Button) findViewById(R.id.registerButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Setting on clickListeners :
        banner.setOnClickListener(this);

        registerUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    public void registerUser(){
        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String fullName = fullNameRegister.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();

        if(fullName.isEmpty()){
            fullNameRegister.setError("Full name is required!");
            fullNameRegister.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            phoneNumber.setError("Age is required!");
            phoneNumber.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailRegister.setError("Email is required!");
            emailRegister.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailRegister.setError("Please provide a valid email");
            emailRegister.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordRegister.setError("Password is required!");
            passwordRegister.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordRegister.setError("Minimum password length should be 8 characters!");
            passwordRegister.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, phone, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}