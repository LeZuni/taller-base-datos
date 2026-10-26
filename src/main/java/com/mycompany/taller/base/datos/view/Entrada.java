package com.mycompany.taller.base.datos.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Lectura segura desde consola: repite la pregunta hasta recibir un valor con formato valido. */
class Entrada {

    private final Scanner sc = new Scanner(System.in);

    String texto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    int entero(String mensaje) {
        while (true) {
            String s = texto(mensaje);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("  Valor invalido: ingrese un numero entero.");
            }
        }
    }

    double decimal(String mensaje) {
        while (true) {
            String s = texto(mensaje).replace(',', '.');
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("  Valor invalido: ingrese un numero (ej. 20 o 12.5).");
            }
        }
    }

    /** Devuelve null si el usuario deja el campo vacio. Formato yyyy-MM-dd. */
    LocalDate fecha(String mensaje) {
        while (true) {
            String s = texto(mensaje);
            if (s.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("  Fecha invalida: use el formato AAAA-MM-DD (ej. 2026-03-15).");
            }
        }
    }
}
