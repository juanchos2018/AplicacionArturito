package com.example.aplicacionarturito.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.LecturaActivity;
import com.example.aplicacionarturito.Activity.MemoriaActivity;
import com.example.aplicacionarturito.Adapter.AdapterGrilla;
import com.example.aplicacionarturito.Adapter.AdapterLectura;
import com.example.aplicacionarturito.Adapter.AdapterMemoria;
import com.example.aplicacionarturito.Interface.InterfaceLectura;
import com.example.aplicacionarturito.Interface.InterfaceMemoria;
import com.example.aplicacionarturito.Model.Images;
import com.example.aplicacionarturito.Model.Lectura;
import com.example.aplicacionarturito.Model.Memoria;
import com.example.aplicacionarturito.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresenterMemoria {


    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterMemoria adapter;
    private AdapterGrilla adapter2;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String paciente_id;


    public PresenterMemoria(Context mContext, DatabaseReference databaseReference,String paciente_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.paciente_id=paciente_id;
    }


    public void cargarRecycler(RecyclerView recyclerView, String CategoriaId){
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Memoria> lista;

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Memoria model=item.getValue(Memoria.class);
                    lista.add(model);
                }
                adapter= new AdapterMemoria(lista,mContext);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id =lista.get(recyclerView.getChildAdapterPosition(v)).getId();
                        Intent intent = new Intent(mContext, MemoriaActivity.class);
                        Bundle bundle = new Bundle();
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

    public void cargarGrilla(GridView gridView){
        databaseReference.child("Figuras").addValueEventListener(new ValueEventListener() {
            ArrayList<Images> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Images model=item.getValue(Images.class);
                    lista.add(model);
                }
                adapter2= new AdapterGrilla(mContext,lista);
                gridView.setAdapter(adapter2);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }
    public void ViewMemoria(InterfaceMemoria interfaceCita, String CategoriaId, String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Memoria obj = snapshot.getValue(Memoria.class);
                    interfaceCita.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public   void  update(Memoria memoria){

        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Map<String,Object> obj= new HashMap<>();

        obj.put("categoriaId",memoria.getCategoriaId());
        obj.put("id",memoria.getId());
        obj.put("estado","resuelto");
        obj.put("audio",memoria.getAudio());

        databaseReference.child("TallerPaciente").child(paciente_id).child(memoria.getCategoriaId()).child(memoria.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    DialogOk("Guardo con Exito");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void  DialogOk(String mensaje){
        builder = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_ok, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

}
