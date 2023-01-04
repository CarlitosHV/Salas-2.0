package com.example.salas.Model;

public class UserType {

    private String email;
    private boolean tipo;
    private String ID;
    private String name;

    public UserType() {
    }

    public UserType(String email, boolean tipo, String ID, String name) {
        this.setEmail(email);
        this.setTipo(tipo);
        this.setID(ID);
        this.setName(name);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
