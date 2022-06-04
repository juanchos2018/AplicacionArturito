package com.example.aplicacionarturito.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.PsicologoViewActivity;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.Model.Resenas;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterResenas extends RecyclerView.Adapter<AdapterResenas.ViewHolderDatos> {


    ArrayList<Resenas> listaResenas;

    public AdapterResenas(ArrayList<Resenas> listaResenas) {
        this.listaResenas = listaResenas;
    }

    //tvcomentario
    @NonNull
    @Override
    public AdapterResenas.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_resenas,parent,false);
       // vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResenas.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvcomentario.setText(listaResenas.get(position).getComentario());

        }
    }

    @Override
    public int getItemCount() {
        return listaResenas.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvcomentario,hora,nombremedico,especialidad,tvestado;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvcomentario=(TextView)itemView.findViewById( R.id.tvcomentario);
        }
    }
}
