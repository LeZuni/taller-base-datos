package com.mycompany.taller.base.datos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gestion_proyectos?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "root1234";

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
