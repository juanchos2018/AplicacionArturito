package com.example.aplicacionarturito.Model;

public class Resenas {


    String medico_id;
    String usuario_id;
    String nombre;
    String photo;
    String comentario;


    public  Resenas(){

    }

    public String getMedico_id() {
        return medico_id;
    }

    public void setMedico_id(String medico_id) {
        this.medico_id = medico_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
