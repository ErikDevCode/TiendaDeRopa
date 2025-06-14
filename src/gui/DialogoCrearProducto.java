package gui;

import datos.Marca;
import datos.Producto;
import logica.MaxLengthDocumentFilter;
import logica.ProductoService;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.util.List;

public class DialogoCrearProducto extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Marca> comboMarca;
    private JTextField txtNombre, txtPrecio, txtTalla, txtColor, txtMaterial, txtStock;
    private final ProductoService service = new ProductoService();

    public DialogoCrearProducto() {
        setTitle("Crear nuevo producto");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboMarca = new JComboBox<>();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtTalla = new JTextField();
        ((AbstractDocument) txtTalla.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(4));
        txtColor = new JTextField();
        txtMaterial = new JTextField();
        txtStock = new JTextField();

        panel.add(new JLabel("Marca:"));
        panel.add(comboMarca);
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

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(52, 168, 83));
        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.addActionListener(e -> guardarProducto());

        add(panel, BorderLayout.CENTER);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        btnGuardar.setPreferredSize(new Dimension(140, 35));
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBackground(new Color(52, 168, 83));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelBoton.add(btnGuardar);

        add(panelBoton, BorderLayout.SOUTH);



        cargarMarcas();
    }

    private void cargarMarcas() {
        List<Marca> marcas = service.obtenerMarcas();
        comboMarca.removeAllItems();
        for (Marca m : marcas) {
            comboMarca.addItem(m);
        }
    }

    private void guardarProducto() {
        try {
            Marca marca = (Marca) comboMarca.getSelectedItem();
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            String talla = txtTalla.getText();
            String color = txtColor.getText();
            String material = txtMaterial.getText();
            int stock = Integer.parseInt(txtStock.getText());

            Producto producto = new Producto(0, marca.getId(), nombre, precio, talla, color, material, stock);
            producto.setMarcaId(marca.getId());

            boolean exito = service.crearProducto(producto);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto creado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear producto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }
}
