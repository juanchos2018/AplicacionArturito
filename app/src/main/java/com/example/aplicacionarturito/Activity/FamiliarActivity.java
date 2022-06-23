package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacionarturito.Presenter.PresenterFamiliar;
import com.example.aplicacionarturito.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FamiliarActivity extends AppCompatActivity  implements View.OnClickListener {


    FloatingActionButton btnaddfamiliar;

    PresenterFamiliar presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiar);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        presenter= new PresenterFamiliar(this,reference,user_id);

        inputs();

    }




    @Override
    protected void onStart() {
        super.onStart();
        records();

    }
    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerfamiliar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView);
    }
    private void inputs() {

        btnaddfamiliar=(FloatingActionButton) findViewById(R.id.btnaddfamiliar);
        btnaddfamiliar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnaddfamiliar:
                startActivity(new Intent(this,ViewPagerActivity.class));
                break;
        }
    }
}