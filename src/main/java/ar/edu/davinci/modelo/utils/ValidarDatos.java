package ar.edu.davinci.modelo.utils;

import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.estados.EstadoObservacion;
import ar.edu.davinci.modelo.exceptions.DatosIncorrectosException;

public class ValidarDatos {

    public static void validarAdoptante(Adoptante adoptante) throws DatosIncorrectosException {
        if (adoptante == null) {
            throw new DatosIncorrectosException("El adoptante no puede ser nulo");
        }

        if (adoptante.getNombre() == null || adoptante.getNombre().trim().isEmpty()) {
            throw new DatosIncorrectosException("El nombre del adoptante es obligatorio");
        }

        if (adoptante.getApellido() == null || adoptante.getApellido().trim().isEmpty()) {
            throw new DatosIncorrectosException("El apellido del adoptante es obligatorio");
        }

        if (adoptante.getDireccion() == null || adoptante.getDireccion().trim().isEmpty()) {
            throw new DatosIncorrectosException("La dirección del adoptante es obligatoria");
        }

        if (adoptante.getEdad() < 18) {
            throw new DatosIncorrectosException("El adoptante debe ser mayor de edad (18 años)");
        }

        if (adoptante.getEdad() > 100) {
            throw new DatosIncorrectosException("Edad del adoptante no válida");
        }
    }

    public static void validarMascota(Mascota mascota) throws DatosIncorrectosException {
        if (mascota == null) {
            throw new DatosIncorrectosException("La mascota no puede ser nula");
        }

        if (mascota.getEstado() instanceof EstadoObservacion) {
            throw new DatosIncorrectosException("La mascota " + mascota.getNombre() +
                    " está en observación y no puede ser adoptada");
        }

        if (mascota.getTemperatura() > 39.0 || mascota.getTemperatura() < 35.0) {
            throw new DatosIncorrectosException("La mascota " + mascota.getNombre() +
                    " tiene temperatura anormal (" + mascota.getTemperatura() + "°C) y no puede ser adoptada");
        }

        if (!mascota.isDisponible()) {
            throw new DatosIncorrectosException("La mascota " + mascota.getNombre() +
                    " no está disponible para adopción");
        }
    }

    public static void validarEmpleado(Empleado empleado) throws DatosIncorrectosException {
        if (empleado == null) {
            throw new DatosIncorrectosException("El empleado no puede ser nulo");
        }

        if (empleado.getIdEmpleado() <= 0) {
            throw new DatosIncorrectosException("ID del empleado no válido");
        }

        if (!empleado.isActivo()) {
            throw new DatosIncorrectosException("El empleado no está activo");
        }
    }
}