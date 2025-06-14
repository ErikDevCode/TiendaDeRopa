package gui;

import datos.Usuario;
import logica.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioWindow extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCorreo;
    private JTextField txtNombre;
    private JPasswordField txtContrasena;

    public RegistroUsuarioWindow() {
        setTitle("Registro de Usuario");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setFont(labelFont);
        panel.add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        // Correo
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(labelFont);
        panel.add(lblCorreo, gbc);

        gbc.gridx = 1;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblContra = new JLabel("Contraseña:");
        lblContra.setFont(labelFont);
        panel.add(lblContra, gbc);

        gbc.gridx = 1;
        txtContrasena = new JPasswordField(20);
        panel.add(txtContrasena, gbc);

        // Botón registrar
        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(66, 133, 244));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());

            if (correo.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario nuevo = new Usuario(0, correo, contrasena, nombre, 2,"");
            boolean registrado = new UsuarioService().registrarUsuario(nuevo);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(panel);
    }
}
