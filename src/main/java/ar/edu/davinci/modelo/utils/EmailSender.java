package ar.edu.davinci.modelo.utils;

public class EmailSender implements EmailSenderI {
    public void enviar(String origen, String destino, String asunto, String mensaje) {
        System.out.println("====== Enviando email ======");
        System.out.println("De: " + origen);
        System.out.println("Para: " + destino);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("====== Mail Enviado ======");
    }
}
