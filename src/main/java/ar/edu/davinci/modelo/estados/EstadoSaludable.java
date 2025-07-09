package ar.edu.davinci.modelo.estados;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.managers.NotificacionManager;

public class EstadoSaludable extends EstadoMascota {

    public EstadoSaludable(Mascota mascota) {
        super(mascota);
    }

    @Override
    public String getCuidados() {
        return mascota.getRecomendacionesBasicas();
    }

    @Override
    public boolean quiereJugar() {
        return true;
    }

    @Override
    public String getNombreEstado() {
        return "Saludable";
    }

    @Override
    public void cambiarTemperatura(double nuevaTemperatura) {
        double temperaturaAnterior = mascota.getTemperatura();
        mascota.setTemperatura(nuevaTemperatura);

        // Solo notificar si hay cambio significativo
        if (Math.abs(nuevaTemperatura - temperaturaAnterior) > 0.5) {
            NotificacionManager.getInstance().verificacionCambioDeTemperatura(
                    mascota, temperaturaAnterior, nuevaTemperatura);
        }
    }
}
