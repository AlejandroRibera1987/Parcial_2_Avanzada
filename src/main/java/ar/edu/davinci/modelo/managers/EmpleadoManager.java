package ar.edu.davinci.modelo.managers;

import ar.edu.davinci.modelo.entidades.Empleado;

public class EmpleadoManager {
    private static EmpleadoManager instance;
    private Empleado empleadoLogueado;


    public static EmpleadoManager getInstance() {
        if (instance == null) {
            instance = new EmpleadoManager();
        }
        return instance;
    }

    public void setEmpleadoLogueado(Empleado empleado) {
        this.empleadoLogueado = empleado;
        System.out.println("Empleado logueado: " + empleado.getNombre() + " " + empleado.getApellido());
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public boolean hayEmpleadoLogueado() {
        return empleadoLogueado != null;
    }

    public void cerrarSesion() {
        if (empleadoLogueado != null) {
            System.out.println("Cerrando sesión de: " + empleadoLogueado.getNombre() + " " + empleadoLogueado.getApellido());
            empleadoLogueado = null;
        }
    }

    public String getInfoEmpleadoLogueado() {
        if (empleadoLogueado != null) {
            return empleadoLogueado.getNombre() + " " + empleadoLogueado.getApellido() +
                    " (ID: " + empleadoLogueado.getIdEmpleado() + ")";
        }
        return "No hay empleado logueado";
    }
}