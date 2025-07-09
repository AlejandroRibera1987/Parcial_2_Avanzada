package ar.edu.davinci;

import ar.edu.davinci.controlador.LoginController;
import ar.edu.davinci.vista.login.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        configurarLookAndFeel();


        SwingUtilities.invokeLater(() -> {
            try {
                iniciarAplicacion();
            } catch (Exception e) {
                manejarErrorCritico(e);
            }
        });
    }


    private static void configurarLookAndFeel() {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            System.out.println("Look and Feel configurado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Clase de Look and Feel no encontrada: " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Error al instanciar Look and Feel: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Acceso ilegal al Look and Feel: " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Look and Feel no soportado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado configurando Look and Feel: " + e.getMessage());
        }

        System.out.println("Continuando con el Look and Feel disponible...");
    }


    private static void iniciarAplicacion() {
        System.out.println("=".repeat(60));
        System.out.println("SISTEMA DE ADOPCIÓN DE MASCOTAS");
        System.out.println("=".repeat(60));
        System.out.println("Versión: 1.0");
        System.out.println("Desarrollado por: Alejandro Ribera");
        System.out.println("Inicializando interfaz gráfica...");

        // Crear y mostrar la ventana de login
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);

        System.out.println("Aplicación iniciada correctamente");
        System.out.println("Credenciales por defecto:");
        System.out.println("Usuario: admin");
        System.out.println("Contraseña: admin123");
        System.out.println("=".repeat(60));
    }

    private static void manejarErrorCritico(Exception e) {
        e.printStackTrace();

        String mensajeError = String.format("""
            ERROR CRÍTICO AL INICIALIZAR LA APLICACIÓN
            
            Tipo de error: %s
            Mensaje: %s
            
            Por favor:
            1. Verifique que Java esté correctamente instalado
            2. Asegúrese de que todas las dependencias estén disponibles
            3. Contacte al administrador del sistema si el problema persiste
            
            Detalles técnicos:
            %s
            """,
                e.getClass().getSimpleName(),
                e.getMessage() != null ? e.getMessage() : "Error desconocido",
                obtenerDetallesError(e)
        );


        try {
            JOptionPane.showMessageDialog(null,
                    mensajeError,
                    "Error Crítico - Sistema de Adopción",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
           System.err.println(mensajeError);
        }


        System.err.println("ERROR CRÍTICO: La aplicación no pudo iniciarse");
        System.err.println("Timestamp: " + java.time.LocalDateTime.now());
        System.err.println("Java Version: " + System.getProperty("java.version"));
        System.err.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));

        System.exit(1);
    }


    private static String obtenerDetallesError(Exception e) {
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StackTraceElement elemento = e.getStackTrace()[0];
            return String.format("Archivo: %s, Línea: %d, Método: %s",
                    elemento.getFileName(),
                    elemento.getLineNumber(),
                    elemento.getMethodName());
        }
        return "Detalles no disponibles";
    }
}