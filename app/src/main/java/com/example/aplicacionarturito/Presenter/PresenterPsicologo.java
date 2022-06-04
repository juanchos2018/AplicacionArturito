package com.example.aplicacionarturito.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Adapter.AdapterPsicologos;
import com.example.aplicacionarturito.Model.Psicologo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class PresenterPsicologo {

    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterPsicologos adapter;

    public PresenterPsicologo(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
    }


    public  void cargarRecycler(RecyclerView recyclerView){
        databaseReference.child("Psicologos").addValueEventListener(new ValueEventListener() {
            ArrayList<Psicologo> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Psicologo model=item.getValue(Psicologo.class);
                    lista.add(model);
                }

                adapter= new AdapterPsicologos(lista);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        String keycita=lista.get(recyclerView.getChildAdapterPosition(v)).getKey();
//                        Intent intent=new Intent(mContext, VistaDetalleCita.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("keyfamiliar",keyfam);
//                        bundle.putString("keycita",keycita);
//                        intent.putExtras(bundle);
//                        mContext.startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}
