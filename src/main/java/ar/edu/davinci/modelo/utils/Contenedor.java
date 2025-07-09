package ar.edu.davinci.modelo.utils;

import ar.edu.davinci.modelo.entidades.Mascota;

public class Contenedor<T> {
    private T contenido;
    private String tipo;
    private boolean vacio;

    public Contenedor() {
        this.vacio = true;
        this.tipo = "Vacío";
    }

    public Contenedor(T contenido) {
        this.contenido = contenido;
        this.vacio = false;
        this.tipo = contenido != null ? contenido.getClass().getSimpleName() : "Nulo";
    }

    public void almacenar(T item) {
        this.contenido = item;
        this.vacio = false;
        this.tipo = item != null ? item.getClass().getSimpleName() : "Nulo";
    }

    public T obtener() {
        return contenido;
    }

    public boolean esVacio() {
        return vacio || contenido == null;
    }

    public String getTipo() {
        return tipo;
    }

    public void vaciar() {
        this.contenido = null;
        this.vacio = true;
        this.tipo = "Vacío";
    }

    public boolean tieneMascota() {
        return !esVacio() && contenido instanceof Mascota;
    }

    public boolean tieneCosa() {
        return !esVacio() && !(contenido instanceof Mascota);
    }

    @Override
    public String toString() {
        if (esVacio()) {
            return "Contenedor vacío";
        }
        return "Contenedor con " + tipo + ": " + contenido.toString();
    }
}