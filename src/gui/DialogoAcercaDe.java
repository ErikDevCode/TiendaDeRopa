package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DialogoAcercaDe extends JDialog {

    private static final long serialVersionUID = 1L;
    public DialogoAcercaDe() {
        setTitle("Acerca de Tienda de Ropa");
        setModal(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new GridBagLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Contenido centralizado
        JLabel lblTitulo = new JLabel("Sistema de Tienda de Ropa");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridy = 0;
        contentPanel.add(lblTitulo, gbc);

        JLabel lblVersion = new JLabel("Versión 2.0 - 2025");
        lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gbc.gridy = 1;
        contentPanel.add(lblVersion, gbc);

        JLabel lblIntegrantes = new JLabel(
                "<html><center><b>Integrantes:</b><br>"
                + "Hinostroza Díaz, Malena Karina<br>"
                + "Huanca Tito, Yuly Gisela<br>"
                + "Sanguinete Huambachano, Cesar David<br>"
                + "Castro Pantoja, Lissette Lidia<br>"
                + "Abanto Ramírez, Erik Raymand<br>"
                + "</center></html>");
        lblIntegrantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.gridy = 2;
        contentPanel.add(lblIntegrantes, gbc);

        JLabel lblCurso = new JLabel(
                "<html><center><b>Curso:</b> Algoritmos y estructura de datos<br>"
                + "<b>Docente:</b> Ing. Diaz Zavala, Marco Antonio<br>"
                + "<b>Ciclo:</b> 2025-II</center></html>");
        lblCurso.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.gridy = 3;
        contentPanel.add(lblCurso, gbc);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCerrar.addActionListener(e -> dispose());
        gbc.gridy = 4;
        contentPanel.add(btnCerrar, gbc);
    }
}