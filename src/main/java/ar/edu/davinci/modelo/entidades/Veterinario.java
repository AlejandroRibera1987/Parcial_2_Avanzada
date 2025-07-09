package ar.edu.davinci.modelo.entidades;

import ar.edu.davinci.modelo.utils.EmailSenderI;
import ar.edu.davinci.modelo.utils.EmailSender;

public class Veterinario extends Persona {

    private EmailSenderI emailSender;
    private String email;

    public Veterinario(String nombre, String apellido, String direccion, String email) {
        super(nombre, apellido, direccion);
        this.emailSender = new EmailSender();
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void notificarCambioDeTemperatura(Mascota mascota, double temperaturaAnterior, double temperaturaNueva) {
        String asunto = "Alerta Cambio de temperatura de: " + mascota.getNombre();
        String mensaje = "Estimado " + nombre + ": " + asunto +
                "\nSe registró un cambio de temperatura de la mascota: " + mascota.getNombre() +
                "\nRaza: " + mascota.getRaza() +
                "\nEstado Actual: " + mascota.getEstado().getNombreEstado() +
                "\nTemperatura Anterior: " + temperaturaAnterior + "°C" +
                "\nTemperatura Nueva: " + temperaturaNueva + "°C" +
                "\nSaludos";

        emailSender.enviar("sistema@veterinaria.com", email, asunto, mensaje);
    }

    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " (" + email + ")";
    }
}