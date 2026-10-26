package com.mycompany.taller.base.datos.model;

import java.time.LocalDate;

public class Proyecto {

    private int id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public Proyecto() {
    }

    public Proyecto(int id, String nombre, String descripcion, LocalDate fechaInicio,
            LocalDate fechaFin, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio,
            LocalDate fechaFin, String estado) {
        this(0, nombre, descripcion, fechaInicio, fechaFin, estado);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s a %s | %s", id, nombre,
                descripcion == null ? "-" : descripcion,
                fechaInicio == null ? "-" : fechaInicio,
                fechaFin == null ? "-" : fechaFin, estado);
    }
}
