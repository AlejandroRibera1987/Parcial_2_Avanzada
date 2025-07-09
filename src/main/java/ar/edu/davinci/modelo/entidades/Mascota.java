package ar.edu.davinci.modelo.entidades;

import ar.edu.davinci.modelo.estados.EstadoMascota;
import ar.edu.davinci.modelo.estados.EstadoSaludable;
import ar.edu.davinci.modelo.estados.EstadoCuidadosEspeciales;
import ar.edu.davinci.modelo.estados.EstadoObservacion;

public abstract class Mascota implements Cuidados {
    protected int idMascota;
    protected String nombre;
    protected String fechaNacimiento;
    protected double peso;
    protected String raza;
    protected double temperatura = 36.5;
    protected EstadoMascota estado;
    protected boolean disponible = true;

    public Mascota(String nombre, String fechaNacimiento, double peso, String raza) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.raza = raza;
        this.estado = new EstadoSaludable(this);
    }

    public Mascota(int idMascota, String nombre, String fechaNacimiento, double peso, String raza, double temperatura, String estadoNombre) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.raza = raza;
        this.temperatura = temperatura;
        this.disponible = true;

        switch (estadoNombre) {
            case "CUIDADOS_ESPECIALES":
            case "Cuidados Especiales":
            case "Requiere Cuidados Especiales":
                this.estado = new EstadoCuidadosEspeciales(this);
                break;
            case "OBSERVACION":
            case "En Observacion":
            case "Observación":
                this.estado = new EstadoObservacion(this);
                break;
            case "SALUDABLE":
            case "Saludable":
            default:
                this.estado = new EstadoSaludable(this);
        }
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public EstadoMascota getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoMascota estado) {
        this.estado = estado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    public void cambiarEstadoSaludable() {
        cambiarEstado(new EstadoSaludable(this));
    }

    public void cambiarEstadoCuidadosEspeciales() {
        cambiarEstado(new EstadoCuidadosEspeciales(this));
    }

    public void cambiarEstadoObservacion() {
        cambiarEstado(new EstadoObservacion(this));
    }

    public void cambiarTemperatura(double nuevaTemperatura) {
        estado.cambiarTemperatura(nuevaTemperatura);
    }

    public boolean quiereJugar() {
        return estado.quiereJugar();
    }

    public abstract String getEspecie();

    @Override
    public String getRecomendaciones() {
        return "Estado Actual: " + estado.getNombreEstado() + "\n" +
                "Temperatura: " + temperatura + "°C\n" +
                "Quiere jugar: " + (quiereJugar() ? "Sí" : "No") + "\n" +
                estado.getCuidados();
    }

    @Override
    public String toString() {
        return nombre + " (" + getEspecie() + " - " + raza + ")";
    }
}
