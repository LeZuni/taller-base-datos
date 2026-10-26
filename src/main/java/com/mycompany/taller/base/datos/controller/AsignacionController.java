package com.mycompany.taller.base.datos.controller;

import com.mycompany.taller.base.datos.dao.AsignacionDAO;
import com.mycompany.taller.base.datos.model.Asignacion;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AsignacionController {

    private static final double HORAS_MAX = 40;

    private final AsignacionDAO dao = new AsignacionDAO();
    private final EmpleadoController empleados = new EmpleadoController();
    private final ProyectoController proyectos = new ProyectoController();

    /** RF-07. Si no se indica fecha se usa la de hoy. */
    public Asignacion asignar(int empleadoId, int proyectoId, String rol,
            LocalDate fecha, double horas) {
        // Lanzan ValidacionException si los IDs son invalidos o no existen
        empleados.buscarPorId(empleadoId);
        proyectos.buscarPorId(proyectoId);
        String r = Validador.texto(rol, "rol");
        validarHoras(horas);
        Asignacion a = new Asignacion(empleadoId, proyectoId, r,
                fecha != null ? fecha : LocalDate.now(), horas);
        try {
            if (dao.buscar(empleadoId, proyectoId) != null) {
                throw new ValidacionException("El empleado ya esta asignado a ese proyecto.");
            }
            dao.insertar(a);
            return dao.buscar(empleadoId, proyectoId);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    /** RF-08 */
    public List<Asignacion> integrantesDeProyecto(int proyectoId) {
        proyectos.buscarPorId(proyectoId);
        try {
            return dao.listarPorProyecto(proyectoId);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    /** RF-09 */
    public List<Asignacion> proyectosDeEmpleado(int empleadoId) {
        empleados.buscarPorId(empleadoId);
        try {
            return dao.listarPorEmpleado(empleadoId);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    /** RF-10: cambia rol y horas sin duplicar la asignacion. */
    public Asignacion modificar(int empleadoId, int proyectoId, String rol, double horas) {
        Validador.id(empleadoId, "ID de empleado");
        Validador.id(proyectoId, "ID de proyecto");
        String r = Validador.texto(rol, "rol");
        validarHoras(horas);
        try {
            if (!dao.actualizar(empleadoId, proyectoId, r, horas)) {
                throw new ValidacionException("La asignacion indicada no existe.");
            }
            return dao.buscar(empleadoId, proyectoId);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    /** RF-11: elimina solo la asignacion. */
    public void retirar(int empleadoId, int proyectoId) {
        Validador.id(empleadoId, "ID de empleado");
        Validador.id(proyectoId, "ID de proyecto");
        try {
            if (!dao.eliminar(empleadoId, proyectoId)) {
                throw new ValidacionException("La asignacion indicada no existe.");
            }
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    private void validarHoras(double horas) {
        if (Double.isNaN(horas) || horas <= 0 || horas > HORAS_MAX) {
            throw new ValidacionException("Las horas semanales deben ser mayores que 0 y no superar " + (int) HORAS_MAX + ".");
        }
    }
}
