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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }


    EditText etcorreo,etclave;
    Button  btnlogin;

    private DatabaseReference reference;

    PresenterLogin presenterLogin;


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static LoginFragment newInstance(int i) {
        return new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etcorreo=(EditText) view.findViewById(R.id.tvcorreo2);
        etclave=(EditText) view.findViewById(R.id.tvclave2);
        btnlogin=(Button)view.findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);

        reference= FirebaseDatabase.getInstance().getReference();
        presenterLogin=new PresenterLogin(getContext(),reference);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogin:

                String correo=etcorreo.getText().toString();
                String clave=etclave.getText().toString();
                if (TextUtils.isEmpty(correo))
                {
                    etcorreo.setError("correo necesario");

                }else if (TextUtils.isEmpty(clave)){
                    etclave.setError("campo necesario");
                }
                else{
                    presenterLogin.login(correo,clave);
                }
                break;

        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}