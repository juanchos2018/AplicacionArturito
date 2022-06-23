package com.example.aplicacionarturito.Model;

public class ItemObject {

    private String _name;
    private String _author;
    private String figura;

    public ItemObject(String name, String auth,String figura)
    {
        this._name = name;
        this._author = auth;
        this.figura=figura;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getAuthor()
    {
        return _author;
    }

    public void setAuthor(String auth)
    {
        this._author = auth;
    }


    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }
}
