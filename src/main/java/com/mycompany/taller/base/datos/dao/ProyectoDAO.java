package com.mycompany.taller.base.datos.dao;

import com.mycompany.taller.base.datos.config.ConexionBD;
import com.mycompany.taller.base.datos.model.Proyecto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    private static final String COLUMNAS = "id, nombre, descripcion, fecha_inicio, fecha_fin, estado";

    public int insertar(Proyecto p) throws SQLException {
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setObject(3, p.getFechaInicio());
            ps.setObject(4, p.getFechaFin());
            ps.setString(5, p.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public List<Proyecto> listar() throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM proyectos ORDER BY id";
        List<Proyecto> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Proyecto buscarPorId(int id) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM proyectos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean actualizar(Proyecto p) throws SQLException {
        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, "
                + "fecha_fin = ?, estado = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setObject(3, p.getFechaInicio());
            ps.setObject(4, p.getFechaFin());
            ps.setString(5, p.getEstado());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proyectos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int id) throws SQLException {
        String sql = "SELECT 1 FROM empleado_proyecto WHERE proyecto_id = ? LIMIT 1";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Proyecto mapear(ResultSet rs) throws SQLException {
        return new Proyecto(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                rs.getObject("fecha_inicio", LocalDate.class),
                rs.getObject("fecha_fin", LocalDate.class),
                rs.getString("estado"));
    }
}
