package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Model.Atencion;
import com.example.aplicacionarturito.Model.Lectura;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterFiguras extends RecyclerView.Adapter<AdapterFiguras.ViewHolderDatos> implements View.OnClickListener{


    ArrayList<Atencion> listaItems;
    Context context;
    public AdapterFiguras(ArrayList<Atencion> listaItems,Context context) {
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
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_atencion,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvfigura.setText(listaItems.get(position).getFigura());
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imgrectagulo;
        TextView tvfigura;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //imgrectagulo=(ImageView) itemView.findViewById(R.id.imgrectagulo);
            tvfigura=(TextView) itemView.findViewById(R.id.tvfigura);
        }
    }
}
