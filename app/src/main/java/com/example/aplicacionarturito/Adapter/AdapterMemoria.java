package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Model.Lectura;
import com.example.aplicacionarturito.Model.Memoria;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterMemoria extends RecyclerView.Adapter<AdapterMemoria.ViewHolderDatos> implements View.OnClickListener{


    ArrayList<Memoria> listaItems;
    Context context;
    public AdapterMemoria(ArrayList<Memoria> listaItems,Context context) {
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
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_memoria ,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvtitulo1.setText(listaItems.get(position).getTitulo());
            datgolder.id=listaItems.get(position).getId();

            if (listaItems.get(position).getEstado().equals("resuelto")){
                datgolder.imgestado.setImageResource(R.drawable.ic_check);
            }else{
                datgolder.imgestado.setImageResource(R.drawable.ic_empty);
            }

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
        ImageView imgestado;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvtitulo1=(TextView) itemView.findViewById(R.id.tvtitulo1);
            btneditar=(Button) itemView.findViewById(R.id.btneditar);
            imgestado=(ImageView) itemView.findViewById(R.id.imgestado);
        }
    }
}
