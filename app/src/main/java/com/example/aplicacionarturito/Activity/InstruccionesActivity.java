package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplicacionarturito.R;

public class InstruccionesActivity extends AppCompatActivity  implements View.OnClickListener {


    Button btnsiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);


        btnsiguiente=(Button) findViewById(R.id.btnsiguiente);
        btnsiguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsiguiente:
                startActivity(new Intent(this,CategoriaActivity.class));
                break;

        }
    }
}