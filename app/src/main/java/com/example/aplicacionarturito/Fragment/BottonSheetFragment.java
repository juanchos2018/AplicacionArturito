package com.example.aplicacionarturito.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionarturito.Interface.InterfacePsicologo;
import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottonSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottonSheetFragment extends BottomSheetDialogFragment implements InterfacePsicologo {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    PresenterPsicologo presenter;
    private DatabaseReference reference;
    public String paciente_id;
    Paciente paciente;
    private BootonClickLisntener mListener;



    public BottonSheetFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static BottonSheetFragment newInstance(String param1, String param2) {
//        BottonSheetFragment fragment = new BottonSheetFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


    public static BottonSheetFragment newInstance() {
        BottonSheetFragment fragment = new BottonSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        final View contentView = View.inflate(getContext(), R.layout.fragment_botton_sheet, null);


        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterPsicologo(getContext(),reference,this);
        ListaPsicologos(paciente_id,contentView);

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        // loadAddNotesFragments();
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view=inflater.inflate(R.layout.fragment_botton_sheet, container, false);
//        reference= FirebaseDatabase.getInstance().getReference();
//        presenter= new PresenterPsicologo(getContext(),reference,this);
//
//       /// paciente_id= getIntent().getStringExtra("id");
//
//        ListaPsicologos(paciente_id,view);
//        return view;
//    }


    private  void  ListaPsicologos( String paciente_id,View view){
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerpsicologos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,paciente_id);

    }

    @Override
    public void onCallback(Psicologo value) {

    }

    @Override
    public void onCallbackFechas(List<Fecha> fechas) {

    }

    @Override
    public void onCallbackFechas2(List<Integer> fechas, List<Fecha> fechas2) {

    }

    public  void borrar(){
      
    }


    public  interface  BootonClickLisntener{
        void  onButtonclick(String texto);
        void  copiartexto(String a);
        void  EliminarArchivo(String keyarchivo);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            if (context instanceof BootonClickLisntener){
                mListener = (BootonClickLisntener) context;
            }else{

                throw  new ClassCastException(context.toString()+"musimpemet");
            }
            // mListener=(BootonClickLisntener)context;
        }catch (ClassCastException e){

        }
    }

}