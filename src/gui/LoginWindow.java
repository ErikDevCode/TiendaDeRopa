package gui;

import datos.Usuario;
import logica.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public LoginWindow() {
        setTitle("Login - Sistema de ventas");
        setSize(650, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

     // ========== Panel de imagen ========== //
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(245, 245, 245));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 20));

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/login-store.jpg"));
            Image scaled = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImagen.setText("Imagen no encontrada");
        }

        imagePanel.add(lblImagen, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.WEST);


        // ========== Panel de login ========== //
        JPanel panelLogin = new JPanel(new GridBagLayout());
        panelLogin.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Correo
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelLogin.add(lblCorreo, gbc);

        gbc.gridy++;
        txtCorreo = new JTextField(20);
        txtCorreo.setFont(inputFont);
        txtCorreo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelLogin.add(txtCorreo, gbc);

        // Contraseña
        gbc.gridy++;
        JLabel lblContra = new JLabel("Contraseña:");
        lblContra.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelLogin.add(lblContra, gbc);

        gbc.gridy++;
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(inputFont);
        txtContrasena.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelLogin.add(txtContrasena, gbc);

        // Botón Ingresar
        gbc.gridy++;
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFocusPainted(false);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(66, 133, 244));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelLogin.add(btnIngresar, gbc);

        // Botón Crear usuario
        gbc.gridy++;
        JButton btnCrearUsuario = new JButton("Crear usuario");
        btnCrearUsuario.setFocusPainted(false);
        btnCrearUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrearUsuario.setBackground(new Color(52, 168, 83));
        btnCrearUsuario.setForeground(Color.WHITE);
        btnCrearUsuario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelLogin.add(btnCrearUsuario, gbc);


        // Evento para crear usuario
        btnCrearUsuario.addActionListener(e -> {
            new RegistroUsuarioWindow().setVisible(true);
        });

        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        tarjeta.add(panelLogin);

        // Contenedor centrado
        JPanel panelContenedor = new JPanel(new GridBagLayout());
        panelContenedor.setBackground(new Color(245, 245, 245));
        panelContenedor.add(tarjeta);

        mainPanel.add(panelContenedor, BorderLayout.CENTER);

        // Acción del botón Ingresar
        btnIngresar.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String contrasena = new String(txtContrasena.getPassword());
            Usuario user = new LoginService().autenticar(correo, contrasena);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + user.getNombreCompleto());
                dispose();
                new MenuWindow(user);
                if (user.getRolId() == 2) {
                    new DialogoVenderCarrito(user).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(mainPanel);
        setVisible(true);
    }
}
