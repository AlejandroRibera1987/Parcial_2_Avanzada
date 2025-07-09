package ar.edu.davinci.vista.login;

import javax.swing.*;
import java.awt.*;

public class RegistroEmpleadoView extends JDialog {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDireccion;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtEmail;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistroEmpleadoView(JFrame parent) {
        super(parent, "Registrar Nuevo Empleado", true);
        initComponents();
        setupLayout();
        setupDialog();
    }

    private void initComponents() {

        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtEmail = new JTextField(20);

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        // Configurar componentes
        Font fieldFont = new Font("Arial", Font.PLAIN, 12);
        txtNombre.setFont(fieldFont);
        txtApellido.setFont(fieldFont);
        txtDireccion.setFont(fieldFont);
        txtUsername.setFont(fieldFont);
        txtPassword.setFont(fieldFont);
        txtEmail.setFont(fieldFont);

        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 12));

        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));

        getRootPane().setDefaultButton(btnRegistrar);

        txtNombre.setToolTipText("Ingrese el nombre del empleado");
        txtApellido.setToolTipText("Ingrese el apellido del empleado");
        txtDireccion.setToolTipText("Dirección del empleado (opcional)");
        txtUsername.setToolTipText("Nombre de usuario único para el login");
        txtPassword.setToolTipText("Contraseña (mínimo 6 caracteres)");
        txtEmail.setToolTipText("Correo electrónico (opcional)");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;


        JLabel lblTitulo = new JLabel("Registro de Nuevo Empleado");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        Font labelFont = new Font("Arial", Font.BOLD, 12);

        JLabel lblNombre = new JLabel("Nombre: *");
        lblNombre.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblNombre, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtNombre, gbc);

        JLabel lblApellido = new JLabel("Apellido: *");
        lblApellido.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblApellido, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtApellido, gbc);


        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(lblDireccion, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDireccion, gbc);

        JLabel lblUsername = new JLabel("Usuario: *");
        lblUsername.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Contraseña: *");
        lblPassword.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPassword, gbc);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);


        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblNota, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(getParent());

    }

    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtApellido() { return txtApellido; }
    public JTextField getTxtDireccion() { return txtDireccion; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}