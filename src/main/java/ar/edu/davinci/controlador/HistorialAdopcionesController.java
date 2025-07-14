package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.AdopcionDAO;
import ar.edu.davinci.modelo.entidades.Adopcion;
import ar.edu.davinci.vista.adopcion.HistorialAdopcionesView;
import ar.edu.davinci.vista.adopcion.ModificarAdopcionView;
import ar.edu.davinci.vista.adopcion.DetallesAdopcionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.List;

public class HistorialAdopcionesController {
    private HistorialAdopcionesView historialView;
    private AdopcionDAO adopcionDAO;
    private TableRowSorter<DefaultTableModel> sorter;

    public HistorialAdopcionesController(HistorialAdopcionesView historialView) {
        this.historialView = historialView;
        this.adopcionDAO = new AdopcionDAO();
        this.sorter = new TableRowSorter<>(historialView.getModeloTabla());

        initEventListeners();
        configurarTabla();
        cargarDatos();
    }

    private void initEventListeners() {
        historialView.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarTabla();
            }
        });

        historialView.getBtnBuscar().addActionListener(e -> filtrarTabla());

        historialView.getBtnDetalles().addActionListener(e -> verDetalles());

        historialView.getBtnModificar().addActionListener(e -> modificarAdopcion());

        historialView.getBtnEliminar().addActionListener(e -> eliminarAdopcion());

        historialView.getBtnCerrar().addActionListener(e -> historialView.dispose());

        historialView.getTablaHistorial().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    verDetalles();
                }
            }
        });
    }

    private void configurarTabla() {
        historialView.getTablaHistorial().setRowSorter(sorter);
    }

    private void cargarDatos() {
        historialView.getModeloTabla().setRowCount(0);

        try {
            List<Adopcion> adopciones = adopcionDAO.obtenerTodasConDetalles();

            for (Adopcion adopcion : adopciones) {
                Object[] fila = {
                        adopcion.getNumeroAdopcion(),
                        formatearFechaParaTabla(adopcion.getFecha()), // Usar método helper
                        adopcion.getMascota().getNombre(),
                        adopcion.getMascota().getRaza(),
                        adopcion.getAdoptante().getNombre() + " " + adopcion.getAdoptante().getApellido(),
                        adopcion.getAdoptante().getTelefono() != null ? adopcion.getAdoptante().getTelefono() : "N/A",
                        adopcion.getEmpleado().getNombre() + " " + adopcion.getEmpleado().getApellido(),
                        "Activa",
                        ""
                };
                historialView.getModeloTabla().addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(historialView,
                    "Error al cargar los datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String formatearFechaParaTabla(String fechaString) {
        try {
            // La fecha viene como String: "2024-01-15 10:30:00.0"
            // Extraer solo la parte de fecha y formatearla como "15/01/2024"
            String fechaSolo = fechaString.split(" ")[0]; // "2024-01-15"
            String[] partes = fechaSolo.split("-");

            if (partes.length == 3) {
                String año = partes[0];
                String mes = partes[1];
                String dia = partes[2];

                return dia + "/" + mes + "/" + año;
            }
        } catch (Exception e) {
            System.err.println("Error al formatear fecha para tabla: " + e.getMessage());
        }

        // Si hay error, devolver la fecha original
        return fechaString;
    }

    private void filtrarTabla() {
        String texto = historialView.getTxtBuscar().getText().trim();
        if (texto.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    private void verDetalles() {
        int filaSeleccionada = historialView.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(historialView,
                    "Por favor, seleccione una adopción para ver los detalles.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaModelo = historialView.getTablaHistorial().convertRowIndexToModel(filaSeleccionada);

        int numeroAdopcion = (Integer) historialView.getModeloTabla().getValueAt(filaModelo, 0);

        try {
            Adopcion adopcion = adopcionDAO.buscarPorNumero(numeroAdopcion);
            if (adopcion != null) {
                DetallesAdopcionView detalles = new DetallesAdopcionView(adopcion);
                new DetallesAdopcionController(detalles);
                detalles.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(historialView,
                    "Error al obtener detalles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarAdopcion() {
        int filaSeleccionada = historialView.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(historialView,
                    "Por favor, seleccione una adopción para modificar.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaModelo = historialView.getTablaHistorial().convertRowIndexToModel(filaSeleccionada);

        int numeroAdopcion = (Integer) historialView.getModeloTabla().getValueAt(filaModelo, 0);

        try {
            Adopcion adopcion = adopcionDAO.buscarPorNumero(numeroAdopcion);
            if (adopcion != null) {
                ModificarAdopcionView modificar = new ModificarAdopcionView(adopcion, this);
                new ModificarAdopcionController(modificar);
                modificar.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(historialView,
                    "Error al obtener adopción: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarAdopcion() {
        int filaSeleccionada = historialView.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(historialView,
                    "Por favor, seleccione una adopción para eliminar.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaModelo = historialView.getTablaHistorial().convertRowIndexToModel(filaSeleccionada);

        int numeroAdopcion = (Integer) historialView.getModeloTabla().getValueAt(filaModelo, 0);
        String mascota = (String) historialView.getModeloTabla().getValueAt(filaModelo, 2);
        String adoptante = (String) historialView.getModeloTabla().getValueAt(filaModelo, 4);

        int respuesta = JOptionPane.showConfirmDialog(historialView,
                "¿Está seguro de eliminar la adopción de " + mascota + " por " + adoptante + "?\n" +
                        "Esta acción no se puede deshacer y liberará la mascota.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                adopcionDAO.eliminar(numeroAdopcion);

                cargarDatos();

                JOptionPane.showMessageDialog(historialView,
                        "Adopción eliminada correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(historialView,
                        "Error al eliminar la adopción: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void actualizarTabla() {
        cargarDatos();
    }
}