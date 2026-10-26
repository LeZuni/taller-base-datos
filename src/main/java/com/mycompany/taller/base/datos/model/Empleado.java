package com.mycompany.taller.base.datos.model;

public class Empleado {

    private int id;
    private String nombre;
    private String correo;
    private String cargo;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String correo, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.cargo = cargo;
    }

    public Empleado(String nombre, String correo, String cargo) {
        this(0, nombre, correo, cargo);
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s", id, nombre, correo, cargo);
    }
}
