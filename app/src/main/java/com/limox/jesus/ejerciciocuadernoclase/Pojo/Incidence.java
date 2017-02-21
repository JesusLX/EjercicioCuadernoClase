package com.limox.jesus.ejerciciocuadernoclase.Pojo;

/**
 * Created by usuario on 21/02/17.
 */

public class Incidence {
    private long id;
    private long id_user;
    private String faltas;
    private String trabajo;
    private String actitud;
    private String objservaciones;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getFaltas() {
        return faltas;
    }

    public void setFaltas(String faltas) {
        this.faltas = faltas;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getActitud() {
        return actitud;
    }

    public void setActitud(String actitud) {
        this.actitud = actitud;
    }

    public String getObjservaciones() {
        return objservaciones;
    }

    public void setObjservaciones(String objservaciones) {
        this.objservaciones = objservaciones;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
