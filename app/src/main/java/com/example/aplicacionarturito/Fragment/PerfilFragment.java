package com.example.aplicacionarturito.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class PerfilFragment extends Fragment implements InterfacePsicologo {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    PresenterPsicologo presenter;
    private DatabaseReference reference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mArg = "non-param";

    TextView tvdescripcion,tvformacion;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public PerfilFragment(String id) {
        mArg=id;

    }

    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterPsicologo(getContext(),reference,this);

        inputs(view);
        infoPsicologo(mArg);
        return view;
    }

    private void inputs(View view) {

        tvdescripcion=(TextView) view.findViewById(R.id.tvdescripcion);
        tvformacion=(TextView) view.findViewById(R.id.tvformacion);

    }


    private  void infoPsicologo(String psicologo_id){
        presenter.info(psicologo_id);
//        presenter.info(new InterfacePsicologo() {
//            @Override
//            public void onCallback(Psicologo value) {
//                tvdescripcion.setText(value.getDescripcion());
//                tvformacion.setText(value.getFormacion());
//            }
//
//            @Override
//            public void onCallbackFechas(List<Fecha> fechas) {
//
//            }
//
//            @Override
//            public void onCallbackFechas2(List<Integer> fechas) {
//
//            }
//        },psicologo_id);
    }

    @Override
    public void onCallback(Psicologo value) {
        tvdescripcion.setText(value.getDescripcion());
        tvformacion.setText(value.getFormacion());
    }

    @Override
    public void onCallbackFechas(List<Fecha> fechas) {

    }

    @Override
    public void onCallbackFechas2(List<Integer> fechas,List<Fecha> fechas2) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}