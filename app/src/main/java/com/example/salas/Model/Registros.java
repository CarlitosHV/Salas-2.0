package com.example.salas.Model;

import java.util.Date;

public class Registros {
    private String ID;
    private String userEmail;
    private Date fechaPrestamo;
    private Date fechaDevuelto;
    private String Herramientas[];
    private String Consumibles[];
    //Falta herramientas y consumibles arreglos
    private boolean status;

    public Registros(){

    }



    public Registros(String ID, String userEmail, Date fechaPrestamo, Date fechaDevuelto, boolean status, String Herramientas[], String Consumibles[]){
        this.setID(ID);
        this.setUserEmail(userEmail);
        this.setFechaPrestamo(fechaPrestamo);
        this.setFechaDevuelto(fechaDevuelto);
        this.setStatus(status);
        this.setHerramientas(Herramientas);
        this.setConsumibles(Consumibles);

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String[] getHerramientas() {
        return Herramientas;
    }

    public void setHerramientas(String[] herramientas) {
        Herramientas = herramientas;
    }

    public String[] getConsumibles() {
        return Consumibles;
    }

    public void setConsumibles(String[] consumibles) {
        Consumibles = consumibles;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Date getFechaDevuelto() {
        return fechaDevuelto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevuelto(Date fechaDevuelto) {
        this.fechaDevuelto = fechaDevuelto;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
