package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.aplicacionarturito.Presenter.PresenterHorario;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HorarioActivity extends AppCompatActivity {


    ///recyclerhoras

    PresenterHorario presenter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);



        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterHorario(this,reference);

        Lista();

    }


    private  void  Lista(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerhoras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String psilocogo_id="-N3iBH2LWijQlld_nCo3";
        presenter.cargarRecycler(recyclerView,psilocogo_id);

    }
}