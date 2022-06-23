package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.Model.ItemObject;
import com.example.aplicacionarturito.Model.SampleViewHolders;
import com.example.aplicacionarturito.R;

import java.util.List;

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolders>
{
    private List<ItemObject> itemList;
    private Context context;

    public SampleRecyclerViewAdapter(Context context,
                                     List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_figura, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SampleViewHolders holder, int position)
    {
       String tipo=itemList.get(position).getFigura();
        if (tipo=="cuadrado"){
            holder.imgrectagulo.setImageResource(R.drawable.cuadradoapp);
        }
        if (tipo=="rectangulo"){
            holder.imgrectagulo.setImageResource(R.drawable.rectaguloapp);
        }
        if (tipo=="triangulo"){
            holder.imgrectagulo.setImageResource(R.drawable.rectaguloapp);
        }
        if (tipo=="rombo"){
            holder.imgrectagulo.setImageResource(R.drawable.romboapp);
        }
        if (tipo=="circulo"){
            holder.imgrectagulo.setImageResource(R.drawable.circuloapp);
        }
//        holder.bookName.setText(itemList.get(position).getName());
  //      holder.authorName.setText(itemList.get(position).getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}