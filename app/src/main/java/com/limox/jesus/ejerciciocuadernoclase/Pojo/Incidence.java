package com.limox.jesus.ejerciciocuadernoclase.Pojo;

import java.io.Serializable;

/**
 * Created by usuario on 21/02/17.
 */

public class Incidence implements Serializable{
    private long id;
    private long id_user;
    private String name;
    private String faltas;
    private String trabajo;
    private String actitud;
    private String observaciones;
    private String date;

    public Incidence(String faltas, String trabajo, String actitud, String observaciones, String date, long id_user) {

        this.faltas = faltas;
        this.trabajo = trabajo;
        this.actitud = actitud;
        this.observaciones = observaciones;
        this.date = date;
        this.id_user = id_user;
    }

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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
