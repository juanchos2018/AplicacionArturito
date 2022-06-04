package com.example.aplicacionarturito.Model;

public class Horas {

    String psicolog_id;
    String horaincio;
    String horafin;
    String estado;
    String fecha;


    public  Horas(){

    }
    public String getPsicolog_id() {
        return psicolog_id;
    }

    public void setPsicolog_id(String psicolog_id) {
        this.psicolog_id = psicolog_id;
    }

    public String getHoraincio() {
        return horaincio;
    }

    public void setHoraincio(String horaincio) {
        this.horaincio = horaincio;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
