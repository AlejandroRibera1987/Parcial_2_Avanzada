package ar.edu.davinci.modelo.factory;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Gato;
import ar.edu.davinci.modelo.entidades.Perro;

public class AdopcionFactory {


    public static ProcesarAdopcion crearAdopcion(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        if (mascota instanceof Gato) {
            return new AdopcionGato(mascota, adoptante, empleado);
        } else if (mascota instanceof Perro) {
            return new AdopcionPerro(mascota, adoptante, empleado);
        } else {
            throw new IllegalArgumentException("El tipo de mascota no es correcto. " +
                    "Debe ser Gato o Perro, pero se recibio: " + mascota.getClass().getSimpleName());
        }
    }


    public static String procesarAdopcionCompleta(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        try {
            ProcesarAdopcion adopcion = crearAdopcion(mascota, adoptante, empleado);
            return adopcion.procesarAdopcion();
        } catch (IllegalArgumentException e) {
            String error = "Error en el tipo de mascota: " + e.getMessage();
            System.err.println(error);
            return error;
        } catch (Exception e) {
            String error = "Error al procesar la adopcion: " + e.getMessage();
            System.err.println(error);
            e.printStackTrace();
            return error;
        }
    }


    public static boolean esTipoMascotaValido(Mascota mascota) {
        return mascota instanceof Gato || mascota instanceof Perro;
    }

    public static String[] getTiposMascotaSoportados() {
        return new String[]{"Gato", "Perro"};
    }

    public static String getInfoTiposAdopcion() {
        return "Tipos de adopcion disponibles:\n" +
                "• Gatos: Proceso especializado para felinos\n" +
                "• Perros: Proceso especializado para caninos\n" +
                "Cada tipo incluye validaciones y recomendaciones específicas.";
    }
}