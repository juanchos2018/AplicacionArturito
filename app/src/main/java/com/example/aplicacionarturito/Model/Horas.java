package com.example.aplicacionarturito.Model;

public class Horas {

    private String  id;
    private Integer index;
    private String  fecha_id;
    private String  hora_inicio;
    private String  hora_fin;
    private String  estado;

    public  Horas(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFecha_id() {
        return fecha_id;
    }

    public void setFecha_id(String fecha_id) {
        this.fecha_id = fecha_id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
