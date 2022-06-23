package com.example.aplicacionarturito.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionarturito.R;

public class SampleViewHolders extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public TextView bookName;
    public TextView authorName;
    public ImageView imgrectagulo;

    public SampleViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        imgrectagulo=(ImageView) itemView.findViewById(R.id.imgrectagulo);
        ///bookName = (TextView) itemView.findViewById(R.id.BookName);
        //authorName = (TextView) itemView.findViewById(R.id.AuthorName);
    }

    @Override
    public void onClick(View view)
    {
        Toast.makeText(view.getContext(),
                "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT)
                .show();
    }
}