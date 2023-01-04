package com.example.salas.Model;

public class ListaUsers {
    private final Integer iconoprod;
    private final String nomprod;


    public ListaUsers(Integer iconoprod, String nomprod) {
        this.iconoprod = iconoprod;
        this.nomprod = nomprod;
    }

    public Integer getIconoprod() {
        return iconoprod;
    }

    public String getNomprod() {
        return nomprod;
    }
}
