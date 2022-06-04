package com.example.aplicacionarturito.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aplicacionarturito.Presenter.PresenterPsicologo;
import com.example.aplicacionarturito.Presenter.PresenterResenas;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResenasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResenasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    PresenterResenas presenter;
    private DatabaseReference reference;




    public ResenasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResenasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResenasFragment newInstance(String param1, String param2) {
        ResenasFragment fragment = new ResenasFragment();
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

        View view=inflater.inflate(R.layout.fragment_resenas, container, false);
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterResenas(getContext(),reference);


        ListaPsicologos(view);

        return view;
    }

    private  void  ListaPsicologos(View view){
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerresenas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        String psilocogo_id="-N3iBH2LWijQlld_nCo3";
        presenter.cargarRecycler(recyclerView,psilocogo_id);

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}