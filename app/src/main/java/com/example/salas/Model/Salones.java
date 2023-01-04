package com.example.salas.Model;

public class Salones {

    private String Edificio;
    private String Descripcion;
    private double Aula;
    private boolean prestado;
    private String propietario;
    private String alumno;
    private String id;
    private String idProfe;
    private String idPrestamo;

    public Salones() {
    }

    public Salones(String edificio, String descripcion, double aula, String propietario, String idProfe) {
        Edificio = edificio;
        Descripcion = descripcion;
        Aula = aula;
        this.propietario = propietario;
        this.idProfe = idProfe;
    }

    public String getEdificio() {
        return Edificio;
    }

    public void setEdificio(String edificio) {
        Edificio = edificio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getAula() {
        return Aula;
    }

    public void setAula(double aula) {
        Aula = aula;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdProfe() {
        return idProfe;
    }

    public void setIdProfe(String idProfe) {
        this.idProfe = idProfe;
    }
}
