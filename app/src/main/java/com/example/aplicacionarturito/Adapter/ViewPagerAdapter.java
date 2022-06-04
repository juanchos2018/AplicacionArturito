package com.example.aplicacionarturito.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aplicacionarturito.Fragment.LoginFragment;
import com.example.aplicacionarturito.Fragment.RegisterFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter {


    private String[] titles = {"Login", "Registrarse"};
    private Context context;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }



    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }
}
