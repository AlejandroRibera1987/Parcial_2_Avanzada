package ar.edu.davinci.modelo.managers;

import ar.edu.davinci.modelo.entidades.Veterinario;
import ar.edu.davinci.modelo.entidades.Mascota;
import java.util.ArrayList;
import java.util.List;

public class NotificacionManager {
    private static NotificacionManager instance;
    private List<Veterinario> veterinarios;

    private NotificacionManager() {
        veterinarios = new ArrayList<>();
    }

    public static NotificacionManager getInstance() {
        if (instance == null) {
            instance = new NotificacionManager();
        }
        return instance;
    }

    public void agregarVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
    }

    public void verificacionCambioDeTemperatura(Mascota mascota, double temperaturaAnterior, double temperaturaNueva) {
        System.out.println("Notificacion de Cambio de temperatura");
        for (Veterinario v : veterinarios) {
            v.notificarCambioDeTemperatura(mascota, temperaturaAnterior, temperaturaNueva);
        }
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

}
