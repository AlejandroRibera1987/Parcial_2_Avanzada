package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.*;
import ar.edu.davinci.modelo.entidades.*;
import ar.edu.davinci.modelo.managers.EmpleadoManager;
import ar.edu.davinci.vista.adopcion.AdopcionView;
import ar.edu.davinci.vista.login.LoginView;
import ar.edu.davinci.vista.adopcion.HistorialAdopcionesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdopcionController {
    private AdopcionView adopcionView;
    private MascotaDAO mascotaDAO;
    private AdoptanteDAO adoptanteDAO;
    private AdopcionDAO adopcionDAO;

    public AdopcionController(AdopcionView adopcionView) {
        this.adopcionView = adopcionView;
        this.mascotaDAO = new MascotaDAO();
        this.adoptanteDAO = new AdoptanteDAO();
        this.adopcionDAO = new AdopcionDAO();

        initEventListeners();
        cargarMascotasDisponibles();
        mostrarEmpleadoLogueado();
    }

    private void abrirHistorialAdopciones() {
        try {
            HistorialAdopcionesView historialView = new HistorialAdopcionesView();
            new HistorialAdopcionesController(historialView);
            historialView.setVisible(true);
        } catch (Exception e) {
            adopcionView.mostrarMensaje(
                    "Error al abrir el historial de adopciones: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void initEventListeners() {
        adopcionView.getBtnProcesarAdopcion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarAdopcion();
            }

        });

        adopcionView.getBtnCerrarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        adopcionView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        adopcionView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMascotasDisponibles();
            }
        });

        adopcionView.getBtnHistorial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirHistorialAdopciones();
            }
        });

    }

    private void cargarMascotasDisponibles() {
        try {
            List<Mascota> mascotas = mascotaDAO.obtenerDisponibles();
            adopcionView.actualizarListaMascotas(mascotas);

            if (mascotas.isEmpty()) {
                adopcionView.mostrarMensaje(
                        "No hay mascotas disponibles para adopción en este momento.",
                        "Sin Mascotas Disponibles",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException e) {
            adopcionView.mostrarMensaje(
                    "Error al cargar mascotas: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void mostrarEmpleadoLogueado() {
        Empleado empleado = EmpleadoManager.getInstance().getEmpleadoLogueado();
        if (empleado != null) {
            adopcionView.mostrarEmpleadoLogueado(empleado);
        } else {
            adopcionView.mostrarMensaje(
                    "No hay empleado logueado. Cerrando sesión...",
                    "Error de Sesión",
                    JOptionPane.ERROR_MESSAGE
            );
            cerrarSesion();
        }
    }

    private void procesarAdopcion() {
        try {
            Mascota mascotaSeleccionada = adopcionView.getMascotaSeleccionada();
            if (mascotaSeleccionada == null) {
                adopcionView.mostrarMensaje(
                        "Por favor seleccione una mascota de la lista para continuar con la adopción.",
                        "Mascota No Seleccionada",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }


            Adoptante adoptante = crearAdoptanteDesdeFormulario();
            if (adoptante == null) return; // Error en validación


            adoptanteDAO.crear(adoptante);


            Empleado empleado = EmpleadoManager.getInstance().getEmpleadoLogueado();


            Adopcion adopcion = new Adopcion(mascotaSeleccionada, adoptante, empleado);
            int numeroAdopcion = adopcionDAO.crear(adopcion);
            adopcion.setNumeroAdopcion(numeroAdopcion);


            mascotaDAO.marcarComoAdoptada(mascotaSeleccionada.getIdMascota());


            mostrarTicketAdopcion(adopcion);


            limpiarFormulario();
            cargarMascotasDisponibles();

            adopcionView.mostrarMensaje(
                    "¡Adopción procesada exitosamente!\n\n" +
                            "Número de adopción: " + numeroAdopcion + "\n" +
                            "Mascota: " + mascotaSeleccionada.getNombre() + "\n" +
                            "Adoptante: " + adoptante.getNombre() + " " + adoptante.getApellido() + "\n\n" +
                            "¡Gracias por dar una nueva oportunidad a " + mascotaSeleccionada.getNombre() + "!",
                    "Adopción Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            adopcionView.mostrarMensaje(
                    "Error al procesar adopción:\n" + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } catch (Exception e) {
            adopcionView.mostrarMensaje(
                    "Error inesperado al procesar adopción:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private Adoptante crearAdoptanteDesdeFormulario() {
        String nombre = adopcionView.getTxtNombreAdoptante().getText().trim();
        String apellido = adopcionView.getTxtApellidoAdoptante().getText().trim();
        String direccion = adopcionView.getTxtDireccionAdoptante().getText().trim();
        String edadStr = adopcionView.getTxtEdadAdoptante().getText().trim();
        String telefono = adopcionView.getTxtTelefonoAdoptante().getText().trim();
        String email = adopcionView.getTxtEmailAdoptante().getText().trim();


        if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || edadStr.isEmpty()) {
            adopcionView.mostrarMensaje(
                    "Por favor complete todos los campos obligatorios del adoptante:\n" +
                            "• Nombre\n• Apellido\n• Dirección\n• Edad",
                    "Campos Obligatorios",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 18) {
                adopcionView.mostrarMensaje(
                        "El adoptante debe ser mayor de edad (18 años o más).\nEdad ingresada: " + edad + " años",
                        "Edad Insuficiente",
                        JOptionPane.WARNING_MESSAGE
                );
                return null;
            }
            if (edad > 100) {
                adopcionView.mostrarMensaje(
                        "La edad ingresada no es válida.\nPor favor verifique el dato.",
                        "Edad Inválida",
                        JOptionPane.WARNING_MESSAGE
                );
                return null;
            }
        } catch (NumberFormatException e) {
            adopcionView.mostrarMensaje(
                    "La edad debe ser un número válido.\nEjemplo: 25",
                    "Formato de Edad Inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }


        if (!email.isEmpty() && !email.contains("@")) {
            adopcionView.mostrarMensaje(
                    "El formato del email no es válido.\nEjemplo: usuario@email.com",
                    "Email Inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        Adoptante adoptante = new Adoptante(nombre, apellido, direccion, edad);
        adoptante.setTelefono(telefono);
        adoptante.setEmail(email);

        return adoptante;
    }

    private void mostrarTicketAdopcion(Adopcion adopcion) {
        String ticket = String.format("""
            =====================================
                    TICKET DE ADOPCIÓN #%d
            =====================================
            Fecha: %s
            
             ADOPTANTE:
            • Nombre: %s %s
            • Edad: %d años
            • Dirección: %s
            • Teléfono: %s
            • Email: %s
            
             MASCOTA:
            • Nombre: %s
            • Especie: %s
            • Raza: %s
            • Peso: %.2f kg
            • Estado: %s
            
            EMPLEADO RESPONSABLE:
            • %s %s (ID: %d)
            
             RECOMENDACIONES IMPORTANTES:
            %s
            
            ¡Felicitaciones por su nueva mascota!
            =====================================
            """,
                adopcion.getNumeroAdopcion(),
                adopcion.getFecha(),
                adopcion.getAdoptante().getNombre(),
                adopcion.getAdoptante().getApellido(),
                adopcion.getAdoptante().getEdad(),
                adopcion.getAdoptante().getDireccion(),
                adopcion.getAdoptante().getTelefono() != null ? adopcion.getAdoptante().getTelefono() : "No proporcionado",
                adopcion.getAdoptante().getEmail() != null ? adopcion.getAdoptante().getEmail() : "No proporcionado",
                adopcion.getMascota().getNombre(),
                adopcion.getMascota().getEspecie(),
                adopcion.getMascota().getRaza(),
                adopcion.getMascota().getPeso(),
                adopcion.getMascota().getEstado().getNombreEstado(),
                adopcion.getEmpleado().getNombre(),
                adopcion.getEmpleado().getApellido(),
                adopcion.getEmpleado().getIdEmpleado(),
                adopcion.getMascota().getRecomendaciones()
        );

        JTextArea textArea = new JTextArea(ticket);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 11));
        textArea.setBackground(new java.awt.Color(248, 248, 248));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 500));

        JOptionPane.showMessageDialog(adopcionView, scrollPane,
                "Ticket de Adopcion #" + adopcion.getNumeroAdopcion(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormulario() {
        adopcionView.limpiarFormulario();
    }

    private void cerrarSesion() {
        boolean confirmar = adopcionView.confirmar(
                "¿Esta seguro que desea cerrar sesion?\n\nSe perderan los datos no guardados del formulario.",
                "Confirmar Cierre de Sesion"
        );

        if (confirmar) {
            EmpleadoManager.getInstance().cerrarSesion();
            adopcionView.dispose();

            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView();
                new LoginController(loginView);
                loginView.setVisible(true);
            });
        }
    }
}
