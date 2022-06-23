package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacionarturito.Interface.InterfacePaciente;
import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Presenter.PresenterFamiliar;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity  implements View.OnClickListener,InterfacePsicologo {

    Button btnverificarhorario;
    Paciente paciente;
    String psicolog_id,fecha,fecha_id,paciente_id;

    CustomCalendar customCalendar;
    PresenterPsicologo presenter;
    PresenterFamiliar presenterFamiliar;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        inputs();

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        psicolog_id=getIntent().getStringExtra("id");
        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter=new PresenterPsicologo(this,reference,this);
        presenterFamiliar=new PresenterFamiliar(this,reference,user_id);
        //Log.e("modelo", paciente.getNombre());
        Log.e("id", psicolog_id);
        Log.e("paciente_id", paciente_id);

        viewHoras();
        infoPaciente();


    }

    private void infoPaciente() {

        presenterFamiliar.infoPaciente(new InterfacePaciente() {
            @Override
            public void onCallback(Paciente value) {
                paciente=value;
                Log.e("pacientenomnre",paciente.getNombre());
            }
        },  paciente_id);
    }

    private void viewHoras() {
        presenter.ViewFechas(psicolog_id);
    }

    private void inputs() {

       // btnverificarhorario=(Button)findViewById(R.id.btnverificarhorario);
        customCalendar=(CustomCalendar)findViewById(R.id.calendar);
        //btnverificarhorario.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
//            case R.id.btnverificarhorario:
//                startActivity(new Intent(this,HorarioActivity.class));
//                break;
        }
    }

    @Override
    public void onCallback(Psicologo value) {

    }

    @Override
    public void onCallbackFechas(List<Fecha> fechas) {

    }

    @Override
    public void onCallbackFechas2(List<Integer> fechas,List<Fecha> fechas2) {
        HashMap<Object, Property> lista = new HashMap<>();
//
        Property defaultproperty = new Property();
        defaultproperty.layoutResource=R.layout.default_calentar;
        defaultproperty.dateTextViewResource=R.id.tex_view;
        lista.put("default",defaultproperty);


        Property currentProperty= new Property();
        currentProperty.layoutResource=R.layout.curre_view;
        currentProperty.dateTextViewResource=R.id.tex_view;
        lista.put("current",currentProperty);

        Property absentProperty= new Property();
        absentProperty.layoutResource=R.layout.absent_view;
        absentProperty.dateTextViewResource=R.id.tex_view;
        lista.put("absent",absentProperty);

        customCalendar.setMapDescToProp(lista);

        HashMap<Integer,Object>dates= new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        dates.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        for (int i=0;i<fechas.size();i++){
            dates.put(fechas.get(i),"absent");
        }
        customCalendar.setDate(calendar,dates);

        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                int position=0;
                int dia =selectedDate.get(Calendar.DAY_OF_MONTH);
                boolean existe=false;
                    fecha=selectedDate.get(Calendar.DAY_OF_MONTH)+"-"+(selectedDate.get(Calendar.MONTH)+1)+"-2022";
                    for (int e =0;e<fechas.size();e++){
                        if (dia==fechas.get(e)){
                            position=e;
                            existe=true;
                            break;
                        }
                    }
                    if (existe){
                        fecha_id =fechas2.get(position).getId();
                        Horas(fecha);
                        Toast.makeText(CalendarioActivity.this, fecha_id+" ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CalendarioActivity.this, "no tiene hora", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private  void Horas(String fecha){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recylcerHoras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecyclerHoras(recyclerView,psicolog_id,fecha_id,paciente_id,fecha,paciente);
    }
    @Override
    public Context getContext() {
        return this;
    }
}