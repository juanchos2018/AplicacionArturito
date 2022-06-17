package com.example.aplicacionarturito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacionarturito.Activity.ConsultaActivity;
import com.example.aplicacionarturito.Activity.SolicitarActivity;
import com.example.aplicacionarturito.Activity.ViewPagerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {



    Button btnsalir;
    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;

    CardView cadsolicitar,carconsulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnsalir=(Button)findViewById(R.id.btnsalir);
        btnsalir.setOnClickListener(this);
        cadsolicitar=(CardView)findViewById(R.id.cadsolicitar);
        carconsulta=(CardView)findViewById(R.id.carconsulta);
        cadsolicitar.setOnClickListener(this);
        carconsulta.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsalir:
                logOutUser();
                break;
            case R.id.cadsolicitar:
                    startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.carconsulta:
                startActivity(new Intent(this, ConsultaActivity.class));
                break;
        }
    }

    private void logOutUser() {
        //boton salir
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent loginIntent =  new Intent(PrincipalActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

}