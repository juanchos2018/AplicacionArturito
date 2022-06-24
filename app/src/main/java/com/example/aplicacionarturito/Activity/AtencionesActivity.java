package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.aplicacionarturito.Interface.InterfaceFigura;
import com.example.aplicacionarturito.Presenter.PresenterAtencion;
import com.example.aplicacionarturito.Presenter.PresenterLectura;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AtencionesActivity extends AppCompatActivity implements InterfaceFigura {

    PresenterAtencion presenter;
    private DatabaseReference reference;
    String CategoriaId="-N3iTztFqDubpdA0kT3h";
    String paciente_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atenciones);


        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterAtencion(this,reference,paciente_id,this);



    }


    @Override
    protected void onStart() {
        super.onStart();
        records();
    }


    private  void  records(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyleratenciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,CategoriaId);
    }

    @Override
    public void onCallback(String figura) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}