package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Model.Atencion;
import com.example.aplicacionarturito.Model.Figura;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterFiguras2 extends RecyclerView.Adapter<AdapterFiguras2.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<Figura> listaItems;
    Context context;
    public AdapterFiguras2(ArrayList<Figura> listaItems,Context context) {
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
    public AdapterFiguras2.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_figura,null,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFiguras2.ViewHolderDatos holder, int position) {

        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;

            String tipo=listaItems.get(position).getType();

            if (tipo=="cuadrado"){
                holder.imgrectagulo.setImageResource(R.drawable.cuadradoapp);
            }
            if (tipo=="rectangulo"){
                holder.imgrectagulo.setImageResource(R.drawable.rectaguloapp);
            }
            if (tipo=="triangulo"){
                holder.imgrectagulo.setImageResource(R.drawable.trianguloapp);
            }
            if (tipo=="rombo"){
                holder.imgrectagulo.setImageResource(R.drawable.romboapp);
            }
            if (tipo=="circulo"){
                holder.imgrectagulo.setImageResource(R.drawable.circuloapp);
            }
        }


    }

    @Override
    public int getItemCount() {
        return this.listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
         ImageView imgrectagulo;
         String tipo;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            imgrectagulo=(ImageView) itemView.findViewById(R.id.imgrectagulo);
        }

    }
}
