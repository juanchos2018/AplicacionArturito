package com.example.aplicacionarturito.Model;

public class PsicoloPaciente {

    String id;
    String psicologo_id;
    String paciente_id;
    String nombres;
    String fecha;
    String hora;
    String photo;
    String estado;

    public  PsicoloPaciente(){

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsicologo_id() {
        return psicologo_id;
    }

    public void setPsicologo_id(String psicologo_id) {
        this.psicologo_id = psicologo_id;
    }

    public String getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(String paciente_id) {
        this.paciente_id = paciente_id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
