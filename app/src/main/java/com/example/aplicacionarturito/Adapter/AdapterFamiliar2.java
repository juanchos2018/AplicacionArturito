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

import com.example.aplicacionarturito.Activity.PsicologosActivity;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterFamiliar2   extends RecyclerView.Adapter<AdapterFamiliar2.ViewHolderDatos> implements View.OnClickListener{


    ArrayList<Paciente> listaItems;
    Context context;

    public AdapterFamiliar2(ArrayList<Paciente> listaItems,Context context) {
        this.listaItems = listaItems;
        this.context=context;
    }


    @NonNull
    @Override
    public AdapterFamiliar2.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_familiar2,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFamiliar2.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;

            datgolder.tvnombreFa.setText(listaItems.get(position).getNombre()+" "+listaItems.get(position).getApellidos());
            datgolder.id=listaItems.get(position).getId();

        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
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

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvnombreFa,hora,nombremedico,especialidad,tvestado;
        Button btncita;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvnombreFa=(TextView)itemView.findViewById( R.id.tvnombreFa);
            btncita=(Button)itemView.findViewById( R.id.btncita);
        }
    }
}
