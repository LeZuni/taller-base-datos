package com.mycompany.taller.base.datos.dao;

import com.mycompany.taller.base.datos.config.ConexionBD;
import com.mycompany.taller.base.datos.model.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public int insertar(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, correo, cargo) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setString(3, e.getCargo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public List<Empleado> listar() throws SQLException {
        String sql = "SELECT id, nombre, correo, cargo FROM empleados ORDER BY id";
        List<Empleado> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Empleado buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, correo, cargo FROM empleados WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public Empleado buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT id, nombre, correo, cargo FROM empleados WHERE correo = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean actualizar(Empleado e) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, correo = ?, cargo = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setString(3, e.getCargo());
            ps.setInt(4, e.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int id) throws SQLException {
        String sql = "SELECT 1 FROM empleado_proyecto WHERE empleado_id = ? LIMIT 1";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Empleado mapear(ResultSet rs) throws SQLException {
        return new Empleado(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("correo"), rs.getString("cargo"));
    }
}
