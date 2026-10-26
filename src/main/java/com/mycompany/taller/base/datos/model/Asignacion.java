package com.mycompany.taller.base.datos.model;

import java.time.LocalDate;

public class Asignacion {

    private int empleadoId;
    private int proyectoId;
    private String rol;
    private LocalDate fechaAsignacion;
    private double horasSemanales;
    private String nombreEmpleado;
    private String cargoEmpleado;
    private String nombreProyecto;

    public Asignacion() {
    }

    public Asignacion(int empleadoId, int proyectoId, String rol, LocalDate fechaAsignacion,
            double horasSemanales) {
        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public double getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(double horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    @Override
    public String toString() {
        return String.format("Empleado %d (%s) -> Proyecto %d (%s) | Rol: %s | Desde: %s | %.1f h/sem",
                empleadoId, nombreEmpleado == null ? "-" : nombreEmpleado,
                proyectoId, nombreProyecto == null ? "-" : nombreProyecto,
                rol, fechaAsignacion, horasSemanales);
    }
}
