package ar.edu.davinci.modelo.estados;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.managers.NotificacionManager;

public class EstadoObservacion extends EstadoMascota {

    public EstadoObservacion(Mascota mascota) {
        super(mascota);
    }

    @Override
    public String getCuidados() {
        return mascota.getRecomendacionesBasicas() + "\n" +
                "CUIDADOS ESPECIALES ADICIONALES: \n" +
                "- Alimento bajo en sodio\n" +
                "- Controlar la hidratación\n" +
                "- Revisar temperatura diariamente\n" +
                "CUIDADOS DE OBSERVACIÓN:\n" +
                "- Revisar constantemente\n" +
                "- Registro de síntomas\n" +
                "- Contacto inmediato con el veterinario";
    }

    @Override
    public boolean quiereJugar() {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "En Observacion";
    }

    @Override
    public void cambiarTemperatura(double nuevaTemperatura) {
        double temperaturaAnterior = mascota.getTemperatura();
        mascota.setTemperatura(nuevaTemperatura);

        System.out.println("Temperatura anterior: " + temperaturaAnterior);
        System.out.println("Temperatura nueva: " + nuevaTemperatura);

        NotificacionManager.getInstance().verificacionCambioDeTemperatura(
                mascota, temperaturaAnterior, nuevaTemperatura);
    }
}