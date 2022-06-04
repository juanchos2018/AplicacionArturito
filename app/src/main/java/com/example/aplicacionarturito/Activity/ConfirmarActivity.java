package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacionarturito.R;

public class ConfirmarActivity extends AppCompatActivity  implements View.OnClickListener {

    Button btnsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);


        btnsi=(Button) findViewById(R.id.btnsi);
        btnsi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.btnsi:
                Toast.makeText(this, "se confimo la hora gracias", Toast.LENGTH_SHORT).show();
                break;

        }



    }
}