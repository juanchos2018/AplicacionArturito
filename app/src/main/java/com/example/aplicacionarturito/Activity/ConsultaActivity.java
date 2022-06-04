package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacionarturito.R;

public class ConsultaActivity extends AppCompatActivity  implements View.OnClickListener {


    CardView cardinstrucciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);


        cardinstrucciones=(CardView) findViewById(R.id.cardinstrucciones);
        cardinstrucciones.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardinstrucciones:
              startActivity(new Intent(this,InstruccionesActivity.class));
                break;

        }
    }
}