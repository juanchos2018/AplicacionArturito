package com.example.aplicacionarturito.Model;

import java.io.Serializable;

public class Memoria implements Serializable {


    private  String id;
    private  String  CategoriaId;
    private  String  titulo;
    private  String figura1;
    private  String figura2;
    private  String figura3;
    private  String cantidad;
    private  String tiempo;
    private  String tiempodemorado;
    private  String estado;


    private  String audio;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFigura1() {
        return figura1;
    }

    public void setFigura1(String figura1) {
        this.figura1 = figura1;
    }

    public String getFigura2() {
        return figura2;
    }

    public void setFigura2(String figura2) {
        this.figura2 = figura2;
    }

    public String getFigura3() {
        return figura3;
    }

    public void setFigura3(String figura3) {
        this.figura3 = figura3;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTiempodemorado() {
        return tiempodemorado;
    }

    public void setTiempodemorado(String tiempodemorado) {
        this.tiempodemorado = tiempodemorado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }


}
