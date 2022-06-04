package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.aplicacionarturito.Presenter.PresenterCategoria;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoriaActivity extends AppCompatActivity {

    PresenterCategoria presenter;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterCategoria(this,reference);

        ListaPsicologos();
    }

    private  void  ListaPsicologos(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclercategorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,"");

    }


}