package com.example.aplicacionarturito.Presenter;

import android.app.AlertDialog;
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

import com.example.aplicacionarturito.Adapter.AdapterHoras;
import com.example.aplicacionarturito.Adapter.AdapterPsicologos;
import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.PsicoloPaciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert1;
    InterfacePsicologo interfacePsicologo2;
    ArrayList<Horas> lista;
    public PresenterPsicologo(Context mContext, DatabaseReference databaseReference,InterfacePsicologo interfacePsicologo2) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.interfacePsicologo2=interfacePsicologo2;
    }

    public  void cargarRecycler(RecyclerView recyclerView,String paciente_id){
        databaseReference.child("Psicologos").addValueEventListener(new ValueEventListener() {
            ArrayList<Psicologo> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Psicologo model=item.getValue(Psicologo.class);
                    lista.add(model);
                }
                adapter= new AdapterPsicologos(lista,paciente_id);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public  void cargarRecyclerHoras(RecyclerView recyclerView, String psicologo_id,String fecha_id,String paciente_id,String fecha,Paciente paciente){
        databaseReference.child("Fechas").child(psicologo_id).child(fecha_id).child("Horas").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Horas model=item.getValue(Horas.class);
                    lista.add(model);
                }
                adapter2= new AdapterHoras(lista);
                recyclerView.setAdapter(adapter2);
                adapter2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombres =paciente.getNombre()+" "+paciente.getApellidos();
                        String photo =paciente.getPhoto();
                        String horas =lista.get(recyclerView.getChildAdapterPosition(view)).getHora_inicio();
                        String horaifin =lista.get(recyclerView.getChildAdapterPosition(view)).getHora_fin();
                        DialogoAddCita("Quiere tener la consulta alas "+horas+" hasta las"+ horaifin,paciente_id,psicologo_id,fecha,nombres,photo);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void filtrar(String texto) {
        ArrayList<Horas> filtradatos= new ArrayList<>();
        for(Horas item :lista){
            if (item.getTurno().contains(texto)){
                filtradatos.add(item);
            }
            adapter2.filtrar(filtradatos);
        }
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
                    interfacePsicologo2.onCallbackFechas2(lista2,lista);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void  DialogoAddCita(String hora,String paciente_id,String psicologo_id,String fecha,String nombres,String photo){
        builder = new AlertDialog.Builder(mContext);
        Button btnsi,btnno;
        TextView tvhoras;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_add, null);
        btnsi=(Button)v.findViewById(R.id.btnsi);
        btnno=(Button)v.findViewById(R.id.btnno);
        tvhoras=(TextView)v.findViewById(R.id.tvhoras);
        tvhoras.setText(hora);
        builder.setView(v);
        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PsicoloPaciente  obj= new PsicoloPaciente();
                obj.setFecha(fecha);
                obj.setHora(hora);
                obj.setPsicologo_id(psicologo_id);
                obj.setPaciente_id(paciente_id);
                obj.setNombres(nombres);
                obj.setPhoto(photo);
                obj.setEstado("Nuevo");
                SaveCita(obj);
                alert.dismiss();
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
    private  void  SaveCita(PsicoloPaciente psicologoPaciente){
        String key =databaseReference.push().getKey();
        psicologoPaciente.setId(key);
        databaseReference.child("PsicologoPaciente").child(psicologoPaciente.getPsicologo_id()).child(key).setValue(psicologoPaciente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    //progressDialog.dismiss();
                    DialogOk("Registrado Cita");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
            }
        });
    }

    private void  DialogOk(String mensaje){
        builder1 = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_ok, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder1.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.dismiss();
            }
        });
        alert1  = builder1.create();
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.show();
    }
}
