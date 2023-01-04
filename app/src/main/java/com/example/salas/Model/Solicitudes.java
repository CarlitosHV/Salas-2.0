package com.example.salas.Model;

import java.util.Date;

public class Solicitudes {
    private String ID;
    private String userEmail;
    private String status; //Creo que es boolean
    private Date fecha;
    private String comentario;
    private String Herramientas[];
    private String Consumibles[];
    //Falta herramientas y consumibles


    public Solicitudes() {

    }

    public Solicitudes(String ID, String userEmail, String status, Date fecha, String comentario, String Herramientas[], String Consumibles[]) {
        this.setID(ID);
        this.setUserEmail(userEmail);
        this.setStatus(status);
        this.setFecha(fecha);
        this.setComentario(comentario);
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

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
