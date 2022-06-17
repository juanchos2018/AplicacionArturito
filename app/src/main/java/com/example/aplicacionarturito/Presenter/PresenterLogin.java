package com.example.aplicacionarturito.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aplicacionarturito.PrincipalActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.annotations.NotNull;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

public class PresenterLogin {


    private Context mContext;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;

    public PresenterLogin(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        mAuth = FirebaseAuth.getInstance();
       // user = mAuth.getCurrentUser();
    }

    public  void  registerUser(String nombre, String apellido, String correo, String clave){

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("cargando");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String current_userID =  mAuth.getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(current_userID);
                            databaseReference.child("nombre").setValue(nombre);
                            databaseReference.child("apellido").setValue(apellido);
                            databaseReference.child("correo").setValue(correo)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                // SENDING VERIFICATION EMAIL TO THE REGISTERED USER'S EMAIL
                                                user = mAuth.getCurrentUser();
                                                if (user != null){
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressDialog.dismiss();
                                                                    mAuth.signOut();
                                                                    Toast.makeText(mContext, "Registrado", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    mAuth.signOut();
                                                                    progressDialog.dismiss();
                                                                }
                                                            }
                                                        });
                                                }

                                            }
                                        }
                                    });

                        } else {
                            String message = task.getException().getMessage();
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Error occurred : " + message, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public  void  Register(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Categoria");
        String key =databaseReference.push().getKey();
        Map<String,Object> pariente= new HashMap<>();
        pariente.put("nombre","Categoria 1 ");
        pariente.put("photo","default");
        pariente.put("tipo","tipo");

        pariente.put("id",key);

        databaseReference.child(key).setValue(pariente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){

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

    public  void  login(String correo,String clave){

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("cargando");
        progressDialog.show();
        //Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    String userUID = mAuth.getCurrentUser().getUid();
                    //checkVerifiedEmail();
                    Intent intent = new Intent(mContext, PrincipalActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "verifique sus datos", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void checkVerifiedEmail() {
        user = mAuth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified){
            databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
            final String UID = mAuth.getCurrentUser().getUid();
            databaseReference.child(UID).child("verificado").setValue("true");
            Intent intent = new Intent(mContext, PrincipalActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);

        } else {
            Toast.makeText(mContext, "Correo no verificado", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }



}
