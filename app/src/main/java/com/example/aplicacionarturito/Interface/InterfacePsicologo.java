package com.example.aplicacionarturito.Interface;

import android.content.Context;

import com.example.aplicacionarturito.Model.Fecha;
import com.example.aplicacionarturito.Model.Psicologo;

import java.util.List;

public interface InterfacePsicologo {
    void onCallback(Psicologo value);

    void onCallbackFechas(List<Fecha> fechas);

    void onCallbackFechas2(List<Integer> fechas,List<Fecha> fechas2);

    Context getContext();

}
