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
        btnRegistrar = new JButton("Registrar Nuevo Empleado");


        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));

        btnLogin.setPreferredSize(new Dimension(160, 35));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);

        btnRegistrar.setPreferredSize(new Dimension(200, 35));
        btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnRegistrar.setForeground(Color.WHITE);


        getRootPane().setDefaultButton(btnLogin);


        txtUsername.setToolTipText("Ingrese su nombre de usuario");
        txtPassword.setToolTipText("Ingrese su contraseña");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("🐾 Sistema de Adopción de Mascotas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Bienvenido - Inicie sesión para continuar");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        mainPanel.add(lblSubtitulo, gbc);


        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(300, 2));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(separator, gbc);


        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;


        JLabel lblUsuario = new JLabel("👤 Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblUsuario, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtUsername, gbc);


        JLabel lblPassword = new JLabel("🔒 Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblPassword, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtPassword, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegistrar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);


        JLabel lblInfo = new JLabel("<html><center><b>Credenciales por defecto:</b><br/>Usuario: admin | Contraseña: admin123</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(105, 105, 105));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        mainPanel.add(lblInfo, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupFrame() {
        setTitle("Login - Sistema de Adopción de Mascotas");
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