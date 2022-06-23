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
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText emailLogin;
    private EditText passwordLogin;
    private Button signIn;
    private TextView register;
    private TextView forgotPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.loginBtn);
        signIn.setOnClickListener(this);

        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if(email.isEmpty()){
            emailLogin.setError("Email is required!");
            emailLogin.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Please provide a valid email");
            emailLogin.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordLogin.setError("Password is required!");
            passwordLogin.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordLogin.setError("Minimum password length should be 8 characters!");
            passwordLogin.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to categories activity:
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(SignInActivity.this, "Check your email inbox for verification link", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(SignInActivity.this, "Failed to login. Check your credentials and try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}