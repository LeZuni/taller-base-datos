package com.mycompany.taller.base.datos.dao;

import com.mycompany.taller.base.datos.config.ConexionBD;
import com.mycompany.taller.base.datos.model.Asignacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {

    private static final String SELECT_JOIN =
            "SELECT ep.empleado_id, ep.proyecto_id, ep.rol, ep.fecha_asignacion, ep.horas_semanales, "
            + "e.nombre AS nombre_empleado, e.cargo AS cargo_empleado, p.nombre AS nombre_proyecto "
            + "FROM empleado_proyecto ep "
            + "JOIN empleados e ON e.id = ep.empleado_id "
            + "JOIN proyectos p ON p.id = ep.proyecto_id ";

    public void insertar(Asignacion a) throws SQLException {
        String sql = "INSERT INTO empleado_proyecto "
                + "(empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getEmpleadoId());
            ps.setInt(2, a.getProyectoId());
            ps.setString(3, a.getRol());
            ps.setObject(4, a.getFechaAsignacion());
            ps.setDouble(5, a.getHorasSemanales());
            ps.executeUpdate();
        }
    }

    public Asignacion buscar(int empleadoId, int proyectoId) throws SQLException {
        String sql = SELECT_JOIN + "WHERE ep.empleado_id = ? AND ep.proyecto_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** Cambia solo rol y horas; la pareja empleado-proyecto y la fecha no se tocan. */
    public boolean actualizar(int empleadoId, int proyectoId, String rol, double horas) throws SQLException {
        String sql = "UPDATE empleado_proyecto SET rol = ?, horas_semanales = ? "
                + "WHERE empleado_id = ? AND proyecto_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol);
            ps.setDouble(2, horas);
            ps.setInt(3, empleadoId);
            ps.setInt(4, proyectoId);
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina solo la fila de la asignacion; empleado y proyecto permanecen. */
    public boolean eliminar(int empleadoId, int proyectoId) throws SQLException {
        String sql = "DELETE FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Asignacion> listarPorProyecto(int proyectoId) throws SQLException {
        return listar("WHERE ep.proyecto_id = ? ORDER BY e.nombre", proyectoId);
    }

    public List<Asignacion> listarPorEmpleado(int empleadoId) throws SQLException {
        return listar("WHERE ep.empleado_id = ? ORDER BY p.nombre", empleadoId);
    }

    private List<Asignacion> listar(String condicion, int id) throws SQLException {
        List<Asignacion> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(SELECT_JOIN + condicion)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    private Asignacion mapear(ResultSet rs) throws SQLException {
        Asignacion a = new Asignacion(rs.getInt("empleado_id"), rs.getInt("proyecto_id"),
                rs.getString("rol"), rs.getObject("fecha_asignacion", LocalDate.class),
                rs.getDouble("horas_semanales"));
        a.setNombreEmpleado(rs.getString("nombre_empleado"));
        a.setCargoEmpleado(rs.getString("cargo_empleado"));
        a.setNombreProyecto(rs.getString("nombre_proyecto"));
        return a;
    }
}
