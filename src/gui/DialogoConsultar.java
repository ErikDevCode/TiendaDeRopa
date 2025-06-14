package gui;

import datos.Marca;
import datos.Producto;
import logica.ProductoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoConsultar extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Marca> comboMarcas;
    private JComboBox<Producto> comboProductos;
    private JTextArea txtDetalle;
    private final ProductoService service = new ProductoService();
    private JTextField txtNombre, txtPrecio, txtTalla, txtColor, txtMaterial, txtStock;


    public DialogoConsultar() {
        setTitle("Consultar Prenda");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(3, 1, 10, 10));
        comboMarcas = new JComboBox<>();
        comboProductos = new JComboBox<>();
        txtDetalle = new JTextArea(6, 40);
        txtDetalle.setEditable(false);
        txtDetalle.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollDetalle = new JScrollPane(txtDetalle);

        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelSuperior.add(new JLabel("Seleccione una Marca:"));
        panelSuperior.add(comboMarcas);
        panelSuperior.add(new JLabel("Seleccione un Producto:"));
        panelSuperior.add(comboProductos);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollDetalle, BorderLayout.CENTER);

        cargarMarcas();

        comboMarcas.addActionListener(e -> {
            Marca marcaSeleccionada = (Marca) comboMarcas.getSelectedItem();
            if (marcaSeleccionada != null) {
                cargarProductos(marcaSeleccionada.getId());
            }
        });

        comboProductos.addActionListener(e -> {
            Producto producto = (Producto) comboProductos.getSelectedItem();
            if (producto != null) {
                mostrarDetalle(producto);
            }
        });
        
        JPanel panelDetalle = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDetalle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelDetalle.setBackground(Color.WHITE);

        txtNombre = crearCampo();
        txtPrecio = crearCampo();
        txtTalla = crearCampo();
        txtColor = crearCampo();
        txtMaterial = crearCampo();
        txtStock = crearCampo();

        panelDetalle.add(new JLabel("Nombre:"));
        panelDetalle.add(txtNombre);

        panelDetalle.add(new JLabel("Precio:"));
        panelDetalle.add(txtPrecio);

        panelDetalle.add(new JLabel("Talla:"));
        panelDetalle.add(txtTalla);

        panelDetalle.add(new JLabel("Color:"));
        panelDetalle.add(txtColor);

        panelDetalle.add(new JLabel("Material:"));
        panelDetalle.add(txtMaterial);

        panelDetalle.add(new JLabel("Stock:"));
        panelDetalle.add(txtStock);

        add(panelDetalle, BorderLayout.CENTER);

    }

    private void cargarMarcas() {
        List<Marca> marcas = service.obtenerMarcas();
        comboMarcas.removeAllItems();
        for (Marca m : marcas) {
            comboMarcas.addItem(m);
        }
    }

    private void cargarProductos(int marcaId) {
        List<Producto> productos = service.obtenerProductosPorMarca(marcaId);
        comboProductos.removeAllItems();
        for (Producto p : productos) {
            comboProductos.addItem(p);
        }
        txtDetalle.setText("");
    }

    private JTextField crearCampo() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        txt.setBackground(new Color(245, 245, 245));
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return txt;
    }
    
    private void mostrarDetalle(Producto p) {
        txtNombre.setText(p.getNombreProducto());
        txtPrecio.setText("S/ " + p.getPrecio());
        txtTalla.setText(p.getTalla());
        txtColor.setText(p.getColor());
        txtMaterial.setText(p.getMaterial());
        txtStock.setText(String.valueOf(p.getStock()));
    }

}
