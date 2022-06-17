package com.example.aplicacionarturito.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aplicacionarturito.Adapter.AdapterCategorias;
import com.example.aplicacionarturito.Model.Paciente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;

public class PresenterFamiliar {


    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterCategorias adapter;
    ProgressDialog progressDialog;

    public PresenterFamiliar(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
    }


    private  void save(Paciente paciente,String user_id){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String key =databaseReference.push().getKey();
        paciente.setId(key);
        databaseReference.child("UsuarioFamiliar").child(user_id).child(key).setValue(paciente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(mContext, "Registrado familiar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
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
