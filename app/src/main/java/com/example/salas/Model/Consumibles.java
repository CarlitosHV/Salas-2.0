package com.example.salas.Model;

import java.util.Date;

public class Consumibles {

    private String code;
    private String descripcion;
    private Date fecha;
    private float count;
    private String sucursalID;


    public Consumibles() {
    }

    public Consumibles(String code, String descripcion, Date fecha, float count, String sucursalID) {
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

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(String sucursalID) {
        this.sucursalID = sucursalID;
    }
}
