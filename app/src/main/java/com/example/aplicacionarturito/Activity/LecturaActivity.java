package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionarturito.Presenter.PresenterLectura;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturaActivity extends AppCompatActivity implements View.OnClickListener  {


    PresenterLectura presenter;
    private DatabaseReference reference;
    String CategoriaId= "-N3iTyAojHIFohK3ft4U";
    String id,paciente_id;

    LinearLayout layoutlectura,layoutcuestionario;
    RadioButton search,offer;
    TextView etpregunta1,etpregunta2,etpregunta3,etLectura,ettitulo;
    EditText tvrespuesta1,tvrespuesta2,tvrespuesta3;
    Button btnguardar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        inputs();
        id=getIntent().getStringExtra("id");
        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterLectura(this,reference,paciente_id);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (id!=""){
            viewLectura(id);
        }
    }


    private void viewLectura(String id) {
        presenter.ViewLectura(value -> {
            ettitulo.setText(value.getTitulo());
            etLectura.setText(value.getLectura());
            etpregunta1.setText(value.getPregunta1());
            etpregunta2.setText(value.getPregunta2());
            etpregunta3.setText(value.getPregunta3());

        }, CategoriaId,id);
    }

    private void inputs() {

        layoutlectura=(LinearLayout)findViewById(R.id.layoutlectura);
        layoutcuestionario=(LinearLayout)findViewById(R.id.layoutcuestionario);

        etpregunta1=(TextView) findViewById(R.id.pregunta1);
        etpregunta2=(TextView) findViewById(R.id.pregunta2);
        etpregunta3=(TextView) findViewById(R.id.pregunta3);


        tvrespuesta1=(EditText) findViewById(R.id.tvrespuesta1);
        tvrespuesta2=(EditText) findViewById(R.id.tvrespuesta2);
        tvrespuesta3=(EditText) findViewById(R.id.tvrespuesta3);


        etLectura=(TextView) findViewById(R.id.etLectura);
        ettitulo =(TextView) findViewById(R.id.ettitulo);

        btnguardar=(Button) findViewById(R.id.btnguardar);

        search=(RadioButton)findViewById(R.id.search);
        offer=(RadioButton)findViewById(R.id.offer);

        search.setChecked(true);
        btnguardar.setOnClickListener(this);
        search.setOnClickListener(this);
        offer.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.search:
                layoutlectura.setVisibility(View.VISIBLE);
                layoutcuestionario.setVisibility(View.GONE);
                break;

            case  R.id.offer:
                layoutlectura.setVisibility(View.GONE);
                layoutcuestionario.setVisibility(View.VISIBLE);
                break;

            case  R.id.btnguardar:

                String title =ettitulo.getText().toString();
                String lectura=etLectura.getText().toString();
                String preunga1=etpregunta1.getText().toString();
                String preunga2=etpregunta2.getText().toString();
                String preunga3=etpregunta3.getText().toString();

                break;

        }
    }
}