package com.mycompany.taller.base.datos.controller;

import com.mycompany.taller.base.datos.dao.ProyectoDAO;
import com.mycompany.taller.base.datos.model.Proyecto;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ProyectoController {

    public static final Set<String> ESTADOS = Set.of("PLANIFICADO", "EN_CURSO", "FINALIZADO");

    private final ProyectoDAO dao = new ProyectoDAO();

    public Proyecto registrar(String nombre, String descripcion, LocalDate inicio,
            LocalDate fin, String estado) {
        Proyecto p = construir(0, nombre, descripcion, inicio, fin, estado);
        try {
            p.setId(dao.insertar(p));
            return p;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public List<Proyecto> listar() {
        try {
            return dao.listar();
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public Proyecto buscarPorId(int id) {
        Validador.id(id, "ID de proyecto");
        try {
            Proyecto p = dao.buscarPorId(id);
            if (p == null) {
                throw new ValidacionException("No existe un proyecto con ID " + id + ".");
            }
            return p;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public Proyecto actualizar(int id, String nombre, String descripcion, LocalDate inicio,
            LocalDate fin, String estado) {
        buscarPorId(id);
        Proyecto p = construir(id, nombre, descripcion, inicio, fin, estado);
        try {
            dao.actualizar(p);
            return p;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public void eliminar(int id) {
        buscarPorId(id);
        try {
            if (dao.tieneAsignaciones(id)) {
                throw new ValidacionException(
                        "No se puede eliminar: el proyecto tiene empleados asignados. Retirelos primero.");
            }
            dao.eliminar(id);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    private Proyecto construir(int id, String nombre, String descripcion, LocalDate inicio,
            LocalDate fin, String estado) {
        String n = Validador.texto(nombre, "nombre");
        if (inicio == null) {
            throw new ValidacionException("La fecha de inicio es obligatoria.");
        }
        if (fin != null && fin.isBefore(inicio)) {
            throw new ValidacionException("La fecha de finalizacion no puede ser anterior a la fecha de inicio.");
        }
        String e = Validador.texto(estado, "estado").toUpperCase();
        if (!ESTADOS.contains(e)) {
            throw new ValidacionException("El estado debe ser PLANIFICADO, EN_CURSO o FINALIZADO.");
        }
        String d = (descripcion == null || descripcion.trim().isEmpty()) ? null : descripcion.trim();
        return new Proyecto(id, n, d, inicio, fin, e);
    }
}
