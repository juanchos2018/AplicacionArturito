package com.example.aplicacionarturito.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Activity.CalendarioActivity;
import com.example.aplicacionarturito.Activity.PsicologoViewActivity;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Model.Psicologo;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterPsicologos  extends RecyclerView.Adapter<AdapterPsicologos.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<Psicologo> listaPsicologos;
    Paciente paciente;

    public AdapterPsicologos(ArrayList<Psicologo> listaPsicologos, Paciente paciente) {
        this.listaPsicologos = listaPsicologos;
        this.paciente=paciente;
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
    public AdapterPsicologos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_psicologos,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPsicologos.ViewHolderDatos holder, int position) {
        // holder.viewBind(listaCitas.get(position));
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
           // datgolder.nombrepsicologo.setText(simpleDateFormat.format(listaCitas.get(position).getFecha()));
            datgolder.nombrepsicologo.setText(listaPsicologos.get(position).getNombre()+" "+listaPsicologos.get(position).getApellido());
            datgolder.id=listaPsicologos.get(position).getId();


            datgolder.btnver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(datgolder.btnver.getContext(), PsicologoViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", datgolder.id);
                    bundle.putSerializable("paciente",paciente);
                    intent.putExtras(bundle);
                    datgolder.btnver.getContext().startActivity(intent);
                }
            });

            datgolder.btnconsultar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(datgolder.btnconsultar.getContext(), CalendarioActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", datgolder.id);
                    bundle.putSerializable("paciente",paciente);
                    intent.putExtras(bundle);
                    datgolder.btnconsultar.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaPsicologos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombrepsicologo,hora,nombremedico,especialidad,tvestado;
        ImageView photopsicologo;
        Button btnver,btnconsultar;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombrepsicologo=(TextView)itemView.findViewById( R.id.nombrepsicologo);
            photopsicologo=(ImageView) itemView.findViewById( R.id.photopsicologo);
            btnver=(Button)itemView.findViewById(R.id.btnver);
            btnconsultar=(Button)itemView.findViewById(R.id.btnconsultar);
        }
    }
}
