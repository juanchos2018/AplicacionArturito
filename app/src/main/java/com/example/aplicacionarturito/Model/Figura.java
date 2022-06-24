package com.example.aplicacionarturito.Model;

public class Figura {

    private  String id;
    private  String type;
    private  String figura;

    public Figura(String id, String type, String figura) {
        this.id = id;
        this.type = type;
        this.figura = figura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }


}
