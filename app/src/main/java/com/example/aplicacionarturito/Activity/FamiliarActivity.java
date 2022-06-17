package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.aplicacionarturito.R;

public class FamiliarActivity extends AppCompatActivity  implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiar);

        inputs();

    }




    @Override
    protected void onStart() {
        super.onStart();


    }

    private void inputs() {



    }

    @Override
    public void onClick(View view) {

    }
}