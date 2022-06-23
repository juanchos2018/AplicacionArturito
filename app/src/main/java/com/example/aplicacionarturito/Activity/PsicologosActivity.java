package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PsicologosActivity extends AppCompatActivity implements InterfacePsicologo {



    PresenterPsicologo presenter;

    private DatabaseReference reference;


    String paciente_id;
    Paciente paciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psicologos);

        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterPsicologo(this,reference,this);
        //paciente= (Paciente) getIntent().getSerializableExtra("paciente");
        paciente_id= getIntent().getStringExtra("id");

        ListaPsicologos(paciente_id);
    }

    private  void  ListaPsicologos( String paciente_id){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerpsicologos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,paciente_id);

    }

    @Override
    public void onCallback(Psicologo value) {

    }

    @Override
    public void onCallbackFechas(List<Fecha> fechas) {

    }

    @Override
    public void onCallbackFechas2(List<Integer> fechas,List<Fecha> fechas2) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}