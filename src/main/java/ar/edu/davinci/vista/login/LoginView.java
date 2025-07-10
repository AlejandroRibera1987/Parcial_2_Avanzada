package ar.edu.davinci.vista.login;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrar;

    public LoginView() {
        initComponents();
        setupLayout();
        setupFrame();
    }

    private void initComponents() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin = new JButton("Iniciar Sesión");
        btnRegistrar = new JButton("Registrar Empleado");

        getRootPane().setDefaultButton(btnLogin);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;


        JLabel lblTitulo = new JLabel("Sistema de Adopcion de Mascotas");
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitulo, gbc);

        // Espacio
        gbc.gridy = 1;
        mainPanel.add(Box.createVerticalStrut(10), gbc);

        // Campos
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblUsuario = new JLabel("Usuario:");
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblUsuario, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblPassword, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtPassword, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegistrar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);


        JLabel lblInfo = new JLabel("Usuario prueba: admin / admin123");
        lblInfo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        gbc.gridy = 5;
        mainPanel.add(lblInfo, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupFrame() {
        setTitle("Sistema de Adopcion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
}