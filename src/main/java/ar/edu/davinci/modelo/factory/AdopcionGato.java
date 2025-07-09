package ar.edu.davinci.modelo.factory;

import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Gato;
import ar.edu.davinci.modelo.entidades.Mascota;

public class AdopcionGato extends ProcesarAdopcion{

    public AdopcionGato(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        super(mascota, adoptante, empleado);

        if (!(mascota instanceof Gato)){
            throw new IllegalArgumentException("La mascota tiene que ser un Gato");
        }
    }

    @Override
    protected String verificarRequisitos() {
        StringBuilder requisitos = new StringBuilder();

        requisitos.append("Verificando Requisitos para el Gato\n");
        requisitos.append("Vacuna Felina");
        requisitos.append("Antiparacitario");
        requisitos.append("Vacuna contra la rabia");
        requisitos.append("Castracion");

        return requisitos.toString();
    }

    @Override
    protected String ofrecerRecomendaciones() {
        StringBuilder recomendaciones = new StringBuilder();

        recomendaciones.append("Recomendaciones para el Gato\n");
        recomendaciones.append("Alimento Balanceado\n");
        recomendaciones.append("Agua fresca\n");
        recomendaciones.append("Juegos en Altura\n");

        return recomendaciones.toString();

    }

    @Override
    protected String entregarAccesorios() {
        StringBuilder accesorios = new StringBuilder();

        accesorios.append("Accesorios del Gato\n");
        accesorios.append("Plato para comida y agua\n");
        accesorios.append("Corta uñas\n");
        accesorios.append("Cepillo para pelo\n");

        return accesorios.toString();
    }
}
