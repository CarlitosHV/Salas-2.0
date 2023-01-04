package com.example.salas.Model;

public class Sucursal {

    private String sucursalesID;
    private String code;
    private String direction;
    private String longitud;
    private String latitude;
    private String userEmail;


    public Sucursal() {
    }

    public Sucursal(String sucursalesID, String code, String direction, String longitud, String latitude, String userEmail) {
        this.setSucursalesID(sucursalesID);
        this.setCode(code);
        this.setDirection(direction);
        this.setLongitud(longitud);
        this.setLatitude(latitude);
        this.setUserEmail(userEmail);
    }

    public String getSucursalesID() {
        return sucursalesID;
    }

    public void setSucursalesID(String sucursalesID) {
        this.sucursalesID = sucursalesID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
