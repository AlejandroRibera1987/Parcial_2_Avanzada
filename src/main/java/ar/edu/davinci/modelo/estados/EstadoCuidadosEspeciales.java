package ar.edu.davinci.modelo.estados;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.managers.NotificacionManager;
import java.util.Random;

public class EstadoCuidadosEspeciales extends EstadoMascota {
    private Random random = new Random();

    public EstadoCuidadosEspeciales(Mascota mascota) {
        super(mascota);
    }

    @Override
    public String getCuidados() {
        return mascota.getRecomendacionesBasicas() + "\n" +
                "CUIDADOS ESPECIALES ADICIONALES: \n" +
                "- Alimento bajo en sodio\n" +
                "- Controlar la hidratación\n" +
                "- Revisar temperatura diariamente";
    }

    @Override
    public boolean quiereJugar() {
        return random.nextBoolean();
    }

    @Override
    public String getNombreEstado() {
        return "Requiere Cuidados Especiales";
    }

    @Override
    public void cambiarTemperatura(double nuevaTemperatura) {
        double temperaturaAnterior = mascota.getTemperatura();
        mascota.setTemperatura(nuevaTemperatura);

        NotificacionManager.getInstance().verificacionCambioDeTemperatura(
                mascota, temperaturaAnterior, nuevaTemperatura);
    }
}