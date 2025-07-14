package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.*;
import ar.edu.davinci.modelo.entidades.*;
import ar.edu.davinci.vista.adopcion.ModificarAdopcionView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ModificarAdopcionController {
    private ModificarAdopcionView modificarView;
    private AdopcionDAO adopcionDAO;
    private MascotaDAO mascotaDAO;
    private AdoptanteDAO adoptanteDAO;
    private EmpleadoDAO empleadoDAO;
    private Adopcion adopcionOriginal;
    private HistorialAdopcionesController ventanaPadre;

    public ModificarAdopcionController(ModificarAdopcionView modificarView) {
        this.modificarView = modificarView;
        this.adopcionDAO = new AdopcionDAO();
        this.mascotaDAO = new MascotaDAO();
        this.adoptanteDAO = new AdoptanteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.adopcionOriginal = modificarView.getAdopcion();
        this.ventanaPadre = modificarView.getVentanaPadre();

        initEventListeners();
        cargarDatosIniciales();
        cargarComboBoxes();
    }

    private void initEventListeners() {
        modificarView.getBtnGuardar().addActionListener(e -> guardarCambios());

        modificarView.getBtnCancelar().addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(modificarView,
                    "¿Está seguro de cancelar? Se perderán los cambios no guardados.",
                    "Confirmar cancelación",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                modificarView.dispose();
            }
        });

        modificarView.getBtnBuscarFecha().addActionListener(e -> seleccionarFecha());

        modificarView.getCmbAdoptante().addActionListener(e -> cargarDatosAdoptante());
    }

    private void cargarDatosIniciales() {
        modificarView.getTxtId().setText(String.valueOf(adopcionOriginal.getNumeroAdopcion()));

        String fechaFormateada = formatearFechaParaEdicion(adopcionOriginal.getFecha());
        modificarView.getTxtFecha().setText(fechaFormateada);

        actualizarDatosAdoptante(adopcionOriginal.getAdoptante());
    }

    private String formatearFechaParaEdicion(String fechaString) {
        try {
            // La fecha viene como String: "2024-01-15 10:30:00.0"
            // Convertir a formato editable: "15/01/2024"
            String fechaSolo = fechaString.split(" ")[0]; // "2024-01-15"
            String[] partes = fechaSolo.split("-");

            if (partes.length == 3) {
                String año = partes[0];
                String mes = partes[1];
                String dia = partes[2];

                return dia + "/" + mes + "/" + año;
            }
        } catch (Exception e) {
            System.err.println("Error al formatear fecha para edición: " + e.getMessage());
        }

        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
    }

    private void cargarComboBoxes() {
        cargarMascotas();
        cargarAdoptantes();
        cargarEmpleados();
    }

    private void cargarMascotas() {
        modificarView.getCmbMascota().removeAllItems();

        try {
            List<Mascota> mascotas = mascotaDAO.obtenerDisponibles();

            Mascota mascotaActual = adopcionOriginal.getMascota();
            boolean mascotaActualEnLista = mascotas.stream()
                    .anyMatch(m -> m.getIdMascota() == mascotaActual.getIdMascota());

            if (!mascotaActualEnLista) {
                mascotas.add(0, mascotaActual);
            }

            for (Mascota mascota : mascotas) {
                String item = mascota.getIdMascota() + " - " + mascota.getNombre() + " (" + mascota.getRaza() + ")";
                modificarView.getCmbMascota().addItem(item);

                if (mascota.getIdMascota() == mascotaActual.getIdMascota()) {
                    modificarView.getCmbMascota().setSelectedItem(item);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modificarView,
                    "Error al cargar mascotas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAdoptantes() {
        modificarView.getCmbAdoptante().removeAllItems();

        try {
            List<Adoptante> adoptantes = adoptanteDAO.obtenerTodos();

            for (Adoptante adoptante : adoptantes) {
                String item = adoptante.getIdAdoptante() + " - " + adoptante.getNombre() + " " + adoptante.getApellido();
                modificarView.getCmbAdoptante().addItem(item);

                if (adoptante.getIdAdoptante() == adopcionOriginal.getAdoptante().getIdAdoptante()) {
                    modificarView.getCmbAdoptante().setSelectedItem(item);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modificarView,
                    "Error al cargar adoptantes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEmpleados() {
        modificarView.getCmbEmpleado().removeAllItems();

        try {
            List<Empleado> empleados = empleadoDAO.obtenerTodos();

            for (Empleado empleado : empleados) {
                String item = empleado.getIdEmpleado() + " - " + empleado.getNombre() + " " + empleado.getApellido();
                modificarView.getCmbEmpleado().addItem(item);

                if (empleado.getIdEmpleado() == adopcionOriginal.getEmpleado().getIdEmpleado()) {
                    modificarView.getCmbEmpleado().setSelectedItem(item);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modificarView,
                    "Error al cargar empleados: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosAdoptante() {
        String adoptanteSeleccionado = (String) modificarView.getCmbAdoptante().getSelectedItem();
        if (adoptanteSeleccionado != null) {
            try {
                // Extraer ID del adoptante del string seleccionado
                int idAdoptante = Integer.parseInt(adoptanteSeleccionado.split(" - ")[0]);

                // Buscar adoptante en la base de datos
                Adoptante adoptante = adoptanteDAO.buscarPorId(idAdoptante);

                if (adoptante != null) {
                    actualizarDatosAdoptante(adoptante);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(modificarView,
                        "Error al cargar datos del adoptante: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarDatosAdoptante(Adoptante adoptante) {
        modificarView.getTxtDireccionAdoptante().setText(adoptante.getDireccion());
        modificarView.getTxtTelefonoAdoptante().setText(
                adoptante.getTelefono() != null ? adoptante.getTelefono() : ""
        );
    }

    private void seleccionarFecha() {
        String fechaActual = modificarView.getTxtFecha().getText();
        String nuevaFecha = JOptionPane.showInputDialog(modificarView,
                "Ingrese la nueva fecha (dd/MM/yyyy):",
                "Seleccionar Fecha",
                JOptionPane.PLAIN_MESSAGE);

        if (nuevaFecha != null && !nuevaFecha.trim().isEmpty()) {
            if (nuevaFecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
                modificarView.getTxtFecha().setText(nuevaFecha);
            } else {
                JOptionPane.showMessageDialog(modificarView,
                        "Formato de fecha inválido. Use dd/MM/yyyy",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarCambios() {
        if (!validarCampos()) {
            return;
        }

        try {
            int idMascota = obtenerIdMascotaSeleccionada();
            int idAdoptante = obtenerIdAdoptanteSeleccionado();
            int idEmpleado = obtenerIdEmpleadoSeleccionado();

            if (idMascota != adopcionOriginal.getMascota().getIdMascota()) {
                mascotaDAO.marcarComoDisponible(adopcionOriginal.getMascota().getIdMascota());

                mascotaDAO.marcarComoAdoptada(idMascota);
            }

            if (idAdoptante == adopcionOriginal.getAdoptante().getIdAdoptante()) {
                Adoptante adoptante = adopcionOriginal.getAdoptante();
                adoptante.setDireccion(modificarView.getTxtDireccionAdoptante().getText().trim());
                adoptante.setTelefono(modificarView.getTxtTelefonoAdoptante().getText().trim());
                adoptanteDAO.actualizar(adoptante);
            }

            Mascota mascotaModificada = mascotaDAO.buscarPorId(idMascota);
            Adoptante adoptanteModificado = adoptanteDAO.buscarPorId(idAdoptante);
            Empleado empleadoModificado = empleadoDAO.buscarPorId(idEmpleado);

            Adopcion adopcionModificada = new Adopcion(mascotaModificada, adoptanteModificado, empleadoModificado);
            adopcionModificada.setNumeroAdopcion(adopcionOriginal.getNumeroAdopcion());

            String fechaFormateada = convertirFechaParaGuardar(modificarView.getTxtFecha().getText());
            adopcionModificada.setFecha(fechaFormateada);

            adopcionDAO.actualizar(adopcionModificada);

            JOptionPane.showMessageDialog(modificarView,
                    "Adopción modificada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            if (ventanaPadre != null) {
                ventanaPadre.actualizarTabla();
            }

            modificarView.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(modificarView,
                    "Error al guardar los cambios: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String convertirFechaParaGuardar(String fechaTexto) {
        try {
            // Convertir de "15/01/2024" a "2024-01-15 00:00:00.0"
            String[] partes = fechaTexto.split("/");

            if (partes.length == 3) {
                String dia = partes[0];
                String mes = partes[1];
                String año = partes[2];

                // Asegurar formato de 2 dígitos para día y mes
                if (dia.length() == 1) dia = "0" + dia;
                if (mes.length() == 1) mes = "0" + mes;

                return año + "-" + mes + "-" + dia + " 00:00:00.0";
            }
        } catch (Exception e) {
            System.err.println("Error al convertir fecha para guardar: " + e.getMessage());
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return sdf.format(new java.util.Date());
    }

    private int obtenerIdMascotaSeleccionada() {
        String item = (String) modificarView.getCmbMascota().getSelectedItem();
        return Integer.parseInt(item.split(" - ")[0]);
    }

    private int obtenerIdAdoptanteSeleccionado() {
        String item = (String) modificarView.getCmbAdoptante().getSelectedItem();
        return Integer.parseInt(item.split(" - ")[0]);
    }

    private int obtenerIdEmpleadoSeleccionado() {
        String item = (String) modificarView.getCmbEmpleado().getSelectedItem();
        return Integer.parseInt(item.split(" - ")[0]);
    }

    private boolean validarCampos() {
        if (modificarView.getCmbMascota().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(modificarView, "Debe seleccionar una mascota.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (modificarView.getCmbAdoptante().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(modificarView, "Debe seleccionar un adoptante.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (modificarView.getCmbEmpleado().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(modificarView, "Debe seleccionar un empleado.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (modificarView.getTxtFecha().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(modificarView, "Debe ingresar una fecha.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!modificarView.getTxtFecha().getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(modificarView, "Formato de fecha inválido. Use dd/MM/yyyy", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (modificarView.getTxtDireccionAdoptante().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(modificarView, "Debe ingresar una dirección.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }
}