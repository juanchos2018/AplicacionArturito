package com.example.aplicacionarturito.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionarturito.Interface.InterfaceFigura;
import com.example.aplicacionarturito.Model.Atencion;
import com.example.aplicacionarturito.Model.Figura;
import com.example.aplicacionarturito.Presenter.PresenterAtencion;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AtencionActivity extends AppCompatActivity  implements View.OnClickListener, InterfaceFigura {


    private StaggeredGridLayoutManager layoutManager;

    RecyclerView recyclerfiguras;
    String id,paciente_id;

    private DatabaseReference reference;
    PresenterAtencion presenter;
    ImageView figura;

    String CategoriaId="-N3iTztFqDubpdA0kT3h";
    Button btnguardar;
    TextView tcantidad;

    String figurabuscada;
    int can=0;
    int clicks=1;
    int clickscorrectos=1;
    int cantidafiguras =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);


        inpust();
        id=getIntent().getStringExtra("id");
        figurabuscada=getIntent().getStringExtra("figurabuscada");
        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterAtencion(this,reference,paciente_id,this);

    }


    private void inpust() {
        figura=(ImageView) findViewById(R.id.figura);
        //btnguardar=(Button) findViewById(R.id.btnguardar);
//        tcantidad=(TextView) findViewById(R.id.tcantidad);

        //btnguardar.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        if (id!=""){
            viewAtencion(id);
            records();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void records() {
        ArrayList<Figura> listViewItems = new ArrayList<Figura>();
        String figuras [] = {"nova","cuadrado","circulo","rombo","rectangulo","triangulo"};
        for (int i =0;i<30;i++){
            int position = (int) Math.floor(Math.random()*5+1);
            String figura =figuras[position];
            listViewItems.add(new Figura(String.valueOf(i), figura,figura));
        }
        listViewItems.forEach(item->{
            if (item.getType().equals(figurabuscada)) {
                cantidafiguras=cantidafiguras+1;

            }
        });

        recyclerfiguras=(RecyclerView)findViewById(R.id.recyclerfiguras);
        layoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager2 = new  LinearLayoutManager(this);
        recyclerfiguras.setLayoutManager(layoutManager);
        presenter.cargarRecycler2(recyclerfiguras,listViewItems);
    }

    private void viewAtencion(String id) {
        presenter.ViewAtencion(value -> {
            if (value.getFigura().equals("triangulo")){
                figura.setImageResource(R.drawable.trianguloapp);
             //   figurabuscada="triangulo";
            }
            else if (value.getFigura().equals("rectangulo")){
                figura.setImageResource(R.drawable.rectaguloapp);
             //   figurabuscada="rectangulo";
            }else if (value.getFigura().equals("circulo")){
                figura.setImageResource(R.drawable.circuloapp);
             //   figurabuscada="circulo";
            }else if (value.getFigura().equals("rombo")) {
                figura.setImageResource(R.drawable.romboapp);
              //  figurabuscada="rombo";
            }
            else if (value.getFigura().equals("cuadrado")) {
                figura.setImageResource(R.drawable.cuadradoapp);
             //   figurabuscada="cuadrado";
            }

        }, CategoriaId,id);
    }



    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case  R.id.btnguardar:

                    break;
            }
    }

    @Override
    public void onCallback(String figura) {
        clicks=clicks+1;
        if (figura.equals(figurabuscada)){
            clickscorrectos=clickscorrectos+1;
            if (clickscorrectos==cantidafiguras){
                messgaeSucces();
            }
        }

    }

    private void messgaeSucces() {
        Atencion atencion = new Atencion();
        atencion.setId(id);
        atencion.setEstado("completado");
        atencion.setCantidad("30");
        atencion.setCantidadclick(clicks+"");
        atencion.setCategoriaId(CategoriaId);
        atencion.setCantidafiguras(cantidafiguras+"");
        presenter.DialogOk("Se completo esta tarea",atencion);
        finish();
        //Toast.makeText(this, "Compleado todos los fuguras", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}

