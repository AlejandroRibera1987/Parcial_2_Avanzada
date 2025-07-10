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
        super(parent, "Registrar Empleado", true);
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

        btnRegistrar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        getRootPane().setDefaultButton(btnRegistrar);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitulo = new JLabel("Registrar Empleados");
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblNombre = new JLabel("* Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtNombre, gbc);

        JLabel lblApellido = new JLabel("* Apellido:");
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblApellido, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtApellido, gbc);

        JLabel lblDireccion = new JLabel("* Dirección:");
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblDireccion, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtDireccion, gbc);

        JLabel lblUsername = new JLabel("* Usuario:");
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblUsername, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("* Contraseña:");
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblPassword, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtPassword, gbc);

        JLabel lblEmail = new JLabel("* Email:");
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtEmail, gbc);

        JLabel lblNota = new JLabel("Los campos que tengan * son OBLIGATORIOS");
        lblNota.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblNota, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        gbc.gridy = 8;
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