package ar.edu.davinci.vista.adopcion;

import ar.edu.davinci.controlador.HistorialAdopcionesController;
import ar.edu.davinci.modelo.entidades.Adopcion;

import javax.swing.*;
import java.awt.*;

public class ModificarAdopcionView extends JFrame {

    private Adopcion adopcion;
    private HistorialAdopcionesController ventanaPadre;

    private JTextField txtId;
    private JComboBox<String> cmbMascota;
    private JComboBox<String> cmbAdoptante;
    private JComboBox<String> cmbEmpleado;
    private JTextField txtFecha;
    private JTextField txtDireccionAdoptante;
    private JTextField txtTelefonoAdoptante;

    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnBuscarFecha;

    public ModificarAdopcionView(Adopcion adopcion, HistorialAdopcionesController ventanaPadre) {
        this.adopcion = adopcion;
        this.ventanaPadre = ventanaPadre;

        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        setTitle("Modificar Adopción - ID: " + adopcion.getNumeroAdopcion());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(new JLabel("ID Adopción:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panelPrincipal.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Mascota:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbMascota = new JComboBox<>();
        panelPrincipal.add(cmbMascota, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Adoptante:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbAdoptante = new JComboBox<>();
        panelPrincipal.add(cmbAdoptante, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Empleado:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbEmpleado = new JComboBox<>();
        panelPrincipal.add(cmbEmpleado, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Fecha Adopción:"), gbc);

        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtFecha = new JTextField(10);
        btnBuscarFecha = new JButton("📅");
        btnBuscarFecha.setPreferredSize(new Dimension(30, 25));
        panelFecha.add(txtFecha);
        panelFecha.add(btnBuscarFecha);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(panelFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Dirección:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDireccionAdoptante = new JTextField(30);
        panelPrincipal.add(txtDireccionAdoptante, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefonoAdoptante = new JTextField(15);
        panelPrincipal.add(txtTelefonoAdoptante, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGuardar = new JButton("Guardar Cambios");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // Getters para el controlador
    public Adopcion getAdopcion() { return adopcion; }
    public HistorialAdopcionesController getVentanaPadre() { return ventanaPadre; }
    public JTextField getTxtId() { return txtId; }
    public JComboBox<String> getCmbMascota() { return cmbMascota; }
    public JComboBox<String> getCmbAdoptante() { return cmbAdoptante; }
    public JComboBox<String> getCmbEmpleado() { return cmbEmpleado; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JTextField getTxtDireccionAdoptante() { return txtDireccionAdoptante; }
    public JTextField getTxtTelefonoAdoptante() { return txtTelefonoAdoptante; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JButton getBtnBuscarFecha() { return btnBuscarFecha; }
}