package ar.edu.davinci.modelo.estados;

import ar.edu.davinci.modelo.entidades.Mascota;

public abstract class EstadoMascota {
    protected Mascota mascota;

    public EstadoMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public abstract String getCuidados();
    public abstract boolean quiereJugar();
    public abstract String getNombreEstado();
    public abstract void cambiarTemperatura(double nuevaTemperatura);
}