package com.example.aplicacionarturito.Presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Adapter.AdapterCategorias;
import com.example.aplicacionarturito.Adapter.AdapterHoras;
import com.example.aplicacionarturito.Model.Categoria;
import com.example.aplicacionarturito.Model.Horas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterCategoria {


    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterCategorias adapter;

    public PresenterCategoria(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
    }


    public  void cargarRecycler(RecyclerView recyclerView, String psicologo_id){
        databaseReference.child("Categoria").addValueEventListener(new ValueEventListener() {
            ArrayList<Categoria> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Categoria model=item.getValue(Categoria.class);
                    lista.add(model);
                }

                adapter= new AdapterCategorias(lista);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "errror", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
