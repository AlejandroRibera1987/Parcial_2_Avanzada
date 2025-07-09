package ar.edu.davinci.modelo.factory;

import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Perro;

public class AdopcionPerro extends ProcesarAdopcion{

    public AdopcionPerro(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        super(mascota, adoptante, empleado);

        if (!(mascota instanceof Perro)){
            throw new IllegalArgumentException("La mascota tiene que ser un Perro");
        }
    }

    public String verificarRequisitos(){
        StringBuilder verificarRequisitos = new StringBuilder();

        verificarRequisitos.append("Verificando Requisitos para el Perro\n");
        verificarRequisitos.append("Vacuna Antirrabica\n");
        verificarRequisitos.append("Vacuna Moquillo\n");
        verificarRequisitos.append("Antiparacitario\n");
        verificarRequisitos.append("Pipeta para Pulgas\n");

        if(mascota.getPeso() > 20){
            verificarRequisitos.append("Perro Pesado, necesita espacio para correr");
        }

        return verificarRequisitos.toString();
    }

    @Override
    protected String ofrecerRecomendaciones() {
        StringBuilder ofrecerRecomendaciones = new StringBuilder();

        ofrecerRecomendaciones.append("Recomendacion del Perro\n");
        ofrecerRecomendaciones.append("Alimento Balanceado\n");
        ofrecerRecomendaciones.append("Cucha con colcha\n");
        ofrecerRecomendaciones.append("Agua frasca\n");
        ofrecerRecomendaciones.append("Visitar al Veterinario cada 6 Meses\n");

        if (mascota.getPeso() > 20){
            ofrecerRecomendaciones.append("Requiere ejercicio intenso");
        }else if (mascota.getPeso() < 5){
            ofrecerRecomendaciones.append("Raza pequeña");
        }

        return ofrecerRecomendaciones.toString();
    }

    @Override
    protected String entregarAccesorios() {
        StringBuilder entregarAccesorios = new StringBuilder();

        entregarAccesorios.append("Accesorios del Perro\n");
        entregarAccesorios.append("Correa con identificacion\n");
        entregarAccesorios.append("Pelota de Goma\n");
        entregarAccesorios.append("Cuenco para agua y alimento\n");

        return entregarAccesorios.toString();
    }
}
