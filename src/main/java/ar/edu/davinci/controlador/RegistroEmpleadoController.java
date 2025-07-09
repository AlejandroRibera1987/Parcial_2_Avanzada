package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.EmpleadoDAO;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.vista.login.RegistroEmpleadoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistroEmpleadoController {
    private RegistroEmpleadoView registroView;
    private EmpleadoDAO empleadoDAO;

    public RegistroEmpleadoController(RegistroEmpleadoView registroView) {
        this.registroView = registroView;
        this.empleadoDAO = new EmpleadoDAO();
        initEventListeners();
    }

    private void initEventListeners() {
        registroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEmpleado();
            }
        });

        registroView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registroView.dispose();
            }
        });

        // Listener para Enter en campos de texto
        registroView.getTxtNombre().addActionListener(e -> registroView.getTxtApellido().requestFocus());
        registroView.getTxtApellido().addActionListener(e -> registroView.getTxtDireccion().requestFocus());
        registroView.getTxtDireccion().addActionListener(e -> registroView.getTxtUsername().requestFocus());
        registroView.getTxtUsername().addActionListener(e -> registroView.getTxtPassword().requestFocus());
        registroView.getTxtPassword().addActionListener(e -> registroView.getTxtEmail().requestFocus());
        registroView.getTxtEmail().addActionListener(e -> registrarEmpleado());
    }

    private void registrarEmpleado() {
        String nombre = registroView.getTxtNombre().getText().trim();
        String apellido = registroView.getTxtApellido().getText().trim();
        String direccion = registroView.getTxtDireccion().getText().trim();
        String username = registroView.getTxtUsername().getText().trim();
        String password = new String(registroView.getTxtPassword().getPassword());
        String email = registroView.getTxtEmail().getText().trim();

        // Validaciones básicas
        if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(registroView,
                    "Por favor complete todos los campos obligatorios:\n• Nombre\n• Apellido\n• Usuario\n• Contraseña",
                    "Campos Obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(registroView,
                    "La contraseña debe tener al menos 6 caracteres",
                    "Contraseña Invalida", JOptionPane.WARNING_MESSAGE);
            registroView.getTxtPassword().requestFocus();
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(registroView,
                    "El nombre de usuario debe tener al menos 3 caracteres",
                    "Usuario Invalido", JOptionPane.WARNING_MESSAGE);
            registroView.getTxtUsername().requestFocus();
            return;
        }

        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(registroView,
                    "El formato del email no es válido",
                    "Email Invaalido", JOptionPane.WARNING_MESSAGE);
            registroView.getTxtEmail().requestFocus();
            return;
        }

        try {
            if (empleadoDAO.existeUsername(username)) {
                JOptionPane.showMessageDialog(registroView,
                        "El nombre de usuario '" + username + "' ya existe.\nPor favor elija otro nombre de usuario.",
                        "Usuario Existente", JOptionPane.WARNING_MESSAGE);
                registroView.getTxtUsername().requestFocus();
                return;
            }

            Empleado empleado = new Empleado(nombre, apellido, direccion, username, password);
            empleado.setEmail(email);

            int id = empleadoDAO.crear(empleado);

            if (id > 0) {
                JOptionPane.showMessageDialog(registroView,
                        "Empleado registrado exitosamente!\n\n" +
                                "Datos registrados:\n" +
                                "• Nombre: " + nombre + " " + apellido + "\n" +
                                "• Usuario: " + username + "\n" +
                                "• ID: " + id + "\n\n" +
                                "Ya puede iniciar sesion con sus credenciales.",
                        "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                registroView.dispose();
            } else {
                JOptionPane.showMessageDialog(registroView,
                        "Error al registrar empleado.\nIntente nuevamente.",
                        "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(registroView,
                    "Error al conectar con la base de datos:\n" + ex.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
