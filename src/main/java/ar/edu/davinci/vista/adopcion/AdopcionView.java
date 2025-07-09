package ar.edu.davinci.vista.adopcion;

import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Mascota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class AdopcionView extends JFrame {

    private JLabel lblEmpleadoLogueado;
    private JButton btnCerrarSesion;


    private JTable tableMascotas;
    private DefaultTableModel modeloTablaMascotas;
    private JTextArea txtRecomendaciones;
    private JScrollPane scrollMascotas;

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

        lblEmpleadoLogueado = new JLabel("👤 Empleado: No logueado");
        lblEmpleadoLogueado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEmpleadoLogueado.setForeground(new Color(70, 130, 180));

        btnCerrarSesion = new JButton("🚪 Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(220, 20, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 11));

        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Peso (kg)", "Estado", "Disponible"};
        modeloTablaMascotas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Double.class; // Peso
                if (columnIndex == 6) return Boolean.class; // Disponible
                return String.class;
            }
        };

        tableMascotas = new JTable(modeloTablaMascotas);
        tableMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableMascotas.setRowHeight(25);
        tableMascotas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableMascotas.setFont(new Font("Arial", Font.PLAIN, 11));


        tableMascotas.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tableMascotas.getColumnModel().getColumn(1).setPreferredWidth(100); // Nombre
        tableMascotas.getColumnModel().getColumn(2).setPreferredWidth(70);  // Especie
        tableMascotas.getColumnModel().getColumn(3).setPreferredWidth(120); // Raza
        tableMascotas.getColumnModel().getColumn(4).setPreferredWidth(70);  // Peso
        tableMascotas.getColumnModel().getColumn(5).setPreferredWidth(150); // Estado
        tableMascotas.getColumnModel().getColumn(6).setPreferredWidth(80);  // Disponible

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTablaMascotas);
        tableMascotas.setRowSorter(sorter);

        scrollMascotas = new JScrollPane(tableMascotas);
        scrollMascotas.setPreferredSize(new Dimension(500, 250));

        tableMascotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarRecomendacionesMascota();
            }
        });

        txtRecomendaciones = new JTextArea(6, 40);
        txtRecomendaciones.setEditable(false);
        txtRecomendaciones.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtRecomendaciones.setBackground(new Color(248, 248, 248));
        txtRecomendaciones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtRecomendaciones.setText("Seleccione una mascota para ver las recomendaciones de cuidado...");


        Font fieldFont = new Font("Arial", Font.PLAIN, 12);
        txtNombreAdoptante = new JTextField(15);
        txtApellidoAdoptante = new JTextField(15);
        txtDireccionAdoptante = new JTextField(15);
        txtEdadAdoptante = new JTextField(15);
        txtTelefonoAdoptante = new JTextField(15);
        txtEmailAdoptante = new JTextField(15);

        txtNombreAdoptante.setFont(fieldFont);
        txtApellidoAdoptante.setFont(fieldFont);
        txtDireccionAdoptante.setFont(fieldFont);
        txtEdadAdoptante.setFont(fieldFont);
        txtTelefonoAdoptante.setFont(fieldFont);
        txtEmailAdoptante.setFont(fieldFont);

        txtNombreAdoptante.setToolTipText("Ingrese el nombre del adoptante");
        txtApellidoAdoptante.setToolTipText("Ingrese el apellido del adoptante");
        txtDireccionAdoptante.setToolTipText("Dirección completa del adoptante");
        txtEdadAdoptante.setToolTipText("Edad (debe ser mayor a 18 años)");
        txtTelefonoAdoptante.setToolTipText("Número de teléfono (opcional)");
        txtEmailAdoptante.setToolTipText("Correo electrónico (opcional)");


        btnProcesarAdopcion = new JButton("Procesar Adopción");
        btnLimpiar = new JButton("Limpiar");
        btnActualizar = new JButton("Actualizar Lista");

        btnProcesarAdopcion.setPreferredSize(new Dimension(180, 35));
        btnProcesarAdopcion.setBackground(new Color(34, 139, 34));
        btnProcesarAdopcion.setForeground(Color.WHITE);
        btnProcesarAdopcion.setFont(new Font("Arial", Font.BOLD, 12));

        btnLimpiar.setPreferredSize(new Dimension(120, 35));
        btnLimpiar.setBackground(new Color(255, 140, 0));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));

        btnActualizar.setPreferredSize(new Dimension(140, 35));
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(230, 240, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        topPanel.add(lblEmpleadoLogueado, BorderLayout.WEST);
        topPanel.add(btnCerrarSesion, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Mascotas Disponibles para Adopcion",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)
        ));

        JPanel topMascotasPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topMascotasPanel.add(btnActualizar);
        leftPanel.add(topMascotasPanel, BorderLayout.NORTH);

        leftPanel.add(scrollMascotas, BorderLayout.CENTER);

        JPanel recomendacionesPanel = new JPanel(new BorderLayout());
        recomendacionesPanel.setBorder(BorderFactory.createTitledBorder(
                "Recomendaciones de Cuidado"
        ));
        JScrollPane scrollRecomendaciones = new JScrollPane(txtRecomendaciones);
        scrollRecomendaciones.setPreferredSize(new Dimension(500, 120));
        recomendacionesPanel.add(scrollRecomendaciones, BorderLayout.CENTER);
        leftPanel.add(recomendacionesPanel, BorderLayout.SOUTH);

        JPanel rightPanel = crearPanelAdoptante();

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(248, 248, 248));
        bottomPanel.add(btnProcesarAdopcion);
        bottomPanel.add(btnLimpiar);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelAdoptante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
                "Datos del Adoptante",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(34, 139, 34)
        ));
        panel.setPreferredSize(new Dimension(320, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 12);

        // Campos del adoptante
        JLabel lblNombre = new JLabel("Nombre: *");
        lblNombre.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombreAdoptante, gbc);

        JLabel lblApellido = new JLabel("Apellido: *");
        lblApellido.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblApellido, gbc);
        gbc.gridx = 1;
        panel.add(txtApellidoAdoptante, gbc);

        JLabel lblDireccion = new JLabel("Dirección: *");
        lblDireccion.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDireccion, gbc);
        gbc.gridx = 1;
        panel.add(txtDireccionAdoptante, gbc);

        JLabel lblEdad = new JLabel("Edad: *");
        lblEdad.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblEdad, gbc);
        gbc.gridx = 1;
        panel.add(txtEdadAdoptante, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblTelefono, gbc);
        gbc.gridx = 1;
        panel.add(txtTelefonoAdoptante, gbc);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmailAdoptante, gbc);

        // Nota sobre campos obligatorios
        JLabel lblNota = new JLabel("<html><i>* Campos obligatorios</i></html>");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblNota, gbc);

        // Información adicional
        JTextArea infoArea = new JTextArea(3, 25);
        infoArea.setText("Información:\n• Debe ser mayor de 18 años\n• Se requiere compromiso de cuidado responsable");
        infoArea.setEditable(false);
        infoArea.setBackground(panel.getBackground());
        infoArea.setFont(new Font("Arial", Font.PLAIN, 10));
        infoArea.setForeground(new Color(105, 105, 105));
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(infoArea, gbc);

        return panel;
    }

    private void setupFrame() {
        setTitle("Sistema de Adopción de Mascotas - Gestión de Adopciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(900, 600));
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar ventana
    }

    // Métodos públicos para el controlador
    public void mostrarEmpleadoLogueado(Empleado empleado) {
        lblEmpleadoLogueado.setText("Empleado: " + empleado.getNombre() + " " + empleado.getApellido() +
                " (ID: " + empleado.getIdEmpleado() + ")");
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
                    mascota.getEstado().getNombreEstado(),
                    mascota.isDisponible() ? "Sí" : "No"
            };
            modeloTablaMascotas.addRow(fila);
        }

        String titulo = "Mascotas Disponibles para Adopcion (" + mascotas.size() + " disponibles)";
        ((javax.swing.border.TitledBorder) ((JPanel) scrollMascotas.getParent()).getBorder()).setTitle(titulo);
        repaint();
    }

    public Mascota getMascotaSeleccionada() {
        int filaSeleccionada = tableMascotas.getSelectedRow();
        if (filaSeleccionada == -1 || mascotasDisponibles == null) return null;

        // Convertir índice de vista a índice del modelo (por si hay sorting)
        int modelIndex = tableMascotas.convertRowIndexToModel(filaSeleccionada);

        if (modelIndex >= 0 && modelIndex < mascotasDisponibles.size()) {
            return mascotasDisponibles.get(modelIndex);
        }

        return null;
    }

    private void mostrarRecomendacionesMascota() {
        Mascota mascota = getMascotaSeleccionada();
        if (mascota != null) {
            String recomendaciones = "🐾 RECOMENDACIONES PARA: " + mascota.getNombre() + " (" + mascota.getEspecie() + ")\n";
            recomendaciones += "═══════════════════════════════════════════════\n\n";
            recomendaciones += mascota.getRecomendaciones();
            txtRecomendaciones.setText(recomendaciones);
            txtRecomendaciones.setCaretPosition(0); // Scroll al inicio
        } else {
            txtRecomendaciones.setText("Seleccione una mascota para ver las recomendaciones de cuidado...");
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
        txtRecomendaciones.setText("Seleccione una mascota para ver las recomendaciones de cuidado...");

        txtNombreAdoptante.requestFocus();
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public boolean confirmar(String mensaje, String titulo) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

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