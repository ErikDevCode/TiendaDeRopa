package gui;

import datos.Marca;
import logica.ProductoService;

import javax.swing.*;
import java.awt.*;

public class DialogoDescuentos extends JDialog {

    private static final long serialVersionUID = 1L;
    private JComboBox<Marca> comboMarca;
    private JTextField txtDescuento;
    private final ProductoService service = new ProductoService();

    public DialogoDescuentos() {
        setTitle("Configurar Descuento por Marca");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        comboMarca = new JComboBox<>();
        txtDescuento = new JTextField();

        // Cargar marcas
        for (Marca marca : service.obtenerMarcas()) {
            comboMarca.addItem(marca);
        }

        comboMarca.addActionListener(e -> mostrarDescuentoActual());

        panel.add(new JLabel("Marca:"));
        panel.add(comboMarca);
        panel.add(new JLabel("Descuento (%):"));
        panel.add(txtDescuento);

        add(panel, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");

        btnGuardar.addActionListener(e -> guardarDescuento());
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        mostrarDescuentoActual();
    }

    private void mostrarDescuentoActual() {
        Marca seleccionada = (Marca) comboMarca.getSelectedItem();
        if (seleccionada != null) {
            double descuento = service.obtenerDescuentoPorMarca(seleccionada.getId());
            txtDescuento.setText(String.valueOf(descuento * 100));
        }
    }

    private void guardarDescuento() {
        Marca seleccionada = (Marca) comboMarca.getSelectedItem();
        if (seleccionada == null) return;

        try {
            double porcentaje = Double.parseDouble(txtDescuento.getText()) / 100;
            if (porcentaje < 0 || porcentaje > 1) {
                JOptionPane.showMessageDialog(this, "Ingrese un porcentaje válido entre 0% y 100%.");
                return;
            }

            boolean ok = service.guardarDescuentoMarca(seleccionada.getId(), porcentaje);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Descuento guardado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar descuento.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
        }
    }
}
