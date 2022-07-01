package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.aplicacionarturito.Presenter.PresenterLectura;
import com.example.aplicacionarturito.Presenter.PresenterMemoria;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemoriasActivity extends AppCompatActivity {

    PresenterMemoria presenter;
    private DatabaseReference reference;

    String CategoriaId="-N3iU-GjNXmOnKIUvK5F";

    String paciente_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorias);

        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterMemoria(this,reference,paciente_id);

    }


    @Override
    protected void onStart() {
        super.onStart();
        records();
    }


    private  void  records(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recylerMemorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,CategoriaId);
    }
}