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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.AtencionActivity;
import com.example.aplicacionarturito.Activity.LecturaActivity;
import com.example.aplicacionarturito.Adapter.AdapterFiguras;
import com.example.aplicacionarturito.Adapter.AdapterFiguras2;
import com.example.aplicacionarturito.Adapter.AdapterLectura;
import com.example.aplicacionarturito.Interface.InterfaceAtencion;
import com.example.aplicacionarturito.Interface.InterfaceFigura;
import com.example.aplicacionarturito.Interface.InterfaceLectura;
import com.example.aplicacionarturito.Model.Atencion;
import com.example.aplicacionarturito.Model.Figura;
import com.example.aplicacionarturito.Model.Lectura;
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

public class PresenterAtencion {

    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterFiguras adapter;
    private AdapterFiguras2 adapter2;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String paciente_id;

    private InterfaceFigura interfaceFigura;


    public PresenterAtencion(Context mContext, DatabaseReference databaseReference,String paciente_id,InterfaceFigura interfaceFigura) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.paciente_id=paciente_id;
        this.interfaceFigura=interfaceFigura;
    }


    public  void cargarRecycler(RecyclerView recyclerView, String CategoriaId){
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Atencion> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Atencion model=item.getValue(Atencion.class);
                    lista.add(model);
                }
                adapter= new AdapterFiguras(lista,mContext);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id =lista.get(recyclerView.getChildAdapterPosition(v)).getId();
                        String figurabuscada =lista.get(recyclerView.getChildAdapterPosition(v)).getFigura();
                        Intent intent = new Intent(mContext, AtencionActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("paciente_id",paciente_id);
                        bundle.putString("figurabuscada",figurabuscada);
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

    public  void cargarRecycler2(RecyclerView recyclerView, ArrayList<Figura> lista){

        adapter2= new AdapterFiguras2(lista,mContext);
        recyclerView.setAdapter(adapter2);
        adapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String figura =lista.get(recyclerView.getChildAdapterPosition(v)).getFigura();
                interfaceFigura.onCallback(figura);
            }
        });
    }

    public void ViewAtencion(InterfaceAtencion interfaceCita, String CategoriaId, String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Atencion obj = snapshot.getValue(Atencion.class);
                    interfaceCita.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void  DialogOk(String mensaje,Atencion atencion){
        builder = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_ok, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder.setView(v);
        btcerrrar.setText("Guardar");
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update(atencion);
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    public   void  update(Atencion atencion){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String,Object> obj= new HashMap<>();
        obj.put("categoriaId",atencion.getCategoriaId());
        obj.put("id",atencion.getId());
        obj.put("cantidadclick",atencion.getCantidadclick());
        obj.put("cantidafiguras",atencion.getCantidafiguras());
        obj.put("cantidad",atencion.getCantidad());
        obj.put("estado","resuelto");

        databaseReference.child("TallerPaciente").child(paciente_id).child(atencion.getCategoriaId()).child(atencion.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                  //  DialogOk("Guardo con Exito");
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
}
