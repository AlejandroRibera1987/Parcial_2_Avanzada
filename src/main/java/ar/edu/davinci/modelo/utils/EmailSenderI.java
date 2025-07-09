package ar.edu.davinci.modelo.utils;

public interface EmailSenderI {
    public void enviar(String origen, String destino, String asunto, String mensaje);
}
