package ar.edu.davinci.modelo.managers;

import ar.edu.davinci.modelo.entidades.Adopcion;
import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import java.util.ArrayList;
import java.util.List;

public class AdopcionesList {

    private static AdopcionesList adopcionesList;
    private List<Adopcion> adopciones;

    public AdopcionesList() {
        this.adopciones = new ArrayList<>();
    }

    public static AdopcionesList getInstance() {
        if (adopcionesList == null) {
            adopcionesList = new AdopcionesList();
        }
        return adopcionesList;
    }

    public Adopcion crearAdopcion(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        Adopcion nuevaAdopcion = new Adopcion(mascota, adoptante, empleado);
        adopciones.add(nuevaAdopcion);
        return nuevaAdopcion;
    }

    public List<Adopcion> getAdopciones() {
        return adopciones;
    }

    public Adopcion buscarAdopcionPorId(int id) {
        return adopciones.stream()
                .filter(a -> a.getNumeroAdopcion() == id)
                .findFirst()
                .orElse(null);
    }
}
