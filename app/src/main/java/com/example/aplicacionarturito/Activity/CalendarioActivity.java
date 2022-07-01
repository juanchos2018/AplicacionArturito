package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicacionarturito.Interface.InterfaceDialog;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity  implements View.OnClickListener,InterfacePsicologo, InterfaceDialog {

    Button btnverificarhorario;
    Paciente paciente;
    String psicolog_id,fecha,fecha_id,paciente_id;

    CustomCalendar customCalendar;
    PresenterPsicologo presenter;
    PresenterFamiliar presenterFamiliar;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String user_id;

    ArrayList<String> listaDia;
    ArrayAdapter<String> adapterDias;
    Spinner spinnerDias;

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
        presenterFamiliar=new PresenterFamiliar(this,reference,user_id,this);

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

        customCalendar=(CustomCalendar)findViewById(R.id.calendar);
        spinnerDias=(Spinner)findViewById(R.id.spinnerDias);
        //btnverificarhorario.setOnClickListener(this);

        listaDia=new ArrayList<>();
        listaDia.add("Seleccione");
        listaDia.add("mañana");
        listaDia.add("tarde");

        adapterDias= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaDia);
        spinnerDias.setAdapter(adapterDias);

        spinnerDias.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        int  pos=spinnerDias.getSelectedItemPosition();
                        String turno =listaDia.get(pos);
                        if (!turno.equals("Seleccione")){
                            presenter.filtrar(turno);
                        }
                       // Toast.makeText(CalendarioActivity.this,  turno, Toast.LENGTH_SHORT).show();
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });


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
                        //Toast.makeText(CalendarioActivity.this, fecha_id+" ", Toast.LENGTH_SHORT).show();
                    }else{
                        Horas(fecha);
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

    @Override
    public void oncallbackPaciente(String paciente_id) {

    }

    @Override
    public Context getContext2() {
        return null;
    }
}