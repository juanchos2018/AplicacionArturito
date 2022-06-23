package com.example.aplicacionarturito.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.AtencionesActivity;
import com.example.aplicacionarturito.Activity.LecturasActivity;
import com.example.aplicacionarturito.Adapter.AdapterCategorias;
import com.example.aplicacionarturito.Adapter.AdapterFamiliar;
import com.example.aplicacionarturito.Adapter.AdapterFamiliar2;
import com.example.aplicacionarturito.Adapter.AdapterHoras;
import com.example.aplicacionarturito.Interface.InterfacePaciente;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.Model.Paciente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterFamiliar {

    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterFamiliar adapter;
    private AdapterFamiliar2 adapter2;
    ProgressDialog progressDialog;
    String user_id;
    public PresenterFamiliar(Context mContext, DatabaseReference databaseReference,String user_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.user_id=user_id;
    }

    public   void save(Paciente paciente){
//        progressDialog= new ProgressDialog(mContext);
//        progressDialog.setMessage("Cargando..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        String key =databaseReference.push().getKey();
        paciente.setId(key);
        paciente.setPhoto("default");
        databaseReference.child("UsuarioFamiliar").child(user_id).child(key).setValue(paciente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(mContext, "Registrado familiar", Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                    savePaciente(paciente,key);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });
    }

    public void infoPaciente(InterfacePaciente interfacePaciente, String paciente_id) {

        databaseReference.child("Paciente").child(paciente_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Paciente userModel = snapshot.getValue(Paciente.class);
                    interfacePaciente.onCallback(userModel);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public  Paciente(InterfacePaciente interfacePaciente, String paciente_id) {
//
//        databaseReference.child("Paciente").child(paciente_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    Paciente userModel = snapshot.getValue(Paciente.class);
//                    interfacePaciente.onCallback(userModel);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
//                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
    private  void savePaciente(Paciente paciente,String id){
//        progressDialog= new ProgressDialog(mContext);
//        progressDialog.setMessage("Cargando..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        databaseReference.child("Paciente").child(id).setValue(paciente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    //progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void cargarRecycler(RecyclerView recyclerView){
        databaseReference.child("UsuarioFamiliar").child(user_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Paciente> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Paciente model=item.getValue(Paciente.class);
                    lista.add(model);
                }
                adapter= new AdapterFamiliar(lista,mContext);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public  void cargarRecycler2(RecyclerView recyclerView){
        databaseReference.child("UsuarioFamiliar").child(user_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Paciente> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Paciente model=item.getValue(Paciente.class);
                    lista.add(model);
                }
                adapter2= new AdapterFamiliar2(lista,mContext);
                recyclerView.setAdapter(adapter2);
                adapter2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String paciente_id =lista.get(recyclerView.getChildAdapterPosition(view)).getId();
                        mostrarDialogOpciones(paciente_id);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void mostrarDialogOpciones(String paciente_id) {
        final CharSequence[] opciones={"LECTURA","ATENCION","MEMORIA","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("LECTURA")){
                    Intent intent = new Intent(mContext, LecturasActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putString("paciente_id",paciente_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else if (opciones[i].equals("ATENCION")) {
                    Intent intent = new Intent(mContext, AtencionesActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putString("paciente_id",paciente_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
                else if (opciones[i].equals("MEMORIA")) {

                }
                else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

}
