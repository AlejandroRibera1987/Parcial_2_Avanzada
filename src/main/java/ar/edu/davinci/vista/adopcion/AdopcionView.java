package ar.edu.davinci.vista.adopcion;

import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Mascota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdopcionView extends JFrame {
    private JLabel lblEmpleadoLogueado;
    private JButton btnCerrarSesion;

    private JTable tableMascotas;
    private DefaultTableModel modeloTablaMascotas;
    private JTextArea txtRecomendaciones;

    private JTextField txtNombreAdoptante;
    private JTextField txtApellidoAdoptante;
    private JTextField txtDireccionAdoptante;
    private JTextField txtEdadAdoptante;
    private JTextField txtTelefonoAdoptante;
    private JTextField txtEmailAdoptante;

    private JButton btnProcesarAdopcion;
    private JButton btnLimpiar;
    private JButton btnActualizar;

    private List<Mascota> mascotasDisponibles;

    public AdopcionView() {
        initComponents();
        setupLayout();
        setupFrame();
    }

    private void initComponents() {
        lblEmpleadoLogueado = new JLabel("Empleado: No logueado");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Peso", "Estado"};
        modeloTablaMascotas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableMascotas = new JTable(modeloTablaMascotas);
        tableMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableMascotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarRecomendacionesMascota();
            }
        });

        txtRecomendaciones = new JTextArea(5, 30);
        txtRecomendaciones.setEditable(false);
        txtRecomendaciones.setText("Seleccione una mascota para ver recomendaciones...");

        txtNombreAdoptante = new JTextField(15);
        txtApellidoAdoptante = new JTextField(15);
        txtDireccionAdoptante = new JTextField(15);
        txtEdadAdoptante = new JTextField(15);
        txtTelefonoAdoptante = new JTextField(15);
        txtEmailAdoptante = new JTextField(15);


        btnProcesarAdopcion = new JButton("Procesar Adopcion");
        btnLimpiar = new JButton("Borrar Datos");
        btnActualizar = new JButton("Actualizar");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.add(lblEmpleadoLogueado, BorderLayout.WEST);
        topPanel.add(btnCerrarSesion, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Mascotas Disponibles"));

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topLeftPanel.add(btnActualizar);
        leftPanel.add(topLeftPanel, BorderLayout.NORTH);

        JScrollPane scrollMascotas = new JScrollPane(tableMascotas);
        scrollMascotas.setPreferredSize(new Dimension(400, 200));
        leftPanel.add(scrollMascotas, BorderLayout.CENTER);


        JPanel recomendacionesPanel = new JPanel(new BorderLayout());
        recomendacionesPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciones"));
        JScrollPane scrollRecomendaciones = new JScrollPane(txtRecomendaciones);
        scrollRecomendaciones.setPreferredSize(new Dimension(400, 100));
        recomendacionesPanel.add(scrollRecomendaciones, BorderLayout.CENTER);
        leftPanel.add(recomendacionesPanel, BorderLayout.SOUTH);

        JPanel rightPanel = crearPanelAdoptante();

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnProcesarAdopcion);
        bottomPanel.add(btnLimpiar);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelAdoptante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Adoptante"));
        panel.setPreferredSize(new Dimension(250, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombreAdoptante, gbc);

        JLabel lblApellido = new JLabel("* Apellido:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblApellido, gbc);
        gbc.gridx = 1;
        panel.add(txtApellidoAdoptante, gbc);

        JLabel lblDireccion = new JLabel("* Direccion:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDireccion, gbc);
        gbc.gridx = 1;
        panel.add(txtDireccionAdoptante, gbc);

        JLabel lblEdad = new JLabel("* Edad:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblEdad, gbc);
        gbc.gridx = 1;
        panel.add(txtEdadAdoptante, gbc);

        JLabel lblTelefono = new JLabel("* Telefono:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblTelefono, gbc);
        gbc.gridx = 1;
        panel.add(txtTelefonoAdoptante, gbc);

        JLabel lblEmail = new JLabel("* Email:");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmailAdoptante, gbc);

        // Nota
        JLabel lblNota = new JLabel("Los campos que tengan * son OBLIGATORIOS");
        lblNota.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblNota, gbc);

        return panel;
    }

    private void setupFrame() {
        setTitle("Panel de Adopciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    // Métodos públicos
    public void mostrarEmpleadoLogueado(Empleado empleado) {
        lblEmpleadoLogueado.setText("Empleado: " + empleado.getNombre() + " " + empleado.getApellido());
    }

    public void actualizarListaMascotas(List<Mascota> mascotas) {
        this.mascotasDisponibles = mascotas;
        modeloTablaMascotas.setRowCount(0);

        for (Mascota mascota : mascotas) {
            Object[] fila = {
                    mascota.getIdMascota(),
                    mascota.getNombre(),
                    mascota.getEspecie(),
                    mascota.getRaza(),
                    mascota.getPeso(),
                    mascota.getEstado().getNombreEstado()
            };
            modeloTablaMascotas.addRow(fila);
        }
    }

    public Mascota getMascotaSeleccionada() {
        int filaSeleccionada = tableMascotas.getSelectedRow();
        if (filaSeleccionada == -1 || mascotasDisponibles == null) return null;

        if (filaSeleccionada >= 0 && filaSeleccionada < mascotasDisponibles.size()) {
            return mascotasDisponibles.get(filaSeleccionada);
        }
        return null;
    }

    private void mostrarRecomendacionesMascota() {
        Mascota mascota = getMascotaSeleccionada();
        if (mascota != null) {
            String recomendaciones = "Recomendaciones para " + mascota.getNombre() + ":\n";
            recomendaciones += mascota.getRecomendaciones();
            txtRecomendaciones.setText(recomendaciones);
        } else {
            txtRecomendaciones.setText("Seleccione una mascota para ver recomendaciones...");
        }
    }

    public void limpiarFormulario() {
        txtNombreAdoptante.setText("");
        txtApellidoAdoptante.setText("");
        txtDireccionAdoptante.setText("");
        txtEdadAdoptante.setText("");
        txtTelefonoAdoptante.setText("");
        txtEmailAdoptante.setText("");
        tableMascotas.clearSelection();
        txtRecomendaciones.setText("Seleccione una mascota para ver recomendaciones...");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public boolean confirmar(String mensaje, String titulo) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    // Getters
    public JTextField getTxtNombreAdoptante() { return txtNombreAdoptante; }
    public JTextField getTxtApellidoAdoptante() { return txtApellidoAdoptante; }
    public JTextField getTxtDireccionAdoptante() { return txtDireccionAdoptante; }
    public JTextField getTxtEdadAdoptante() { return txtEdadAdoptante; }
    public JTextField getTxtTelefonoAdoptante() { return txtTelefonoAdoptante; }
    public JTextField getTxtEmailAdoptante() { return txtEmailAdoptante; }
    public JButton getBtnProcesarAdopcion() { return btnProcesarAdopcion; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JTable getTableMascotas() { return tableMascotas; }
    public List<Mascota> getMascotasDisponibles() { return mascotasDisponibles; }
}