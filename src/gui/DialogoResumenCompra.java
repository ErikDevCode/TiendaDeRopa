package gui;

import javax.swing.*;
import java.awt.*;

public class DialogoResumenCompra extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean confirmado = false;

    public DialogoResumenCompra(double total, double descuentoPorcentaje) {
        setTitle("Resumen de Compra");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        double descuentoMonto = total * descuentoPorcentaje;
        double totalConDescuento = total - descuentoMonto;
        double igv = totalConDescuento * 0.18;

        // Panel de contenido central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font valueFont = new Font("Segoe UI", Font.BOLD, 16);

        // Fila 1 - Monto de descuento
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentro.add(new JLabel("Monto de descuento:"), gbc);
        gbc.gridx = 1;
        JLabel lblDescuentoMonto = new JLabel(String.format("- S/ %.2f", descuentoMonto));
        lblDescuentoMonto.setFont(valueFont);
        lblDescuentoMonto.setForeground(new Color(219, 68, 55));
        panelCentro.add(lblDescuentoMonto, gbc);

        // Fila 2 - Subtotal
        gbc.gridx = 0; gbc.gridy = 2;
        panelCentro.add(new JLabel("Subtotal:"), gbc);
        gbc.gridx = 1;
        JLabel lblSubtotal = new JLabel(String.format("S/ %.2f", total));
        lblSubtotal.setFont(valueFont);
        panelCentro.add(lblSubtotal, gbc);

        // Fila 3 - IGV
        gbc.gridx = 0; gbc.gridy = 3;
        panelCentro.add(new JLabel("IGV (18%):"), gbc);
        gbc.gridx = 1;
        JLabel lblIGV = new JLabel(String.format("S/ %.2f", igv));
        lblIGV.setFont(valueFont);
        panelCentro.add(lblIGV, gbc);

        // Fila 4 - Total
        gbc.gridx = 0; gbc.gridy = 4;
        panelCentro.add(new JLabel("Total a pagar:"), gbc);
        gbc.gridx = 1;
        JLabel lblTotal = new JLabel(String.format("S/ %.2f", totalConDescuento));
        lblTotal.setFont(valueFont);
        lblTotal.setForeground(new Color(52, 168, 83));
        panelCentro.add(lblTotal, gbc);

        add(panelCentro, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");

        btnConfirmar.setBackground(new Color(52, 168, 83));
        btnConfirmar.setForeground(Color.WHITE);
        btnCancelar.setBackground(Color.LIGHT_GRAY);

        btnConfirmar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}
