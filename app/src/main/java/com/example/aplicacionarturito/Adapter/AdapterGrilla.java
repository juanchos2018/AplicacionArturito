package com.example.aplicacionarturito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aplicacionarturito.Model.Images;
import com.example.aplicacionarturito.R;

import java.util.ArrayList;

public class AdapterGrilla  extends ArrayAdapter<Images> {

    Context context;
    //   RecyclerView.ViewHolder viewHolder;
    ViewHolder viewHolder;
    ArrayList<Images> al_menu = new ArrayList<>();
    public AdapterGrilla(Context context, ArrayList<Images> al_menu) {
        super(context, R.layout.item_grilla, al_menu);
        this.al_menu = al_menu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView== null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grilla, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);//
            convertView.setTag(viewHolder);
        } else {
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);//
            viewHolder = (ViewHolder) convertView.getTag();
            Glide.with(getContext())
                    .load(al_menu.get(position).getPhoto())
                    .placeholder(R.drawable.default_profile_image)
                    .fitCenter()
                    .error(R.drawable.default_profile_image)
                    .centerCrop()
                    .into(viewHolder.imageView);
        }

       // viewHolder.tv_foldern.setText(al_menu.get(position).ge());
        return convertView;

    }


    public static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView imageView,imgicnono;

    }
}

