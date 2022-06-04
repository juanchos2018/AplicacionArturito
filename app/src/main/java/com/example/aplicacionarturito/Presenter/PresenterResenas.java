package com.example.aplicacionarturito.Presenter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Adapter.AdapterPsicologos;
import com.example.aplicacionarturito.Adapter.AdapterResenas;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Model.Resenas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterResenas {


    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterResenas adapter;

    public PresenterResenas(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
    }

    public  void cargarRecycler(RecyclerView recyclerView,String psicologo_id){
        databaseReference.child("Resenas").child(psicologo_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Resenas> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Resenas model=item.getValue(Resenas.class);
                    lista.add(model);
                }

                adapter= new AdapterResenas(lista);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
