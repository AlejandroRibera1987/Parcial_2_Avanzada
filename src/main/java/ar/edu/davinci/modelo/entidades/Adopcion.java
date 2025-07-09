package ar.edu.davinci.modelo.entidades;

public class Adopcion {

    private static int contadorAdopcion = 0;
    private int numeroAdopcion;
    private Mascota mascota;
    private Adoptante adoptante;
    private Empleado empleado;
    private String fecha;

    public Adopcion(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        this.numeroAdopcion = ++contadorAdopcion;
        this.mascota = mascota;
        this.adoptante = adoptante;
        this.empleado = empleado;
        this.fecha = java.time.LocalDateTime.now().toString();
    }

    public int getNumeroAdopcion() {
        return numeroAdopcion;
    }

    public void setNumeroAdopcion(int numeroAdopcion) {
        this.numeroAdopcion = numeroAdopcion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Adoptante getAdoptante() {
        return adoptante;
    }

    public void setAdoptante(Adoptante adoptante) {
        this.adoptante = adoptante;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Adopcion #" + numeroAdopcion + " - " + mascota.getNombre() +
                " adoptado por " + adoptante.getNombre() + " " + adoptante.getApellido();
    }
}