package ar.edu.davinci.vista.adopcion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistorialAdopcionesView extends JFrame {

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JButton btnCerrar;

    public HistorialAdopcionesView() {
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        setTitle("Historial de Adopciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Buscar Adopciones"));

        panelSuperior.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelSuperior.add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {
                "ID", "Fecha", "Mascota", "Raza", "Adoptante",
                "Teléfono", "Empleado", "Estado", "Observaciones"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnDetalles = new JButton("Ver Detalles");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnDetalles);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Getters para el controlador
    public JTable getTablaHistorial() { return tablaHistorial; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTextField getTxtBuscar() { return txtBuscar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnDetalles() { return btnDetalles; }
    public JButton getBtnCerrar() { return btnCerrar; }
}