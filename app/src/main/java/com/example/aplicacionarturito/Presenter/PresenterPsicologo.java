package com.example.aplicacionarturito.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Adapter.AdapterHoras;
import com.example.aplicacionarturito.Adapter.AdapterPsicologos;
import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;

import java.util.ArrayList;
import java.util.Collections;

public class PresenterPsicologo {

    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterPsicologos adapter;
    private AdapterHoras adapter2;

    InterfacePsicologo interfacePsicologo2;

    public PresenterPsicologo(Context mContext, DatabaseReference databaseReference,InterfacePsicologo interfacePsicologo2) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.interfacePsicologo2=interfacePsicologo2;
    }


    public  void cargarRecycler(RecyclerView recyclerView, Paciente paciente){
        databaseReference.child("Psicologos").addValueEventListener(new ValueEventListener() {
            ArrayList<Psicologo> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Psicologo model=item.getValue(Psicologo.class);
                    lista.add(model);
                }
                adapter= new AdapterPsicologos(lista,paciente);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public  void cargarRecyclerHoras(RecyclerView recyclerView, String psicologo_id,String fecha_id){
        databaseReference.child("Fechas").child(psicologo_id).child(fecha_id).child("Horas").addValueEventListener(new ValueEventListener() {
            ArrayList<Horas> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Horas model=item.getValue(Horas.class);
                    lista.add(model);
                }
                adapter2= new AdapterHoras(lista);
                recyclerView.setAdapter(adapter2);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void info(String user_id) {

        databaseReference.child("Psicologos").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Psicologo userModel = snapshot.getValue(Psicologo.class);
                    interfacePsicologo2.onCallback(userModel);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void  ViewFechas(String psicolog_id){

        databaseReference.child("Fechas").child(psicolog_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Fecha> lista;
            ArrayList<Integer> lista2;
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    lista=new ArrayList<>();
                    lista2=new ArrayList<>();
                    for (DataSnapshot item:snapshot.getChildren()){
                        Fecha model=item.getValue(Fecha.class);
                        String []fec=model.getFecha().split("-");
                        int fe=Integer.parseInt(fec[0]);
                        lista.add(model);
                        lista2.add(fe);
                    }
                    //interfacePsicologo.onCallbackFechas(lista);
                    interfacePsicologo2.onCallbackFechas2(lista2,lista);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
