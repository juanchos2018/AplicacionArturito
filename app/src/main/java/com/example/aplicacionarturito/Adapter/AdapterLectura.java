package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.LecturaActivity;
import com.example.aplicacionarturito.Model.Lectura;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterLectura extends RecyclerView.Adapter<AdapterLectura.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<Lectura> listaItems;
    Context context;
    public AdapterLectura(ArrayList<Lectura> listaItems,Context context) {
        this.context=context;
        this.listaItems = listaItems;
    }

    private View.OnClickListener listener;

    public  void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_lectura,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvtitulo1.setText(listaItems.get(position).getTitulo());
            datgolder.id=listaItems.get(position).getId();

        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvtitulo1;
        Button btneditar;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvtitulo1=(TextView) itemView.findViewById(R.id.tvtitulo1);
            btneditar=(Button) itemView.findViewById(R.id.btneditar);
        }
    }
}

