package com.example.aplicacionarturito.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.CalendarioActivity;
import com.example.aplicacionarturito.Activity.ConfirmarActivity;
import com.example.aplicacionarturito.Activity.PsicologoViewActivity;
import com.example.aplicacionarturito.Model.Horas;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterHoras extends RecyclerView.Adapter<AdapterHoras.ViewHolderDatos> implements View.OnClickListener {


    ArrayList<Horas> listaItems;

    public AdapterHoras(ArrayList<Horas> listaItems) {
        this.listaItems = listaItems;
    }

    @NonNull
    @Override
    public AdapterHoras.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horas,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoras.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;

            datgolder.tvhoras.setText(listaItems.get(position).getHora_inicio()+" - "+listaItems.get(position).getHora_fin());
            String hora=listaItems.get(position).getHora_inicio()+" - "+listaItems.get(position).getHora_fin();
            datgolder.id_hora=listaItems.get(position).getId();
//            datgolder.btnestado.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent= new Intent(datgolder.btnestado.getContext(), ConfirmarActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id_hora",datgolder.id_hora);
//                    bundle.putString("horaInfin",hora);
//                    intent.putExtras(bundle);
//
//                }
//            });
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
        TextView tvhoras;
        Button btnestado;
        String id_hora;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvhoras=(TextView)itemView.findViewById( R.id.tvhoras);
            btnestado=(Button) itemView.findViewById( R.id.btnestado);
        }
    }
}
