package com.example.aplicacionarturito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentUser == null ){
                            mAuth.signOut();
                            logOutUser();
                        }else{
                            startActivity(new Intent(SplashActivity.this,PrincipalActivity.class));
                        }
                    }
          }, 1500);

    }
    private void logOutUser() {

        Intent loginIntent =  new Intent(SplashActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}