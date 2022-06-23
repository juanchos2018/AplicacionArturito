package com.example.aplicacionarturito.Model;

public class Atencion {


    private String id;
    private String CategoriaId;
    private String figura;
    private String cantidad;
    private String cantidadclick;
    private String cantidafiguras;
    private String estado;

    public Atencion(){

    }

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

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantidadclick() {
        return cantidadclick;
    }

    public void setCantidadclick(String cantidadclick) {
        this.cantidadclick = cantidadclick;
    }

    public String getCantidafiguras() {
        return cantidafiguras;
    }

    public void setCantidafiguras(String cantidafiguras) {
        this.cantidafiguras = cantidafiguras;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
