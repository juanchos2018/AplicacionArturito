package com.example.aplicacionarturito.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacionarturito.Presenter.PresenterLogin;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class RegisterFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private DatabaseReference reference;

    PresenterLogin presenterLogin;

    EditText tvnonbre,tvapellido,tvcorreo,tvclave,tvclave2;
    Button btnregistrar;

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        tvnonbre=(EditText) view.findViewById(R.id.tvnonbre);
        tvapellido=(EditText) view.findViewById(R.id.tvapellido);
        tvcorreo=(EditText) view.findViewById(R.id.tvcorreo);
        tvclave=(EditText) view.findViewById(R.id.tvclave);
        tvclave2=(EditText) view.findViewById(R.id.tvclave2);
        btnregistrar=(Button) view.findViewById(R.id.btnregistrar);

        btnregistrar.setOnClickListener(this);
        reference= FirebaseDatabase.getInstance().getReference();
        presenterLogin=new PresenterLogin(getContext(),reference);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnregistrar:
                String nombre=tvnonbre.getText().toString();
                String apellido=tvapellido.getText().toString();
                String correo=tvcorreo.getText().toString();
                String clave=tvclave.getText().toString();
                String clave2=tvclave2.getText().toString();
                if (TextUtils.isEmpty(nombre)){
                    tvnonbre.setError("campo necesario");
                }
                else if (TextUtils.isEmpty(apellido)){
                    tvapellido.setError("campo necesario");
                }
                else if (TextUtils.isEmpty(correo)){
                    tvcorreo.setError("campo necesario");
                }
                else if (!isValidEmailId(correo.trim())){
                    tvcorreo.setError("correo invalido");
                }
                else if (TextUtils.isEmpty(clave)){
                    tvclave.setError("correo invalido");
                }else{
                    if (clave.equals(clave2))
                    {   //regitstrear usuarui
                        presenterLogin.registerUser(nombre,apellido,correo,clave);
                        // presenterLogin.Register();
                    }else{
                        Toast.makeText(getContext(), "las claves no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

        }
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}