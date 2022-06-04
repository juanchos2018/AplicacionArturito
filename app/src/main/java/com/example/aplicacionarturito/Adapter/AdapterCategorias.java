package com.example.aplicacionarturito.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.ConfirmarActivity;
import com.example.aplicacionarturito.Model.Categoria;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterCategorias  extends RecyclerView.Adapter<AdapterCategorias.ViewHolderDatos> {


//Categoria

    ArrayList<Categoria> listaItems;

    public AdapterCategorias(ArrayList<Categoria> listaItems) {
        this.listaItems = listaItems;
    }

    @NonNull
    @Override
    public AdapterCategorias.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_categorias,parent,false);

        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategorias.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;

            datgolder.tvcategoria.setText(listaItems.get(position).getNombre());


        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tvcategoria,hora,nombremedico,especialidad,tvestado;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvcategoria=(TextView)itemView.findViewById( R.id.tvcategoria);

        }
    }
}
