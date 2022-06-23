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
import com.example.aplicacionarturito.Model.Categoria;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterFamiliar extends RecyclerView.Adapter<AdapterFamiliar.ViewHolderDatos>  {


    ArrayList<Paciente> listaItems;
    Context context;

    public AdapterFamiliar(ArrayList<Paciente> listaItems,Context context) {
        this.listaItems = listaItems;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterFamiliar.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_familiar,parent,false);

        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFamiliar.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;

            datgolder.tvnombreFa.setText(listaItems.get(position).getNombre()+" "+listaItems.get(position).getApellidos());
            datgolder.id=listaItems.get(position).getId();
            datgolder.btncita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PsicologosActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", datgolder.id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
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
