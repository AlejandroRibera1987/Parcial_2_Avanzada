package ar.edu.davinci.modelo.managers;

import ar.edu.davinci.modelo.utils.Contenedor;
import java.util.ArrayList;
import java.util.List;

public class GestorContenedores {
    private static GestorContenedores instance;
    private List<Contenedor<?>> contenedores;

    private GestorContenedores() {
        contenedores = new ArrayList<>();
    }

    public static GestorContenedores getInstance() {
        if (instance == null) {
            instance = new GestorContenedores();
        }
        return instance;
    }

    public <T> Contenedor<T> crearContenedor() {
        Contenedor<T> nuevoContenedor = new Contenedor<>();
        contenedores.add(nuevoContenedor);
        return nuevoContenedor;
    }

    public <T> Contenedor<T> crearContenedor(T contenidoInicial) {
        Contenedor<T> nuevoContenedor = new Contenedor<>(contenidoInicial);
        contenedores.add(nuevoContenedor);
        return nuevoContenedor;
    }

    public void mostrarEstadoContenedores() {
        System.out.println("\n=== Estado de Contenedores ===");
        if (contenedores.isEmpty()) {
            System.out.println("No hay contenedores creados.");
        } else {
            for (int i = 0; i < contenedores.size(); i++) {
                Contenedor<?> contenedor = contenedores.get(i);
                System.out.println("Contenedor " + (i + 1) + ": " + contenedor.toString());
            }
        }
        System.out.println("===============================");
    }

    public int getTotalContenedores() {
        return contenedores.size();
    }

    public long getContenedoresConMascotas() {
        return contenedores.stream()
                .filter(c -> c.tieneMascota())
                .count();
    }

    public long getContenedoresConCosas() {
        return contenedores.stream()
                .filter(c -> c.tieneCosa())
                .count();
    }

    public long getContenedoresVacios() {
        return contenedores.stream()
                .filter(c -> c.esVacio())
                .count();
    }


    public void limpiarTodosLosContenedores() {
        contenedores.clear();
        System.out.println("Todos los contenedores han sido eliminados.");
    }


    public String getEstadisticas() {
        return String.format("""
            ESTADÍSTICAS DE CONTENEDORES:
            • Total de contenedores: %d
            • Contenedores con mascotas: %d
            • Contenedores con otros objetos: %d
            • Contenedores vacíos: %d
            """,
                getTotalContenedores(),
                getContenedoresConMascotas(),
                getContenedoresConCosas(),
                getContenedoresVacios()
        );
    }

    public List<Contenedor<?>> getContenedores() {
        return new ArrayList<>(contenedores);
    }
}