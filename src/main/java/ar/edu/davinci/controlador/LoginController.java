package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.EmpleadoDAO;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.managers.EmpleadoManager;
import ar.edu.davinci.vista.login.LoginView;
import ar.edu.davinci.vista.login.RegistroEmpleadoView;
import ar.edu.davinci.vista.adopcion.AdopcionView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginController {
    private LoginView loginView;
    private EmpleadoDAO empleadoDAO;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.empleadoDAO = new EmpleadoDAO();
        initEventListeners();

    }

    private void initEventListeners() {
        loginView.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Boton de login presionado");
                realizarLogin();
            }
        });

        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });


        loginView.getTxtUsername().addActionListener(e -> {
            realizarLogin();
        });

        loginView.getTxtPassword().addActionListener(e -> {
            realizarLogin();
        });

    }

    private void realizarLogin() {
        System.out.println("\n=== INICIANDO PROCESO DE LOGIN ===");

        String username = loginView.getTxtUsername().getText().trim();
        String password = new String(loginView.getTxtPassword().getPassword());


        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(loginView,
                    "Por favor ingrese usuario y contraseña",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            boolean loginValido = empleadoDAO.validarLogin(username, password);

            if (loginValido) {

                Empleado empleado = empleadoDAO.buscarPorUsername(username);

                if (empleado != null) {

                    EmpleadoManager.getInstance().setEmpleadoLogueado(empleado);

                    JOptionPane.showMessageDialog(loginView,
                            "¡Bienvenido " + empleado.getNombre() + "!\nAcceso concedido al sistema.",
                            "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);

                    loginView.dispose();
                    abrirSistemaAdopcion();
                } else {
                    JOptionPane.showMessageDialog(loginView,
                            "Error interno: No se pudieron cargar los datos del empleado",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(loginView,
                        "Usuario o contraseña incorrectos.\nVerifique sus credenciales.",
                        "Error de Login", JOptionPane.ERROR_MESSAGE);

                // Limpiar campos
                loginView.getTxtPassword().setText("");
                loginView.getTxtUsername().requestFocus();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(loginView,
                    "Error al conectar con la base de datos:\n" + ex.getMessage() +
                            "\n\nVerifique que H2 esté correctamente configurado.",
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(loginView,
                    "Error inesperado durante el login:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("=== FIN PROCESO DE LOGIN ===\n");
    }

    private void abrirRegistro() {
        RegistroEmpleadoView registroView = new RegistroEmpleadoView(loginView);
        new RegistroEmpleadoController(registroView);
        registroView.setVisible(true);
    }

    private void abrirSistemaAdopcion() {
        SwingUtilities.invokeLater(() -> {
            try {
                AdopcionView adopcionView = new AdopcionView();
                new AdopcionController(adopcionView);
                adopcionView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error al abrir el sistema de adopción: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}