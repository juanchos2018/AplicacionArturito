package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacionarturito.Fragment.PerfilFragment;
import com.example.aplicacionarturito.Fragment.ResenasFragment;
import com.example.aplicacionarturito.R;

public class PsicologoViewActivity extends AppCompatActivity implements ResenasFragment.OnFragmentInteractionListener,PerfilFragment.OnFragmentInteractionListener{



    ResenasFragment fragment1;
    PerfilFragment fragment2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psicologo_view);

        fragment1=new ResenasFragment();
        fragment2=new PerfilFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container2, fragment1).commit();




    }


    public void onClick(View view) {

        FragmentTransaction Transaction=getSupportFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.btn11:
                Transaction.replace(R.id.main_container2,fragment1);
                break;
            case R.id.btn22:
                Transaction.replace(R.id.main_container2,fragment2);
                break;
        }
        Transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}