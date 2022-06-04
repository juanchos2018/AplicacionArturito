package com.example.aplicacionarturito.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Adapter.AdapterHoras;
import com.example.aplicacionarturito.Adapter.AdapterResenas;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.Model.Resenas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterHorario {

    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterHoras adapter;

    public PresenterHorario(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
    }


    public  void cargarRecycler(RecyclerView recyclerView, String psicologo_id){
        databaseReference.child("Horas").child(psicologo_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Horas> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Horas model=item.getValue(Horas.class);
                    lista.add(model);
                }

                adapter= new AdapterHoras(lista);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
