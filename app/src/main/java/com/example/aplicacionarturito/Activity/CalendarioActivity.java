package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplicacionarturito.R;

public class CalendarioActivity extends AppCompatActivity  implements View.OnClickListener {

    Button btnverificarhorario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        btnverificarhorario=(Button)findViewById(R.id.btnverificarhorario);
        btnverificarhorario.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.btnverificarhorario:
                startActivity(new Intent(this,HorarioActivity.class));
                break;

        }


    }
}