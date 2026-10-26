package com.mycompany.taller.base.datos.controller;

public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }

    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
