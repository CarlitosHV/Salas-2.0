package com.example.salas.Model;

import java.util.Date;

public class Herramientas {

    private String code;
    private String descripcion;
    private Date fecha;
    private int count;
    private String sucursalID;

    public Herramientas(String code, String description, Date fecha, int count) {
    }

    public Herramientas(String code, String descripcion, Date fecha, int count, String sucursalID) {
        this.setCode(code);
        this.setDescripcion(descripcion);
        this.setFecha(fecha);
        this.setCount(count);
        this.setSucursalID(sucursalID);
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(String sucursalID) {
        this.sucursalID = sucursalID;
    }
}
