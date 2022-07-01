package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionarturito.Fragment.BottonSheetFragment;
import com.example.aplicacionarturito.Presenter.PresenterLectura;
import com.example.aplicacionarturito.Presenter.PresenterMemoria;
import com.example.aplicacionarturito.PrincipalActivity;
import com.example.aplicacionarturito.R;
import com.example.aplicacionarturito.SplashActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemoriaActivity extends AppCompatActivity implements View.OnClickListener {


    PresenterMemoria presenter;
    private DatabaseReference reference;
    String CategoriaId= "-N3iU-GjNXmOnKIUvK5F";
    String id,paciente_id;
    TextView tvtienpo;


    Button btnguardar;
    ImageView img1,img2,img3;

    int contador=1;
    public int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);

        inputs();
        id=getIntent().getStringExtra("id");
        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterMemoria(this,reference,paciente_id);
        CargeFiguras();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (id!="") {
            viewMemoria(id);
        }
        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                tvtienpo.setText(String.valueOf(counter));
                counter++;
            }
            public  void onFinish(){
                tvtienpo.setText("FINISH!!");
                finish();
                Toast.makeText(MemoriaActivity.this, counter+"", Toast.LENGTH_SHORT).show();
                star();
            }
        }.start();
    }

    private  void  star(){
        Intent intent= new Intent(this,ResponderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("paciente_id",paciente_id);
        bundle.putString("CategoriaId",CategoriaId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void viewMemoria(String id) {
        presenter.ViewMemoria(value -> {

//            String figura1=value.getFigura1();
//            img1.setImageResource(R.drawable.bici);
//            img2.setImageResource(R.drawable.casa);
//            img3.setImageResource(R.drawable.globos);

        }, CategoriaId,id);
    }
    private void inputs() {

//        img1=(ImageView) findViewById(R.id.img1);
//        img2=(ImageView) findViewById(R.id.img2);
//        img3=(ImageView) findViewById(R.id.img3);

        tvtienpo=(TextView) findViewById(R.id.tvtienpo);
    }
    private  void  goActivity(){

    }
    private  void  CargeFiguras(){
//        GridView gridView =findViewById(R.id.simpleGridView);
//        presenter.cargarGrilla(gridView);
    }
    @Override
    public void onClick(View view) {

    }
}