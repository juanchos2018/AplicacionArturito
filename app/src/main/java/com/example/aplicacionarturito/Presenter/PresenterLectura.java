package com.example.aplicacionarturito.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.LecturaActivity;
import com.example.aplicacionarturito.Adapter.AdapterLectura;
import com.example.aplicacionarturito.Interface.InterfaceLectura;
import com.example.aplicacionarturito.Model.Lectura;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterLectura {


    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterLectura adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String paciente_id;

    public PresenterLectura(Context mContext, DatabaseReference databaseReference,String paciente_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.paciente_id=paciente_id;
    }

    public  void cargarRecycler(RecyclerView recyclerView, String CategoriaId){
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Lectura> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Lectura model=item.getValue(Lectura.class);
                    lista.add(model);
                }
                adapter= new AdapterLectura(lista,mContext);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id =lista.get(recyclerView.getChildAdapterPosition(v)).getId();
                        Intent intent = new Intent(mContext, LecturaActivity.class);
                        Bundle  bundle = new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("paciente_id",paciente_id);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void ViewLectura(InterfaceLectura interfaceCita, String CategoriaId,String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Lectura obj = snapshot.getValue(Lectura.class);
                    interfaceCita.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
