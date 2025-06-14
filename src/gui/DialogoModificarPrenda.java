package gui;

import datos.Marca;
import datos.Producto;
import logica.MaxLengthDocumentFilter;
import logica.ProductoService;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import java.awt.*;

public class DialogoModificarPrenda extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Marca> comboMarca;
    private JComboBox<Producto> comboProducto;
    private JTextField txtNombre, txtPrecio, txtTalla, txtColor, txtMaterial, txtStock;
    private final ProductoService service = new ProductoService();

    public DialogoModificarPrenda() {
        setTitle("Modificar Prenda");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        comboMarca = new JComboBox<>();
        comboProducto = new JComboBox<>();

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtTalla = new JTextField();
        ((AbstractDocument) txtTalla.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(4));
        txtColor = new JTextField();
        txtMaterial = new JTextField();
        txtStock = new JTextField();

        panel.add(new JLabel("Marca:"));
        panel.add(comboMarca);
        panel.add(new JLabel("Producto:"));
        panel.add(comboProducto);
        panel.add(new JLabel("Nombre producto:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Talla:"));
        panel.add(txtTalla);
        panel.add(new JLabel("Color:"));
        panel.add(txtColor);
        panel.add(new JLabel("Material:"));
        panel.add(txtMaterial);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(255, 153, 51));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar.setPreferredSize(new Dimension(180, 40));
        panelBoton.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        cargarMarcas();

        comboMarca.addActionListener(e -> {
            Marca m = (Marca) comboMarca.getSelectedItem();
            if (m != null) {
                cargarProductos(m.getId());
            }
        });

        comboProducto.addActionListener(e -> {
            Producto p = (Producto) comboProducto.getSelectedItem();
            if (p != null) {
                mostrarDetalle(p);
            }
        });

        btnGuardar.addActionListener(e -> {
            Producto seleccionado = (Producto) comboProducto.getSelectedItem();
            if (seleccionado != null) {
                seleccionado.setNombreProducto(txtNombre.getText());
                seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
                seleccionado.setTalla(txtTalla.getText());
                seleccionado.setColor(txtColor.getText());
                seleccionado.setMaterial(txtMaterial.getText());
                seleccionado.setStock(Integer.parseInt(txtStock.getText()));

                boolean actualizado = service.actualizarProducto(seleccionado);

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar producto.");
                }
            }
        });
    }

    private void cargarMarcas() {
        comboMarca.removeAllItems();
        for (Marca m : service.obtenerMarcas()) {
            comboMarca.addItem(m);
        }
    }

    private void cargarProductos(int marcaId) {
        comboProducto.removeAllItems();
        for (Producto p : service.obtenerProductosPorMarca(marcaId)) {
            comboProducto.addItem(p);
        }
    }

    private void mostrarDetalle(Producto p) {
        txtNombre.setText(p.getNombreProducto());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtTalla.setText(p.getTalla());
        txtColor.setText(p.getColor());
        txtMaterial.setText(p.getMaterial());
        txtStock.setText(String.valueOf(p.getStock()));
    }
}
