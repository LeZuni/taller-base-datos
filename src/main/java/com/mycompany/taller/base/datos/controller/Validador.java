package com.mycompany.taller.base.datos.controller;

import java.sql.SQLException;

final class Validador {

    private Validador() {
    }

    static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo '" + campo + "' es obligatorio.");
        }
        return valor.trim();
    }

    static void id(int valor, String campo) {
        if (valor <= 0) {
            throw new ValidacionException("El " + campo + " debe ser un numero entero positivo.");
        }
    }

    static ValidacionException errorBD(SQLException e) {
        return new ValidacionException("Error de base de datos: " + e.getMessage(), e);
    }
}
