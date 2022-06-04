package com.example.aplicacionarturito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacionarturito.Fragment.LoginFragment;
import com.example.aplicacionarturito.Fragment.MainFragment;
import com.example.aplicacionarturito.Fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,RegisterFragment.OnFragmentInteractionListener{


    LoginFragment fragment1;
    RegisterFragment fragment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragment1=new LoginFragment();
        fragment2=new RegisterFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment1).commit();

    }

    public void onClick(View view) {

        FragmentTransaction Transaction=getSupportFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.btn1:
                Transaction.replace(R.id.main_container,fragment1);
                break;
            case R.id.btn2:
                Transaction.replace(R.id.main_container,fragment2);
                break;
        }
        Transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}