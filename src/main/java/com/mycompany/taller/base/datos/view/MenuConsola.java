package com.mycompany.taller.base.datos.view;

import com.mycompany.taller.base.datos.controller.AsignacionController;
import com.mycompany.taller.base.datos.controller.EmpleadoController;
import com.mycompany.taller.base.datos.controller.ProyectoController;
import com.mycompany.taller.base.datos.controller.ValidacionException;
import com.mycompany.taller.base.datos.model.Asignacion;
import com.mycompany.taller.base.datos.model.Empleado;
import com.mycompany.taller.base.datos.model.Proyecto;
import java.time.LocalDate;
import java.util.List;

public class MenuConsola {

    private final Entrada in = new Entrada();
    private final EmpleadoController empleados = new EmpleadoController();
    private final ProyectoController proyectos = new ProyectoController();
    private final AsignacionController asignaciones = new AsignacionController();

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n===== GESTION DE PROYECTOS EMPRESARIALES =====");
            System.out.println("1. Empleados");
            System.out.println("2. Proyectos");
            System.out.println("3. Asignaciones");
            System.out.println("0. Salir");
            switch (in.entero("Opcion: ")) {
                case 1 -> menuEmpleados();
                case 2 -> menuProyectos();
                case 3 -> menuAsignaciones();
                case 0 -> salir = true;
                default -> System.out.println("Opcion no valida.");
            }
        }
        System.out.println("Hasta luego.");
    }

    // ---------- EMPLEADOS ----------

    private void menuEmpleados() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Buscar empleado por ID");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("0. Volver");
            int op = in.entero("Opcion: ");
            if (op == 0) {
                volver = true;
                continue;
            }
            ejecutar(() -> {
                switch (op) {
                    case 1 -> {
                        Empleado e = empleados.registrar(in.texto("Nombre: "),
                                in.texto("Correo: "), in.texto("Cargo: "));
                        System.out.println("Empleado registrado: " + e);
                    }
                    case 2 -> imprimir(empleados.listar());
                    case 3 -> System.out.println(empleados.buscarPorId(in.entero("ID: ")));
                    case 4 -> {
                        int id = in.entero("ID del empleado a actualizar: ");
                        System.out.println("Actual: " + empleados.buscarPorId(id));
                        Empleado e = empleados.actualizar(id, in.texto("Nuevo nombre: "),
                                in.texto("Nuevo correo: "), in.texto("Nuevo cargo: "));
                        System.out.println("Empleado actualizado: " + e);
                    }
                    case 5 -> {
                        empleados.eliminar(in.entero("ID del empleado a eliminar: "));
                        System.out.println("Empleado eliminado.");
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            });
        }
    }

    // ---------- PROYECTOS ----------

    private void menuProyectos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PROYECTOS ---");
            System.out.println("1. Registrar proyecto");
            System.out.println("2. Listar proyectos");
            System.out.println("3. Buscar proyecto por ID");
            System.out.println("4. Actualizar proyecto");
            System.out.println("5. Eliminar proyecto");
            System.out.println("0. Volver");
            int op = in.entero("Opcion: ");
            if (op == 0) {
                volver = true;
                continue;
            }
            ejecutar(() -> {
                switch (op) {
                    case 1 -> {
                        Proyecto p = leerProyecto(0);
                        System.out.println("Proyecto registrado: " + p);
                    }
                    case 2 -> imprimir(proyectos.listar());
                    case 3 -> System.out.println(proyectos.buscarPorId(in.entero("ID: ")));
                    case 4 -> {
                        int id = in.entero("ID del proyecto a actualizar: ");
                        System.out.println("Actual: " + proyectos.buscarPorId(id));
                        Proyecto p = leerProyecto(id);
                        System.out.println("Proyecto actualizado: " + p);
                    }
                    case 5 -> {
                        proyectos.eliminar(in.entero("ID del proyecto a eliminar: "));
                        System.out.println("Proyecto eliminado.");
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            });
        }
    }

    /** Pide los datos de un proyecto; con id 0 registra, con id > 0 actualiza. */
    private Proyecto leerProyecto(int id) {
        String nombre = in.texto("Nombre: ");
        String descripcion = in.texto("Descripcion (opcional): ");
        LocalDate inicio = in.fecha("Fecha de inicio (AAAA-MM-DD): ");
        LocalDate fin = in.fecha("Fecha de fin (AAAA-MM-DD, vacio si no tiene): ");
        String estado = in.texto("Estado (PLANIFICADO / EN_CURSO / FINALIZADO): ");
        return id == 0
                ? proyectos.registrar(nombre, descripcion, inicio, fin, estado)
                : proyectos.actualizar(id, nombre, descripcion, inicio, fin, estado);
    }

    // ---------- ASIGNACIONES ----------

    private void menuAsignaciones() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- ASIGNACIONES ---");
            System.out.println("1. Asignar empleado a proyecto");
            System.out.println("2. Consultar integrantes de un proyecto");
            System.out.println("3. Consultar proyectos de un empleado");
            System.out.println("4. Modificar asignacion (rol y horas)");
            System.out.println("5. Retirar empleado de un proyecto");
            System.out.println("0. Volver");
            int op = in.entero("Opcion: ");
            if (op == 0) {
                volver = true;
                continue;
            }
            ejecutar(() -> {
                switch (op) {
                    case 1 -> {
                        int emp = in.entero("ID del empleado: ");
                        int proy = in.entero("ID del proyecto: ");
                        String rol = in.texto("Rol: ");
                        LocalDate fecha = in.fecha("Fecha de asignacion (AAAA-MM-DD, vacio = hoy): ");
                        double horas = in.decimal("Horas semanales (1-40): ");
                        Asignacion a = asignaciones.asignar(emp, proy, rol, fecha, horas);
                        System.out.println("Asignacion registrada: " + a);
                    }
                    case 2 -> {
                        int proy = in.entero("ID del proyecto: ");
                        System.out.println("Proyecto: " + proyectos.buscarPorId(proy));
                        List<Asignacion> lista = asignaciones.integrantesDeProyecto(proy);
                        if (lista.isEmpty()) {
                            System.out.println("El proyecto no tiene integrantes.");
                        }
                        for (Asignacion a : lista) {
                            System.out.printf("  %s (%s) | Rol: %s | %.1f h/sem%n", a.getNombreEmpleado(),
                                    a.getCargoEmpleado(), a.getRol(), a.getHorasSemanales());
                        }
                    }
                    case 3 -> {
                        int emp = in.entero("ID del empleado: ");
                        System.out.println("Empleado: " + empleados.buscarPorId(emp));
                        List<Asignacion> lista = asignaciones.proyectosDeEmpleado(emp);
                        if (lista.isEmpty()) {
                            System.out.println("El empleado no tiene proyectos asignados.");
                        }
                        for (Asignacion a : lista) {
                            System.out.printf("  %s | Rol: %s | %.1f h/sem%n", a.getNombreProyecto(),
                                    a.getRol(), a.getHorasSemanales());
                        }
                    }
                    case 4 -> {
                        int emp = in.entero("ID del empleado: ");
                        int proy = in.entero("ID del proyecto: ");
                        String rol = in.texto("Nuevo rol: ");
                        double horas = in.decimal("Nuevas horas semanales (1-40): ");
                        Asignacion a = asignaciones.modificar(emp, proy, rol, horas);
                        System.out.println("Asignacion modificada: " + a);
                    }
                    case 5 -> {
                        asignaciones.retirar(in.entero("ID del empleado: "), in.entero("ID del proyecto: "));
                        System.out.println("Empleado retirado del proyecto.");
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            });
        }
    }

    // ---------- Utilidades ----------

    private void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (ValidacionException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private <T> void imprimir(List<T> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay registros.");
        }
        lista.forEach(System.out::println);
    }
}
