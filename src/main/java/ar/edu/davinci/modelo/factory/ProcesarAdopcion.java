package ar.edu.davinci.modelo.factory;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Adopcion;

import ar.edu.davinci.modelo.utils.ValidarDatos;
import ar.edu.davinci.modelo.managers.AdopcionesList;
import ar.edu.davinci.modelo.utils.ImprimirTicket;
import ar.edu.davinci.modelo.exceptions.DatosIncorrectosException;

public abstract class ProcesarAdopcion {
    protected Mascota mascota;
    protected Adoptante adoptante;
    protected Empleado empleado;
    protected Adopcion adopcion;

    public ProcesarAdopcion(Mascota mascota, Adoptante adoptante, Empleado empleado) {
        this.mascota = mascota;
        this.adoptante = adoptante;
        this.empleado = empleado;
    }

    public final String procesarAdopcion() {
        try {
            ValidarDatos.validarAdoptante(adoptante);
            ValidarDatos.validarMascota(mascota);
            ValidarDatos.validarEmpleado(empleado);

            StringBuilder resultado = new StringBuilder();

            resultado.append("=== Procesando adopcion ===\n");
            resultado.append(verificarRequisitos()).append("\n");
            resultado.append(ofrecerRecomendaciones()).append("\n");
            resultado.append(entregarAccesorios()).append("\n");

            AdopcionesList lista = AdopcionesList.getInstance();
            this.adopcion = lista.crearAdopcion(mascota, adoptante, empleado);

            ImprimirTicket ticket = new ImprimirTicket(adopcion);
            ticket.imprimirTicket();

            resultado.append("=== Adopcion completada correctamente ===\n");
            resultado.append("Numero de adopcion: ").append(adopcion.getNumeroAdopcion()).append("\n");

            return resultado.toString();

        } catch (DatosIncorrectosException e) {
            String error = "Error de validación: " + e.getMessage();
            System.out.println(error);
            return error;
        } catch (Exception e) {
            String error = "Error inesperado: " + e.getMessage();
            System.out.println(error);
            e.printStackTrace();
            return error;
        }
    }


    protected abstract String verificarRequisitos();
    protected abstract String ofrecerRecomendaciones();
    protected abstract String entregarAccesorios();


    protected String registrarDatosAdoptante() {
        return "Datos del adoptante: \n" +
                "Nombre: " + adoptante.getNombre() + "\n" +
                "Apellido: " + adoptante.getApellido() + "\n" +
                "Edad: " + adoptante.getEdad() + "\n" +
                "Direccion: " + adoptante.getDireccion();
    }

    protected String registrarDatosEmpleado() {
        return "Empleado responsable: \n" +
                "Nombre: " + empleado.getNombre() + "\n" +
                "Apellido: " + empleado.getApellido() + "\n" +
                "ID: " + empleado.getIdEmpleado();
    }

    protected String guardarDatosMascota() {
        return "Mascota adoptada: \n" +
                "Nombre: " + mascota.getNombre() + "\n" +
                "Especie: " + mascota.getEspecie() + "\n" +
                "Raza: " + mascota.getRaza() + "\n" +
                "Peso: " + mascota.getPeso() + " kg";
    }

    protected String generarTicketFinal() {
        if (adopcion != null) {
            return "Ticket de adopción # " + adopcion.getNumeroAdopcion();
        }
        return "Error al generar ticket final";
    }

    protected boolean validarAdopcion() {
        return adoptante.getEdad() >= 18;
    }

    public Adopcion getAdopcion() {
        return adopcion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Adoptante getAdoptante() {
        return adoptante;
    }
}