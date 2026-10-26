package com.mycompany.taller.base.datos.controller;

import com.mycompany.taller.base.datos.dao.EmpleadoDAO;
import com.mycompany.taller.base.datos.model.Empleado;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoController {

    private static final String PATRON_CORREO = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final EmpleadoDAO dao = new EmpleadoDAO();

    public Empleado registrar(String nombre, String correo, String cargo) {
        Empleado e = new Empleado(Validador.texto(nombre, "nombre"),
                validarCorreo(correo), Validador.texto(cargo, "cargo"));
        try {
            if (dao.buscarPorCorreo(e.getCorreo()) != null) {
                throw new ValidacionException("Ya existe un empleado con ese correo.");
            }
            e.setId(dao.insertar(e));
            return e;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public List<Empleado> listar() {
        try {
            return dao.listar();
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public Empleado buscarPorId(int id) {
        Validador.id(id, "ID de empleado");
        try {
            Empleado e = dao.buscarPorId(id);
            if (e == null) {
                throw new ValidacionException("No existe un empleado con ID " + id + ".");
            }
            return e;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public Empleado actualizar(int id, String nombre, String correo, String cargo) {
        buscarPorId(id);
        Empleado e = new Empleado(id, Validador.texto(nombre, "nombre"),
                validarCorreo(correo), Validador.texto(cargo, "cargo"));
        try {
            Empleado conMismoCorreo = dao.buscarPorCorreo(e.getCorreo());
            if (conMismoCorreo != null && conMismoCorreo.getId() != id) {
                throw new ValidacionException("Ya existe otro empleado con ese correo.");
            }
            dao.actualizar(e);
            return e;
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    public void eliminar(int id) {
        buscarPorId(id);
        try {
            if (dao.tieneAsignaciones(id)) {
                throw new ValidacionException(
                        "No se puede eliminar: el empleado tiene asignaciones activas. Retirelo primero de sus proyectos.");
            }
            dao.eliminar(id);
        } catch (SQLException ex) {
            throw Validador.errorBD(ex);
        }
    }

    private String validarCorreo(String correo) {
        String c = Validador.texto(correo, "correo");
        if (!c.matches(PATRON_CORREO)) {
            throw new ValidacionException("El correo no tiene un formato valido.");
        }
        return c;
    }
}
