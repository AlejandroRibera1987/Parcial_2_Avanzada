package ar.edu.davinci.vista.adopcion;

import ar.edu.davinci.modelo.entidades.Adopcion;

import javax.swing.*;
import java.awt.*;

public class DetallesAdopcionView extends JFrame {

    private Adopcion adopcion;

    private JLabel lblIdAdopcion;
    private JLabel lblFechaAdopcion;
    private JLabel lblEstado;

    private JLabel lblMascotaNombre;
    private JLabel lblMascotaEspecie;
    private JLabel lblMascotaRaza;
    private JLabel lblMascotaEdad;
    private JLabel lblMascotaColor;
    private JLabel lblMascotaTamanio;

    private JLabel lblAdoptanteNombre;
    private JLabel lblAdoptanteEmail;
    private JLabel lblAdoptanteTelefono;
    private JLabel lblAdoptanteDireccion;
    private JLabel lblAdoptanteDni;

    private JLabel lblEmpleadoNombre;
    private JLabel lblEmpleadoCargo;
    private JLabel lblEmpleadoLegajo;

    private JTextArea txtObservaciones;

    private JButton btnCerrar;
    private JButton btnModificar;
    private JButton btnImprimir;

    public DetallesAdopcionView(Adopcion adopcion) {
        this.adopcion = adopcion;

        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        setTitle("Detalles de la Adopción - ID: " + adopcion.getNumeroAdopcion());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelAdopcion = crearPanelInformacionAdopcion();
        panelPrincipal.add(panelAdopcion);
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel panelMascota = crearPanelInformacionMascota();
        panelPrincipal.add(panelMascota);
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel panelAdoptante = crearPanelInformacionAdoptante();
        panelPrincipal.add(panelAdoptante);
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel panelEmpleado = crearPanelInformacionEmpleado();
        panelPrincipal.add(panelEmpleado);
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel panelObservaciones = crearPanelObservaciones();
        panelPrincipal.add(panelObservaciones);

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelInformacionAdopcion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información de la Adopción"));
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Adopción:"), gbc);
        gbc.gridx = 1;
        lblIdAdopcion = new JLabel();
        lblIdAdopcion.setFont(lblIdAdopcion.getFont().deriveFont(Font.BOLD));
        panel.add(lblIdAdopcion, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Fecha de Adopción:"), gbc);
        gbc.gridx = 1;
        lblFechaAdopcion = new JLabel();
        lblFechaAdopcion.setFont(lblFechaAdopcion.getFont().deriveFont(Font.BOLD));
        panel.add(lblFechaAdopcion, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        lblEstado = new JLabel();
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD));
        panel.add(lblEstado, gbc);

        return panel;
    }

    private JPanel crearPanelInformacionMascota() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información de la Mascota"));
        panel.setBackground(new Color(240, 255, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        lblMascotaNombre = new JLabel();
        lblMascotaEspecie = new JLabel();
        lblMascotaRaza = new JLabel();
        lblMascotaEdad = new JLabel();
        lblMascotaColor = new JLabel();
        lblMascotaTamanio = new JLabel();

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(lblMascotaNombre, gbc);


        return panel;
    }

    private JPanel crearPanelInformacionAdoptante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información del Adoptante"));
        panel.setBackground(new Color(255, 248, 240));

        lblAdoptanteNombre = new JLabel();
        lblAdoptanteEmail = new JLabel();
        lblAdoptanteTelefono = new JLabel();
        lblAdoptanteDireccion = new JLabel();
        lblAdoptanteDni = new JLabel();

        return panel;
    }

    private JPanel crearPanelInformacionEmpleado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Empleado Responsable"));
        panel.setBackground(new Color(248, 240, 255));

        lblEmpleadoNombre = new JLabel();
        lblEmpleadoCargo = new JLabel();
        lblEmpleadoLegajo = new JLabel();

        return panel;
    }

    private JPanel crearPanelObservaciones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Observaciones"));

        txtObservaciones = new JTextArea(4, 40);
        txtObservaciones.setEditable(false);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        panel.add(scrollObservaciones, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnModificar = new JButton("Modificar");
        btnImprimir = new JButton("Imprimir");
        btnCerrar = new JButton("Cerrar");

        panel.add(btnModificar);
        panel.add(btnImprimir);
        panel.add(btnCerrar);

        return panel;
    }

    private void configurarVentana() {
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Getters para el controlador
    public Adopcion getAdopcion() { return adopcion; }
    public JLabel getLblIdAdopcion() { return lblIdAdopcion; }
    public JLabel getLblFechaAdopcion() { return lblFechaAdopcion; }
    public JLabel getLblEstado() { return lblEstado; }
    public JLabel getLblMascotaNombre() { return lblMascotaNombre; }
    public JLabel getLblMascotaEspecie() { return lblMascotaEspecie; }
    public JLabel getLblMascotaRaza() { return lblMascotaRaza; }
    public JLabel getLblMascotaEdad() { return lblMascotaEdad; }
    public JLabel getLblMascotaColor() { return lblMascotaColor; }
    public JLabel getLblMascotaTamanio() { return lblMascotaTamanio; }
    public JLabel getLblAdoptanteNombre() { return lblAdoptanteNombre; }
    public JLabel getLblAdoptanteEmail() { return lblAdoptanteEmail; }
    public JLabel getLblAdoptanteTelefono() { return lblAdoptanteTelefono; }
    public JLabel getLblAdoptanteDireccion() { return lblAdoptanteDireccion; }
    public JLabel getLblAdoptanteDni() { return lblAdoptanteDni; }
    public JLabel getLblEmpleadoNombre() { return lblEmpleadoNombre; }
    public JLabel getLblEmpleadoCargo() { return lblEmpleadoCargo; }
    public JLabel getLblEmpleadoLegajo() { return lblEmpleadoLegajo; }
    public JTextArea getTxtObservaciones() { return txtObservaciones; }
    public JButton getBtnCerrar() { return btnCerrar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnImprimir() { return btnImprimir; }
}